package br.ufpb.ccae.dcx.service;


import br.ufpb.ccae.dcx.model.Resposta;
import br.ufpb.ccae.dcx.repository.RespostaRepository;

import java.util.List;

public class RespostaService {
    private final RespostaRepository repo = new RespostaRepository();

    public List<Resposta> listarPorPergunta(int perguntaId) {
        return repo.listarPorPergunta(perguntaId);
    }

    public void criar(Resposta resposta) {
        repo.salvar(resposta);
    }
}
