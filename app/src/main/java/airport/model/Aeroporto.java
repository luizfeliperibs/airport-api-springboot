package airport.model;

import airport.dto.DadosAtualizacaoAeroporto;
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
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public Aeroporto (DadosCadastroAeroporto dados){

        this.nomeAeroporto = dados.nomeAeroporto();
        this.codigoIata = dados.codigoIata();
        this.cidade = dados.cidade();
        this.codigoPaisIso=dados.codigoPaisIso();
        this.latitude = dados.latitude();
        this.longitude = dados.longitude();
        this.altitude = dados.altitude();

    }

    public void atualizarInformacoes(DadosAtualizacaoAeroporto dados) {

        if(dados.nomeAeroporto() != null){
            this.nomeAeroporto = dados.nomeAeroporto();
        }

        if(dados.codigoIata() != null){
            this.codigoIata = dados.codigoIata();
        }

        if(dados.cidade() != null){
            this.cidade = dados.cidade();
        }

        if(dados.codigoPaisIso() != null){
            this.codigoPaisIso = dados.codigoPaisIso();
        }

        if(dados.latitude() != null){
            this.latitude = dados.latitude();
        }

        if(dados.longitude() != null){
            this.longitude = dados.longitude();
        }

        if(dados.altitude() != null){
            this.altitude = dados.altitude();
        }

    }
}
