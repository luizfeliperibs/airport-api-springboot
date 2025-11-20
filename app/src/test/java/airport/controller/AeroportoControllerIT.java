package airport.controller;

import airport.dto.DadosAtualizacaoAeroporto;
import airport.dto.DadosCadastroAeroporto;
import airport.model.Aeroporto;
import airport.repository.AeroportoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AeroportoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AeroportoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve cadastrar um aeroporto com sucesso (Status 201)")
    void deveCadastrarAeroporto() throws Exception {

        var dadosCadastro = new DadosCadastroAeroporto(
                "Aeroporto Internacional de Confins",
                "CNF",
                "Belo Horizonte",
                "BR",
                -19.624,
                -43.971,
                827.0
        );

        var json = objectMapper.writeValueAsString(dadosCadastro);

        mockMvc.perform(post("/aeroportos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.codigoIata").value("CNF"))
                .andExpect(jsonPath("$.nomeAeroporto").value("Aeroporto Internacional de Confins"));

        assertThat(repository.existsByCodigoIata("CNF")).isTrue();
    }

    @Test
    @DisplayName("Deve listar aeroportos (Status 200)")
    void deveListarAeroportos() throws Exception {

        criarAeroportoNoBanco("GRU", "Guarulhos");
        criarAeroportoNoBanco("GIG", "Galeão");

        mockMvc.perform(get("/aeroportos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].codigoIata").value("GRU"))
                .andExpect(jsonPath("$[1].codigoIata").value("GIG"));
    }

    @Test
    @DisplayName("Deve detalhar um aeroporto específico (Status 200)")
    void deveDetalharAeroporto() throws Exception {

        criarAeroportoNoBanco("SDU", "Santos Dumont");

        mockMvc.perform(get("/aeroportos/{iata}", "SDU"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoIata").value("SDU"))
                .andExpect(jsonPath("$.nomeAeroporto").value("Santos Dumont"));
    }

    @Test
    @DisplayName("Deve atualizar aeroporto (Status 200)")
    void deveAtualizarAeroporto() throws Exception {

        criarAeroportoNoBanco("VCP", "Viracopos Antigo");

        var dadosAtualizacao = new DadosAtualizacaoAeroporto(
                "Viracopos Atualizado",
                "VCP",
                "Campinas",
                "BR",
                0.0,
                0.0,
                0.0
        );
        var json = objectMapper.writeValueAsString(dadosAtualizacao);

        mockMvc.perform(put("/aeroportos/{iata}", "VCP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeAeroporto").value("Viracopos Atualizado"));

        Aeroporto aeroportoNoBanco = repository.findByCodigoIata("VCP").orElseThrow();
        assertThat(aeroportoNoBanco.getNomeAeroporto()).isEqualTo("Viracopos Atualizado");
    }

    @Test
    @DisplayName("Deve excluir aeroporto (Status 204)")
    void deveExcluirAeroporto() throws Exception {

        criarAeroportoNoBanco("BSB", "Brasilia");

        mockMvc.perform(delete("/aeroportos/{iata}", "BSB"))
                .andExpect(status().isNoContent()); // Espera 204

        assertThat(repository.existsByCodigoIata("BSB")).isFalse();
    }

    private void criarAeroportoNoBanco(String iata, String nome) {
        var dados = new DadosCadastroAeroporto(
                nome, iata, "Cidade Teste", "BR", 0.0, 0.0, 0.0
        );
        repository.save(new Aeroporto(dados));
    }

}