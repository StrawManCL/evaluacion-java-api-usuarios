package cl.banco.evaluacion.fixtures;

import cl.banco.evaluacion.model.Usuario;
import io.jsonwebtoken.lang.Strings;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UsuarioFixture {

  public static Usuario usuarioSinTelefono(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .name("Juan")
        .email("juan@rodriguez.org")
        .password("hunter2")
        .active(true)
        .created(fecha)
        .modified(fecha)
        .lastLogin(fecha)
        .phones(TelefonoFixture.sinTelefono())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioSinCorreo(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .name("Juan")
        .email(Strings.EMPTY)
        .password("hunter2")
        .active(true)
        .created(fecha)
        .modified(fecha)
        .lastLogin(fecha)
        .phones(TelefonoFixture.sinTelefono())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioConUnTelefono(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .name("Juan")
        .email("juan@rodriguez.org")
        .password("hunter2")
        .active(true)
        .created(fecha)
        .modified(fecha)
        .lastLogin(fecha)
        .phones(TelefonoFixture.telefonoUnico())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioConTresTelefono(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .name("Juan")
        .email("juan@rodriguez.org")
        .password("hunter2")
        .active(true)
        .created(fecha)
        .modified(fecha)
        .lastLogin(fecha)
        .phones(TelefonoFixture.tresTelefonos())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioSinTelefonoSinId(OffsetDateTime fecha) {
    return Usuario.builder()
        .name("Juan")
        .email("juan@rodriguez.org")
        .password("hunter2")
        .active(true)
        .created(fecha)
        .modified(fecha)
        .lastLogin(fecha)
        .phones(TelefonoFixture.sinTelefono())
        .token("ABC123")
        .build();
  }
}
