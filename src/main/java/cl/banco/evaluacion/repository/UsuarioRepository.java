package cl.banco.evaluacion.repository;

import cl.banco.evaluacion.model.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  Usuario findByEmail(String email);

  boolean existsByEmail(String email);
}
