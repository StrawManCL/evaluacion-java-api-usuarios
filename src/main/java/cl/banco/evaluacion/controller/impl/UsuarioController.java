package cl.banco.evaluacion.controller.impl;

import cl.banco.evaluacion.controller.UsuarioApi;
import cl.banco.evaluacion.dto.UsuarioBasicDTO;
import cl.banco.evaluacion.dto.UsuarioFullResponseDTO;
import cl.banco.evaluacion.dto.UsuarioRequestDTO;
import cl.banco.evaluacion.dto.UsuarioResponseDTO;
import cl.banco.evaluacion.exceptions.UserNotFoundException;
import cl.banco.evaluacion.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuarioApi {

  private final UsuarioService usuarioService;

  @Override
  @PreAuthorize("permitAll()")
  public ResponseEntity<?> crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
    UsuarioBasicDTO usuarioCreado = usuarioService.create(usuarioRequestDTO);
    return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<UsuarioResponseDTO>> listaUsuarios() {
    return ResponseEntity.ok(usuarioService.findAll());
  }

  @Override
  public ResponseEntity<UsuarioFullResponseDTO> getUsuario(UUID id) {
    Optional<UsuarioFullResponseDTO> optionalUsuario = usuarioService.findById(id);
    return optionalUsuario.map(ResponseEntity::ok)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
  }
}
