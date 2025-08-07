package br.ufpb.ccae.dcx.repository;

import br.ufpb.ccae.dcx.model.Pergunta;

import java.util.ArrayList;
import java.util.List;

public class PerguntaRepository {
    private final List<Pergunta> perguntas = new ArrayList<>();
    private int idCounter = 1;

    public List<Pergunta> listar() {
        return perguntas;
    }

    public Pergunta buscar(int id) {
        return perguntas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void salvar(Pergunta pergunta) {
        pergunta.setId(idCounter++);
        perguntas.add(pergunta);
    }

    public void atualizar(Pergunta pergunta) {
        Pergunta existing = buscar(pergunta.getId());
        if (existing != null) {
            existing.setTitulo(pergunta.getTitulo());
            existing.setConteudo(pergunta.getConteudo());
            existing.setAutor(pergunta.getAutor());
        }
    }

    public boolean remover(int id) {
        Pergunta pergunta = buscar(id);
        if (pergunta != null) {
            perguntas.remove(pergunta);
            return true;
        }
        return false;
    }
}

