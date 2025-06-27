package cl.banco.evaluacion.service.impl;

import cl.banco.evaluacion.model.Usuario;
import cl.banco.evaluacion.repository.UsuarioRepository;
import cl.banco.evaluacion.service.MyUserDetailsService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

  private final UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByEmail(email);
    return new User(
        usuario.getEmail(),
        "{noop}" + usuario.getPassword(),
        Collections.emptyList()
    );
  }
}
