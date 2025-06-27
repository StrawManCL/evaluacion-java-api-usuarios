package cl.banco.evaluacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TelefonoDTO(
        @Schema(
            name = "number",
            example = "1234567",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("number")
        String number,

        @Schema(
            name = "cityCode",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("cityCode")
        String cityCode,

        @Schema(
            name = "countryCode",
            example = "57",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("countryCode")
        String countryCode
) { }
