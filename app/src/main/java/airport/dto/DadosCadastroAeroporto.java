package airport.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DadosCadastroAeroporto(

        @NotBlank(message = "O nome do aeroporto é obrigatório.")
        String nomeAeroporto,

        @NotNull
        @Size(min = 3, max = 3, message = "O código IATA deve ter exatamente 3 caracteres.")
        @Pattern(regexp = "[A-Z]{3}", message = "O código IATA deve conter apenas letras maiúsculas.")
        String codigoIata,

        @NotBlank(message = "A cidade é obrigatória.")
        String cidade,

        @NotBlank(message = "O código do país (ISO) é obrigatório.")
        @Size(min = 2, max = 2, message = "O código do país deve ter exatamente 2 caracteres.")
        @Pattern(regexp = "[A-Z]{2}", message = "O código do país deve conter apenas letras maiúsculas.")
        String codigoPaisIso,

        @NotNull(message = "A latitude é obrigatória.")
        BigDecimal latitude,

        @NotNull(message = "A longitude é obrigatória.")
        BigDecimal longitude,

        BigDecimal altitude

){
}
