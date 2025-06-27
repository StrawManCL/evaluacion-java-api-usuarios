package cl.banco.evaluacion.service.impl;

import cl.banco.evaluacion.dto.TelefonoDTO;
import cl.banco.evaluacion.dto.UsuarioBasicDTO;
import cl.banco.evaluacion.dto.UsuarioFullResponseDTO;
import cl.banco.evaluacion.dto.UsuarioRequestDTO;
import cl.banco.evaluacion.dto.UsuarioResponseDTO;
import cl.banco.evaluacion.exceptions.EmailAlreadyExistsException;
import cl.banco.evaluacion.model.Telefono;
import cl.banco.evaluacion.model.Usuario;
import cl.banco.evaluacion.repository.UsuarioRepository;
import cl.banco.evaluacion.service.UsuarioService;
import cl.banco.evaluacion.util.JwtUtil;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final JwtUtil jwtUtil;

  @Override
  public List<UsuarioResponseDTO> findAll() {
    return usuarioRepository.findAll()
        .stream()
        .map(UsuarioResponseDTO::fromUser)
        .toList();
  }

  @Override
  public Optional<UsuarioFullResponseDTO> findById(UUID id) {
    return usuarioRepository.findById(id)
        .map(UsuarioFullResponseDTO::fromUser);
  }

  @Override
  public UsuarioBasicDTO create(UsuarioRequestDTO usuarioRequestDTO)
      throws EmailAlreadyExistsException {
    if (usuarioRepository.existsByEmail(usuarioRequestDTO.email())) {
      throw new EmailAlreadyExistsException("El email ya está registrado");
    }
    List<Telefono> telefonos = telefonoDtoToEntity(usuarioRequestDTO.phones());

    Usuario user = Usuario.builder()
        .name(usuarioRequestDTO.name())
        .email(usuarioRequestDTO.email())
        .password(usuarioRequestDTO.password())
        .phones(telefonos)
        .created(OffsetDateTime.now())
        .modified(OffsetDateTime.now())
        .lastLogin(OffsetDateTime.now())
        .token(generateToken(usuarioRequestDTO.email()))
        .active(true)
        .build();
    usuarioRepository.save(user);

    return UsuarioBasicDTO.fromUser(user);
  }

  private List<Telefono> telefonoDtoToEntity(List<TelefonoDTO> telefonoDTOList) {
    return telefonoDTOList.stream()
        .map(dto -> Telefono.builder()
            .number(dto.number())
            .cityCode(dto.cityCode())
            .countryCode(dto.countryCode())
            .build())
        .toList();
  }

  private String generateToken(String email) {
    return jwtUtil.generateToken(email);
  }
}
