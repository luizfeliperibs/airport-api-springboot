package airport.controller;

import airport.dto.DadosCadastroAeroporto;
import airport.model.Aeroporto;
import airport.repository.AeroportoRepository;
import airport.service.AeroportoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aeroportos")
public class AeroportoController {

    @Autowired
    AeroportoService service;

    @GetMapping
    public List<Aeroporto> listarAeroportos() {
        return service.listarAeroportos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aeroporto> buscarPorId(@PathVariable Long id) {

        Optional<Aeroporto> aeroportoOptional = service.buscarAeroportoById(id);

        if (aeroportoOptional.isPresent()) {
            Aeroporto aeroportoEncontrado = aeroportoOptional.get();
            return ResponseEntity.ok(aeroportoEncontrado);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    @Transactional
    public ResponseEntity<Aeroporto> cadastrarAeroporto(@RequestBody @Valid DadosCadastroAeroporto dados, UriComponentsBuilder uriBuilder) {

        Aeroporto novoAeroporto = service.cadastrarAeroporto(dados);

        var uri = uriBuilder.path("aeroportos/{id}").buildAndExpand(novoAeroporto.getId_aeroporto()).toUri();

        return ResponseEntity.created(uri).body(novoAeroporto);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Aeroporto> editarAeroporto(@PathVariable Long id, @RequestBody DadosCadastroAeroporto dados){

        Aeroporto aeroportoAtualizado = service.editarAeroporto(id, dados);

        return ResponseEntity.ok(aeroportoAtualizado);

    }

}
