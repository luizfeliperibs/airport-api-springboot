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

@RestController
@RequestMapping("/aeroportos")
public class AeroportoController {

    @Autowired
    AeroportoService service;

    @GetMapping
    public List<Aeroporto> listarAeroportos() {
        return service.listarAeroportos();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Aeroporto> cadastrarAeroporto(@RequestBody @Valid DadosCadastroAeroporto dados, UriComponentsBuilder uriBuilder) {

        Aeroporto novoAeroporto = service.cadastrarAeroporto(dados);

        var uri = uriBuilder.path("aeroportos/{id}").buildAndExpand(novoAeroporto.getId_aeroporto()).toUri();

        return ResponseEntity.created(uri).body(novoAeroporto);

    }

}
