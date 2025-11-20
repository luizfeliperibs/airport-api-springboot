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
import static org.mockito.Mockito.when;


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


}