package cl.banco.evaluacion.dto;

import cl.banco.evaluacion.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UsuarioBasicDTO(
    @Schema(name = "id", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @JsonProperty("id")
    UUID id,

    @Schema(name = "created", example = "2025-05-26T00:00Z")
    @JsonProperty("created")
    OffsetDateTime created,

    @Schema(name = "modified", example = "2025-05-26T00:00Z")
    @JsonProperty("modified")
    OffsetDateTime modified,

    @Schema(name = "lastLogin", example = "2025-05-26T00:00Z")
    @JsonProperty("lastLogin")
    OffsetDateTime lastLogin,

    @Schema(name = "token", example = "abc123def456ghi789")
    @JsonProperty("token")
    String token,

    @Schema(name = "isActive", example = "true")
    @JsonProperty("isActive")
    boolean isActive
) {
  public static UsuarioBasicDTO fromUser(Usuario user) {
    return new UsuarioBasicDTO(
        user.getId(),
        user.getCreated(),
        user.getModified(),
        user.getLastLogin(),
        user.getToken(),
        user.isActive()
    );
  }
}
