package cl.banco.evaluacion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "telefono")
public class Telefono {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String number;

  private String cityCode;

  private String countryCode;
}