package airport.model;

import airport.dto.DadosCadastroAeroporto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="aeroporto")
@Table(name="aeroportos")
public class Aeroporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_aeroporto;
    private String nomeAeroporto;
    private String codigoIata;
    private String cidade;
    private String codigoPaisIso;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;

    public Aeroporto (DadosCadastroAeroporto dados){

        this.nomeAeroporto = dados.nomeAeroporto();
        this.codigoIata = dados.codigoIata();
        this.cidade = dados.cidade();
        this.codigoPaisIso=dados.codigoPaisIso();
        this.latitude = dados.latitude();
        this.longitude = dados.longitude();
        this.altitude = dados.altitude();

    }

}
