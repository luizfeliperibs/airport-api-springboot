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



}