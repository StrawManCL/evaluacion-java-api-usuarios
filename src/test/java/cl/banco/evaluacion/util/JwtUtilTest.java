package cl.banco.evaluacion.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtUtil.class})
@ExtendWith(SpringExtension.class)
class JwtUtilTest {

  @Autowired
  private JwtUtil jwtUtil;

  @Test
  void testGenerateToken() {
    String token = jwtUtil.generateToken("User");
    assertNotNull(token);
  }
}
