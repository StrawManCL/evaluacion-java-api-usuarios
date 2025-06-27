package cl.banco.evaluacion.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.banco.evaluacion.dto.TelefonoDTO;
import cl.banco.evaluacion.dto.UsuarioBasicDTO;
import cl.banco.evaluacion.dto.UsuarioFullResponseDTO;
import cl.banco.evaluacion.dto.UsuarioRequestDTO;
import cl.banco.evaluacion.dto.UsuarioResponseDTO;
import cl.banco.evaluacion.exceptions.EmailAlreadyExistsException;
import cl.banco.evaluacion.fixtures.UsuarioFixture;
import cl.banco.evaluacion.model.Usuario;
import cl.banco.evaluacion.repository.UsuarioRepository;
import cl.banco.evaluacion.util.JwtUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UsuarioServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioServiceImplTest {

  private static final UUID ID_USUARIO = UUID.randomUUID();
  private static final OffsetDateTime FECHA = OffsetDateTime.of(LocalDate.of(1970, 1, 1),
      LocalTime.MIDNIGHT, ZoneOffset.UTC);

  @MockitoBean
  private JwtUtil jwtUtil;
  @MockitoBean
  private UsuarioRepository userRepository;
  @Autowired
  private UsuarioServiceImpl userServiceImpl;

  @Test
  void testFindAllUsers() {
    when(userRepository.findAll()).thenReturn(new ArrayList<>());

    List<UsuarioResponseDTO> actualFindAllUsersResult = userServiceImpl.findAll();

    verify(userRepository).findAll();
    assertTrue(actualFindAllUsersResult.isEmpty());
  }

  @Test
  void testFindAllUsers2() {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    List<Usuario> userList = new ArrayList<>();
    userList.add(usuario);
    when(userRepository.findAll()).thenReturn(userList);

    List<UsuarioResponseDTO> actualFindAllUsersResult = userServiceImpl.findAll();

    verify(userRepository).findAll();
    assertEquals(1, actualFindAllUsersResult.size());
    UsuarioResponseDTO getResult = actualFindAllUsersResult.getFirst();
    assertTrue(getResult.active());
    assertSame(FECHA, getResult.lastLogin());
    assertSame(ID_USUARIO, getResult.id());
  }

  @Test
  void testFindAllUsers4() {
    when(userRepository.findAll()).thenThrow(new EmailAlreadyExistsException("An error occurred"));

    assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.findAll());
    verify(userRepository).findAll();
  }

  @Test
  void testFindUserById() {
    UUID id = UUID.randomUUID();
    OffsetDateTime fecha = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);

    Optional<Usuario> usuario = Optional.of(UsuarioFixture.usuarioSinTelefono(id, fecha));

    when(userRepository.findById(Mockito.any())).thenReturn(usuario);

    Optional<UsuarioFullResponseDTO> findByIdResult = userServiceImpl.findById(UUID.randomUUID());

    verify(userRepository).findById(isA(UUID.class));
    assertTrue(findByIdResult.isPresent());
    UsuarioFullResponseDTO getResult = findByIdResult.get();
    assertEquals("ABC123", getResult.token());
    assertEquals("Juan", getResult.name());
    assertEquals("hunter2", getResult.password());
    assertEquals("juan@rodriguez.org", getResult.email());
    assertTrue(getResult.active());
    assertTrue(getResult.phones()
        .isEmpty());
    assertSame(id, getResult.id());
    assertSame(fecha, getResult.created());
    assertSame(fecha, getResult.modified());
    assertSame(fecha, getResult.lastLogin());
  }

  @Test
  void testCreateUser() throws EmailAlreadyExistsException {
    when(userRepository.existsByEmail(Mockito.any())).thenReturn(true);

    assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", new ArrayList<>())));
    verify(userRepository).existsByEmail(eq("juan@rodriguez.org"));
  }

  @Test
  void testCreateUser2() throws EmailAlreadyExistsException {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);
    when(userRepository.save(Mockito.any())).thenReturn(usuario);
    when(jwtUtil.generateToken(Mockito.any())).thenReturn("ABC123");

    UsuarioBasicDTO actualCreateUserResult = userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", new ArrayList<>()));

    verify(userRepository).existsByEmail(eq("juan@rodriguez.org"));
    verify(jwtUtil).generateToken(eq("juan@rodriguez.org"));
    verify(userRepository).save(isA(Usuario.class));

    assertEquals("ABC123", actualCreateUserResult.token());
    assertTrue(actualCreateUserResult.isActive());
    ZoneOffset offset = actualCreateUserResult.created()
        .getOffset();
    assertSame(offset, actualCreateUserResult.lastLogin()
        .getOffset());
    assertSame(offset, actualCreateUserResult.modified()
        .getOffset());
  }

  @Test
  void testCreateUser3() throws EmailAlreadyExistsException {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);
    when(userRepository.save(Mockito.any())).thenReturn(usuario);
    when(jwtUtil.generateToken(Mockito.any())).thenReturn("ABC123");

    ArrayList<TelefonoDTO> phones = new ArrayList<>();
    phones.add(new TelefonoDTO("1234", "2", "56"));

    UsuarioBasicDTO actualCreateUserResult = userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "iloveyou", phones));

    verify(userRepository).existsByEmail(eq("juan@rodriguez.org"));
    verify(jwtUtil).generateToken(eq("juan@rodriguez.org"));
    verify(userRepository).save(isA(Usuario.class));
    assertEquals("ABC123", actualCreateUserResult.token());
    assertTrue(actualCreateUserResult.isActive());
    ZoneOffset offset = actualCreateUserResult.created()
        .getOffset();
    assertSame(offset, actualCreateUserResult.lastLogin()
        .getOffset());
    assertSame(offset, actualCreateUserResult.modified()
        .getOffset());
  }

  @Test
  void testCreateUser4() throws EmailAlreadyExistsException {
    Usuario usuario = new Usuario();
    usuario.setActive(true);
    usuario.setCreated(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    usuario.setEmail("jane.doe@example.org");
    usuario.setId(UUID.randomUUID());
    usuario.setLastLogin(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    usuario.setModified(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    usuario.setName("Name");
    usuario.setPassword("iloveyou");
    usuario.setPhones(new ArrayList<>());
    usuario.setToken("ABC123");
    when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);
    when(userRepository.save(Mockito.any())).thenReturn(usuario);
    when(jwtUtil.generateToken(Mockito.any())).thenReturn("ABC123");

    ArrayList<TelefonoDTO> phones = new ArrayList<>();
    phones.add(new TelefonoDTO("42", "Oxford", "GB"));
    phones.add(new TelefonoDTO("42", "Oxford", "GB"));

    UsuarioBasicDTO actualCreateUserResult = userServiceImpl.create(
        new UsuarioRequestDTO("Name", "jane.doe@example.org", "iloveyou", phones));

    verify(userRepository).existsByEmail(eq("jane.doe@example.org"));
    verify(jwtUtil).generateToken(eq("jane.doe@example.org"));
    verify(userRepository).save(isA(Usuario.class));
    assertEquals("ABC123", actualCreateUserResult.token());
    assertTrue(actualCreateUserResult.isActive());
    ZoneOffset offset = actualCreateUserResult.created()
        .getOffset();
    assertSame(offset, actualCreateUserResult.lastLogin()
        .getOffset());
    assertSame(offset, actualCreateUserResult.modified()
        .getOffset());
  }

  @Test
  void testCreateUser5() throws EmailAlreadyExistsException {
    when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);
    when(jwtUtil.generateToken(Mockito.any())).thenThrow(
        new EmailAlreadyExistsException("An error occurred"));

    assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", new ArrayList<>())));
    verify(userRepository).existsByEmail(eq("juan@rodriguez.org"));
    verify(jwtUtil).generateToken(eq("juan@rodriguez.org"));
  }
}
