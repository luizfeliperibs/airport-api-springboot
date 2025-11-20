package airport.dto;

import airport.model.Aeroporto; // (ou airport.aeroporto.Aeroporto)
import java.math.BigDecimal;

public record DadosDetalhamentoAeroporto(
        Long id,
        String nomeAeroporto,
        String codigoIata,
        String cidade,
        String codigoPaisIso,
        Double latitude,
        Double longitude,
        Double altitude
) {
    public DadosDetalhamentoAeroporto(Aeroporto aeroporto) {
        this(
                aeroporto.getId_aeroporto(),
                aeroporto.getNomeAeroporto(),
                aeroporto.getCodigoIata(),
                aeroporto.getCidade(),
                aeroporto.getCodigoPaisIso(),
                aeroporto.getLatitude(),
                aeroporto.getLongitude(),
                aeroporto.getAltitude()
        );
    }
}