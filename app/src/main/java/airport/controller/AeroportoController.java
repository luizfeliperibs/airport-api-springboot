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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aeroportos")
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

        // URI agora é baseada no IATA
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
        Optional<Aeroporto> aeroportoOpt = service.buscarAeroportoByIata(iata);

        if (aeroportoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoAeroporto(aeroportoOpt.get()));
    }

    @PutMapping("/{iata}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoAeroporto> atualizar(
            @PathVariable String iata,
            @RequestBody @Valid DadosAtualizacaoAeroporto dados) {

        Aeroporto aeroportoAtualizado = service.editarAeroporto(iata, dados);

        if (aeroportoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoAeroporto(aeroportoAtualizado));
    }

    @DeleteMapping("/{iata}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable String iata) {
        boolean sucesso = service.excluirAeroporto(iata);

        if (!sucesso) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}