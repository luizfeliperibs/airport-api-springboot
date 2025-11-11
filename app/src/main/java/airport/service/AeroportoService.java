package airport.service;

import airport.dto.DadosCadastroAeroporto;
import airport.model.Aeroporto;
import airport.repository.AeroportoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AeroportoService {

    @Autowired
    AeroportoRepository repository;

    public AeroportoService(AeroportoRepository repository) {}

    public List<Aeroporto> listarAeroportos() {
        return repository.findAll();
    }

    public Aeroporto cadastrarAeroporto(DadosCadastroAeroporto dados) {
        var aeroporto = new Aeroporto(dados);
        repository.save(aeroporto);

        return aeroporto;
    }

}
