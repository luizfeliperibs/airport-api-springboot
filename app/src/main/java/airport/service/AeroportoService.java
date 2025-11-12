package airport.service;

import airport.dto.DadosAtualizacaoAeroporto;
import airport.dto.DadosCadastroAeroporto;
import airport.model.Aeroporto;
import airport.exceptions.AeroportoNaoEncontradoException;
import airport.repository.AeroportoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportoService {

    @Autowired
    AeroportoRepository repository;

    public AeroportoService(AeroportoRepository repository) {
        this.repository = repository;
    }

    public List<Aeroporto> listarAeroportos() {
        return repository.findAll();
    }

    public Aeroporto buscarAeroportoByIata(String iata) {

        return repository.findByCodigoIata(iata)
                .orElseThrow(() -> new AeroportoNaoEncontradoException(
                        "Aeroporto com IATA '" + iata + "' não encontrado."
                ));
    }

    @Transactional
    public Aeroporto cadastrarAeroporto(DadosCadastroAeroporto dados) {

        if(repository.existsByCodigoIata(dados.codigoIata())) {
            throw new IllegalArgumentException("Código IATA já cadastrado.");
        }
        var aeroporto = new Aeroporto(dados);
        repository.save(aeroporto);
        return aeroporto;
    }

    @Transactional
    public Aeroporto editarAeroporto(String iata, DadosAtualizacaoAeroporto dados){

        Optional<Aeroporto> aeroportoOpt = repository.findByCodigoIata(iata);
        if (aeroportoOpt.isEmpty()) {
            return null;
        }

        Aeroporto aeroporto = aeroportoOpt.get();
        aeroporto.atualizarInformacoes(dados);
        return aeroporto;
    }

    // Em AeroportoService.java
    @Transactional
    public void excluirAeroporto(String iata){

        if (!repository.existsByCodigoIata(iata)) {
            throw new AeroportoNaoEncontradoException(
                    "Aeroporto com IATA '" + iata + "' não encontrado para exclusão."
            );
        }

        repository.deleteByCodigoIata(iata);
    }
}