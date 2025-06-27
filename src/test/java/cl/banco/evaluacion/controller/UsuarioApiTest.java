package cl.banco.evaluacion.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.banco.evaluacion.controller.impl.UsuarioController;
import cl.banco.evaluacion.dto.UsuarioFullResponseDTO;
import cl.banco.evaluacion.dto.UsuarioRequestDTO;
import cl.banco.evaluacion.dto.UsuarioResponseDTO;
import cl.banco.evaluacion.fixtures.UsuarioFixture;
import cl.banco.evaluacion.model.Usuario;
import cl.banco.evaluacion.repository.UsuarioRepository;
import cl.banco.evaluacion.service.impl.UsuarioServiceImpl;
import cl.banco.evaluacion.util.JwtUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UsuarioApi.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioApiTest {

  private static final UUID ID_USUARIO = UUID.randomUUID();
  private static final OffsetDateTime FECHA = OffsetDateTime.of(LocalDate.of(1970, 1, 1),
      LocalTime.MIDNIGHT, ZoneOffset.UTC);

  @MockitoBean
  private UsuarioApi usuarioApi;

  @Test
  void testCrearUsuario() {
    ResponseEntity<?> responseEntity = new ResponseEntity<>(
        HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    Mockito.<ResponseEntity<?>>when(usuarioApi.crearUsuario(Mockito.any()))
        .thenReturn(responseEntity);

    ResponseEntity<?> actualCreateUserResult = usuarioApi.crearUsuario(
        new UsuarioRequestDTO("Name", "jane.doe@example.org", "testtest", new ArrayList<>()));

    verify(usuarioApi).crearUsuario(isA(UsuarioRequestDTO.class));
    assertSame(responseEntity, actualCreateUserResult);
  }

  @Test
  void testGetAllUsers() {
    UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

    ResponseEntity<List<UsuarioResponseDTO>> actualAllUsers = (new UsuarioController(
        new UsuarioServiceImpl(usuarioRepository, new JwtUtil()))).listaUsuarios();

    verify(usuarioRepository).findAll();
    HttpStatusCode statusCode = actualAllUsers.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    assertEquals(200, actualAllUsers.getStatusCode()
        .value());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(Objects.requireNonNull(actualAllUsers.getBody())
        .isEmpty());
    assertTrue(actualAllUsers.hasBody());
    assertTrue(actualAllUsers.getHeaders()
        .isEmpty());
  }

  @Test
  void testGetUser() {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    Optional<Usuario> ofResult = Optional.of(usuario);

    UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    when(usuarioRepository.findById(Mockito.any())).thenReturn(ofResult);
    UsuarioController usuarioController = new UsuarioController(
        new UsuarioServiceImpl(usuarioRepository, new JwtUtil()));

    ResponseEntity<UsuarioFullResponseDTO> usuarioActual = usuarioController.getUsuario(
        UUID.randomUUID());

    verify(usuarioRepository).findById(isA(UUID.class));
    HttpStatusCode statusCode = usuarioActual.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    UsuarioFullResponseDTO body = usuarioActual.getBody();
    assertEquals("ABC123", Objects.requireNonNull(body)
        .token());
    assertEquals("Juan", body.name());
    assertEquals("hunter2", body.password());
    assertEquals("juan@rodriguez.org", body.email());
    assertEquals(200, usuarioActual.getStatusCode()
        .value());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.active());
    assertTrue(body.phones()
        .isEmpty());
    assertTrue(usuarioActual.hasBody());
    assertTrue(usuarioActual.getHeaders()
        .isEmpty());
    assertSame(FECHA, body.created());
    assertSame(FECHA, body.modified());
    assertSame(FECHA, body.lastLogin());
    assertSame(ID_USUARIO, body.id());
  }
}
