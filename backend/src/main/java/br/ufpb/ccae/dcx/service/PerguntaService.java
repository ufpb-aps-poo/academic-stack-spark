package br.ufpb.ccae.dcx.service;


import br.ufpb.ccae.dcx.model.Pergunta;
import br.ufpb.ccae.dcx.repository.PerguntaRepository;

import java.util.List;

public class PerguntaService {
    private final PerguntaRepository repo = new PerguntaRepository();

    public List<Pergunta> listar() { return repo.listar(); }
    public Pergunta buscar(int id) { return repo.buscar(id); }
    public void criar(Pergunta p) { repo.salvar(p); }

    public void atualizar(Pergunta pergunta) {
        repo.atualizar(pergunta);
    }

    public boolean remover(int id) {
        return repo.remover(id);
    }
}
