// Em airport/dto/DadosAtualizacaoAeroporto.java
package airport.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoAeroporto(
        @NotNull
        Long id,
        String nomeAeroporto,
        String codigoIata,
        String cidade,
        String codigoPaisIso,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal altitude
) {
}