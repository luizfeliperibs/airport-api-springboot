package airport.service;

import airport.dto.DadosCadastroAeroporto;
import airport.exceptions.AeroportoNaoEncontradoException;
import airport.model.Aeroporto;
import airport.repository.AeroportoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AeroportoServiceTest {

    @Mock
    private AeroportoRepository repository;

    @InjectMocks
    private AeroportoService service;

    @Test
    @DisplayName("Deve lançar exceção ao buscar IATA que não existe")
    void deveLancarExcecaoQuandoBuscarIataNaoExistente() {

        String iataInexistente = "XXX";
        when(repository.findByCodigoIata(iataInexistente))
                .thenReturn(Optional.empty());

        assertThrows(AeroportoNaoEncontradoException.class, () -> {
            service.buscarAeroportoByIata(iataInexistente);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar IATA duplicado")
    void deveLancarExcecaoAoCadastrarIataDuplicado() {

        DadosCadastroAeroporto dadosAeroporto = new DadosCadastroAeroporto(
                "Aeroporto Teste", "GRU", "Sao Paulo", "BR",
                -23.4, -46.4, 100.0);

        when(repository.existsByCodigoIata("GRU")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarAeroporto(dadosAeroporto);
        });
    }

    @Test
    @DisplayName("Deve cadastrar aeroporto com sucesso quando IATA é único")
    void deveCadastrarAeroportoComSucesso() {

        DadosCadastroAeroporto dados = new DadosCadastroAeroporto(
                "Aeroporto de Confins", "CNF", "Belo Horizonte", "BR",
                -19.6, -43.9, 827.0);

        when(repository.existsByCodigoIata("CNF")).thenReturn(false);

        Aeroporto resultado = service.cadastrarAeroporto(dados);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodigoIata()).isEqualTo("CNF");

        verify(repository, times(1)).save(any(Aeroporto.class));
    }

    @Test
    @DisplayName("Deve retornar aeroporto quando buscar por IATA existente")
    void deveRetornarAeroportoQuandoBuscarIataExistente() {

        String iata = "GIG";
        Aeroporto aeroportoEsperado = new Aeroporto();
        aeroportoEsperado.setCodigoIata(iata);

        when(repository.findByCodigoIata(iata))
                .thenReturn(Optional.of(aeroportoEsperado));

        Aeroporto resultado = service.buscarAeroportoByIata(iata);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodigoIata()).isEqualTo("GIG");
    }

    @Test
    @DisplayName("Deve excluir aeroporto com sucesso quando IATA existe")
    void deveExcluirAeroportoComSucesso() {

        String iata = "SDU";

        when(repository.existsByCodigoIata(iata)).thenReturn(true);

        service.excluirAeroporto(iata);

        verify(repository, times(1)).deleteByCodigoIata(iata);
    }


}