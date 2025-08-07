package br.ufpb.ccae.dcx.repository;

import br.ufpb.ccae.dcx.model.Resposta;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RespostaRepository {
    private final List<Resposta> respostas = new ArrayList<>();
    private int nextId = 1;

    public List<Resposta> listarPorPergunta(int idPergunta) {
        return respostas.stream()
                .filter(r -> r.getIdPergunta() == idPergunta)
                .collect(Collectors.toList());
    }

    public void salvar(Resposta resposta) {
        resposta.setId(nextId++);
        respostas.add(resposta);
    }
}
