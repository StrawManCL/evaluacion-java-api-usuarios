package cl.banco.evaluacion.dto;

import cl.banco.evaluacion.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UsuarioResponseDTO(
    @Schema(name = "id", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @JsonProperty("id")
    UUID id,

    @Schema(name = "name", example = "Juan Rodriguez")
    @JsonProperty("name")
    String name,

    @Schema(name = "email", example = "juan@rodriguez.org")
    @JsonProperty("email")
    String email,

    @Schema(name = "phones", example = "[{\"number\": \"1234567\", \"citycode\": \"1\", \"countrycode\": \"57\"}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("phones")
    List<TelefonoDTO> phones,

    @Schema(name = "last_login", example = "2025-05-26T00:00Z")
    @JsonProperty("lastLogin")
    OffsetDateTime lastLogin,

    @Schema(name = "isactive", example = "true")
    @JsonProperty("isactive")
    boolean active
) {
  public static UsuarioResponseDTO fromUser(Usuario usuario) {
    return new UsuarioResponseDTO(
        usuario.getId(),
        usuario.getName(),
        usuario.getEmail(),
        usuario.getPhones()
            .stream()
            .map(telefono -> new TelefonoDTO(
                telefono.getNumber(),
                telefono.getCityCode(),
                telefono.getCountryCode()
            ))
            .toList(),
        usuario.getLastLogin(),
        usuario.isActive());
  }
}
