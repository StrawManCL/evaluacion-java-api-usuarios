package cl.banco.evaluacion.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest {

  private static final UUID ID_USUARIO = UUID.randomUUID();
  private static final OffsetDateTime FECHA = OffsetDateTime.of(LocalDate.of(1970, 1, 1),
      LocalTime.MIDNIGHT, ZoneOffset.UTC);

  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setName("Juan");
    usuario.setEmail("juan@rodriguez.org");
    usuario.setPhones(Collections.emptyList());
    usuario.setPassword("hunter2");
    usuario.setCreated(FECHA);
    usuario.setModified(FECHA);
    usuario.setLastLogin(FECHA);
    usuario.setActive(true);
    usuario.setToken("ABC123");

    int expectedHashCodeResult = usuario.hashCode();
    assertEquals(expectedHashCodeResult, usuario.hashCode());
  }

  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setName("Juan");
    usuario.setEmail("juan@rodriguez.org");
    usuario.setPhones(Collections.emptyList());
    usuario.setPassword("hunter2");
    usuario.setCreated(FECHA);
    usuario.setModified(FECHA);
    usuario.setLastLogin(FECHA);
    usuario.setActive(true);
    usuario.setToken("ABC123");

    Usuario usuario2 = new Usuario();
    usuario2.setId(UUID.randomUUID());
    usuario2.setName("Juan");
    usuario2.setEmail("juan@rodriguez.org");
    usuario2.setPhones(Collections.emptyList());
    usuario2.setPassword("hunter2");
    usuario2.setCreated(FECHA);
    usuario2.setModified(FECHA);
    usuario2.setLastLogin(FECHA);
    usuario2.setActive(false);
    usuario2.setToken("ABC123");

    assertNotEquals(usuario, usuario2);
  }

  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setName("Juan");
    usuario.setEmail("juan@rodriguez.org");
    usuario.setPhones(Collections.emptyList());
    usuario.setPassword("hunter2");
    usuario.setCreated(FECHA);
    usuario.setModified(FECHA);
    usuario.setLastLogin(FECHA);
    usuario.setActive(true);
    usuario.setToken("ABC123");

    assertNotEquals(null, usuario);
  }

  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setName("Juan");
    usuario.setEmail("juan@rodriguez.org");
    usuario.setPhones(Collections.emptyList());
    usuario.setPassword("hunter2");
    usuario.setCreated(FECHA);
    usuario.setModified(FECHA);
    usuario.setLastLogin(FECHA);
    usuario.setActive(true);
    usuario.setToken("ABC123");

    assertNotEquals("Otro Objeto", usuario);
  }

  @Test
  void testGettersAndSetters() {
    Usuario usuario = new Usuario();
    usuario.setId(ID_USUARIO);
    usuario.setName("Juan");
    usuario.setEmail("juan@rodriguez.org");
    usuario.setPhones(Collections.emptyList());
    usuario.setPassword("hunter2");
    usuario.setCreated(FECHA);
    usuario.setModified(FECHA);
    usuario.setLastLogin(FECHA);
    usuario.setActive(true);
    usuario.setToken("ABC123");

    assertEquals("ABC123", usuario.getToken());
    assertEquals("Juan", usuario.getName());
    assertEquals("hunter2", usuario.getPassword());
    assertEquals("juan@rodriguez.org", usuario.getEmail());
    assertTrue(usuario.isActive());
    assertTrue(usuario.getPhones()
        .isEmpty());
    assertSame(FECHA, usuario.getCreated());
    assertSame(FECHA, usuario.getLastLogin());
    assertSame(FECHA, usuario.getModified());
    assertSame(ID_USUARIO, usuario.getId());
  }
}
