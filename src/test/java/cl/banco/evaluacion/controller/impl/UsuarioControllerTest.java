package cl.banco.evaluacion.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.banco.evaluacion.dto.UsuarioBasicDTO;
import cl.banco.evaluacion.dto.UsuarioRequestDTO;
import cl.banco.evaluacion.service.UsuarioService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UsuarioController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioControllerTest {

  private static final UUID ID_USUARIO = UUID.randomUUID();
  private static final OffsetDateTime FECHA = OffsetDateTime.of(LocalDate.of(1970, 1, 1),
      LocalTime.MIDNIGHT, ZoneOffset.UTC);

  @Autowired
  private UsuarioController usuarioController;

  @MockitoBean
  private UsuarioService usuarioService;

  @Test
  void testCreateUser() {
    UsuarioBasicDTO usuarioBasicDTO = new UsuarioBasicDTO(ID_USUARIO, FECHA, FECHA, FECHA, "ABC123",
        true);

    when(usuarioService.create(Mockito.any())).thenReturn(usuarioBasicDTO);

    ResponseEntity<?> actualCreateUserResult = usuarioController.crearUsuario(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", Collections.emptyList()));

    verify(usuarioService).create(isA(UsuarioRequestDTO.class));
    HttpStatusCode statusCode = actualCreateUserResult.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    assertEquals(201, actualCreateUserResult.getStatusCode()
        .value());
    assertEquals(HttpStatus.CREATED, statusCode);
    assertTrue(actualCreateUserResult.hasBody());
    assertTrue(actualCreateUserResult.getHeaders()
        .isEmpty());
    assertSame(usuarioBasicDTO, actualCreateUserResult.getBody());
  }
}
