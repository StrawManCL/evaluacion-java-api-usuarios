package cl.banco.evaluacion.service;

import cl.banco.evaluacion.dto.UsuarioBasicDTO;
import cl.banco.evaluacion.dto.UsuarioFullResponseDTO;
import cl.banco.evaluacion.dto.UsuarioRequestDTO;
import cl.banco.evaluacion.dto.UsuarioResponseDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

  List<UsuarioResponseDTO> findAll();

  Optional<UsuarioFullResponseDTO> findById(UUID id);

  UsuarioBasicDTO create(UsuarioRequestDTO user);
}
