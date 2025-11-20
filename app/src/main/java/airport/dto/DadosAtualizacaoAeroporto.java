package airport.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoAeroporto(
        String nomeAeroporto,
        String codigoIata,
        String cidade,
        String codigoPaisIso,
        Double latitude,
        Double longitude,
        Double altitude
) {

}