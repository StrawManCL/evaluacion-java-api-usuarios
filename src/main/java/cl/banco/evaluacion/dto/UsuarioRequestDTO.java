package cl.banco.evaluacion.dto;

import cl.banco.evaluacion.validation.ValidEmail;
import cl.banco.evaluacion.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioRequestDTO(
        @Schema(name = "name", example = "Juan Rodriguez", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("name")
        @NotBlank(message = "Nombre es obligatorio")
        String name,

        @Schema(name = "email", example = "juan@rodriguez.org", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Correo electrónico válido (por ejemplo, juan@rodriguez.org)")
        @JsonProperty("email")
        @ValidEmail
        String email,

        @Schema(name = "password", example = "hunter2", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Contraseña debe contener al menos 6 caracteres, minúsculas y números")
        @JsonProperty("password")
        @ValidPassword
        String password,

        @Schema(name = "phones", example = "[{\"number\": \"1234567\", \"cityCode\": \"1\", \"countryCode\": \"57\"}]",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("phones")
        List<TelefonoDTO> phones
) { }