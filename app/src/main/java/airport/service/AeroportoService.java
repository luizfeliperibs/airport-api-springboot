package airport.service;

import airport.dto.DadosCadastroAeroporto;
import airport.model.Aeroporto;
import airport.repository.AeroportoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportoService {

    @Autowired
    AeroportoRepository repository;

    public AeroportoService(AeroportoRepository repository) {}

    public List<Aeroporto> listarAeroportos() {
        return repository.findAll();
    }

    public Optional<Aeroporto> buscarAeroportoById(Long id) {
        return repository.findById(id);
    }

    public Aeroporto cadastrarAeroporto(DadosCadastroAeroporto dados) {
        var aeroporto = new Aeroporto(dados);
        repository.save(aeroporto);

        return aeroporto;
    }

    public Aeroporto editarAeroporto(Long id, DadosCadastroAeroporto dados){
        var aeroporto = repository.getReferenceById(id);
        aeroporto.atualizarInformacoes(dados);

        return aeroporto;
    }

}
