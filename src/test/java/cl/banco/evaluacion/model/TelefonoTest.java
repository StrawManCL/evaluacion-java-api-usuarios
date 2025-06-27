package cl.banco.evaluacion.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class TelefonoTest {

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumber("1234");
    telefono2.setCityCode("2");
    telefono2.setCountryCode("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode(null);
    telefono.setCountryCode("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumber("1234");
    telefono2.setCityCode(null);
    telefono2.setCountryCode("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode(null);

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumber("1234");
    telefono2.setCityCode("2");
    telefono2.setCountryCode(null);

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    Telefono telefono = new Telefono();
    telefono.setId(null);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(null);
    telefono2.setNumber("1234");
    telefono2.setCityCode("2");
    telefono2.setCountryCode("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber(null);
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumber(null);
    telefono2.setCityCode("2");
    telefono2.setCountryCode("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono.hashCode());
  }

  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumber("12345");
    telefono2.setCityCode("2");
    telefono2.setCountryCode("563");

    assertNotEquals(telefono, telefono2);
  }

  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    assertNotEquals(null, telefono);
  }

  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    assertNotEquals("Objeto diferente a Telefono", telefono);
  }

  @Test
  void testGettersAndSetters() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    assertEquals("1234", telefono.getNumber());
    assertEquals("2", telefono.getCityCode());
    assertEquals("56", telefono.getCountryCode());
    assertEquals("Telefono(id=1, number=1234, cityCode=2, countryCode=56)",
        telefono.toString());
    assertEquals(1L, telefono.getId());
  }

  @Test
  void testGettersAndSetters2() {
    Telefono telefono = new Telefono(1L, "1234", "2", "56");
    telefono.setId(1L);
    telefono.setNumber("1234");
    telefono.setCityCode("2");
    telefono.setCountryCode("56");

    assertEquals("1234", telefono.getNumber());
    assertEquals("2", telefono.getCityCode());
    assertEquals("56", telefono.getCountryCode());
    assertEquals("Telefono(id=1, number=1234, cityCode=2, countryCode=56)",
        telefono.toString());
    assertEquals(1L, telefono.getId());
  }
}
