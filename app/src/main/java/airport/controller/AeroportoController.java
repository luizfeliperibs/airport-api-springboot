package airport.controller;

import airport.dto.DadosAtualizacaoAeroporto;
import airport.dto.DadosCadastroAeroporto;
import airport.dto.DadosDetalhamentoAeroporto;
import airport.model.Aeroporto;
import airport.service.AeroportoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aeroportos")
public class AeroportoController {

    private final AeroportoService service;

    public AeroportoController(AeroportoService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAeroporto> cadastrarAeroporto(
            @RequestBody @Valid DadosCadastroAeroporto dados,
            UriComponentsBuilder uriBuilder) {

        Aeroporto aeroportoSalvo = service.cadastrarAeroporto(dados);

        var uri = uriBuilder.path("/aeroportos/{iata}")
                .buildAndExpand(aeroportoSalvo.getCodigoIata())
                .toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoAeroporto(aeroportoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoAeroporto>> listar() {
        List<Aeroporto> listaAeroportos = service.listarAeroportos();
        List<DadosDetalhamentoAeroporto> listaDto = listaAeroportos.stream()
                .map(DadosDetalhamentoAeroporto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDto);
    }

    @GetMapping("/{iata}")
    public ResponseEntity<DadosDetalhamentoAeroporto> detalhar(@PathVariable String iata) {

        Aeroporto aeroporto = service.buscarAeroportoByIata(iata);
        return ResponseEntity.ok(new DadosDetalhamentoAeroporto(aeroporto));

    }

    @PutMapping("/{iata}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoAeroporto> atualizar(
            @PathVariable String iata,
            @RequestBody @Valid DadosAtualizacaoAeroporto dados) {

        Aeroporto aeroportoAtualizado = service.editarAeroporto(iata, dados);

        return ResponseEntity.ok(new DadosDetalhamentoAeroporto(aeroportoAtualizado));
    }

    // Em AeroportoController.java
    @DeleteMapping("/{iata}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable String iata) {
        service.excluirAeroporto(iata);
        return ResponseEntity.noContent().build();
    }
}