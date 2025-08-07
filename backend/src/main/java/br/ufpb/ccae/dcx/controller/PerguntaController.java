package br.ufpb.ccae.dcx.controller;

import br.ufpb.ccae.dcx.model.Pergunta;
import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.service.PerguntaService;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class PerguntaController {
    private final PerguntaService service = new PerguntaService();

    public void listar(Context ctx) {
        ctx.json(service.listar());
    }

    public void criar(Context ctx) {
        Pergunta pergunta = ctx.bodyAsClass(Pergunta.class);

        Usuario usuarioAutenticado = ctx.attribute("usuarioAutenticado");
        pergunta.setAutor(usuarioAutenticado);
        service.criar(pergunta);
        ctx.status(201).json(pergunta);
    }

    public void buscar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Pergunta pergunta = service.buscar(id);
        if (pergunta == null) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Pergunta não encontrada");
            ctx.status(404).json(resposta);
        } else {
            ctx.json(pergunta);
        }
    }

    public void editar(Context ctx) {
        Usuario usuarioAutenticado = ctx.attribute("usuarioAutenticado");

        int id = Integer.parseInt(ctx.pathParam("id"));
        Pergunta pergunta = service.buscar(id);
        Map<String, Object> resposta = new HashMap<>();
        if (pergunta == null) {

            resposta.put("mensagem", "Pergunta não encontrada");
            ctx.status(404).json(resposta);
            return;
        }

        if (pergunta.getAutor() == null || pergunta.getAutor().getId() != usuarioAutenticado.getId()) {
            resposta.put("mensagem", "Usuário não autorizado a editar esta pergunta");
            ctx.status(403).json(resposta);
            return;
        }

        Pergunta dadosAtualizados = ctx.bodyAsClass(Pergunta.class);
        pergunta.setTitulo(dadosAtualizados.getTitulo());
        pergunta.setConteudo(dadosAtualizados.getConteudo());

        service.atualizar(pergunta);
        ctx.json(pergunta);
    }

    public void deletar(Context ctx) {
        Usuario usuarioAutenticado = ctx.attribute("usuarioAutenticado");

        int id = Integer.parseInt(ctx.pathParam("id"));
        Pergunta pergunta = service.buscar(id);
        Map<String, Object> resposta = new HashMap<>();
        if (pergunta == null) {
            resposta.put("mensagem", "Pergunta não encontrada");
            ctx.status(404).json(resposta);
            return;
        }

        if (pergunta.getAutor() == null || pergunta.getAutor().getId() != usuarioAutenticado.getId()) {
            resposta.put("mensagem", "Usuário não autorizado a deletar esta pergunta");
            ctx.status(403).json(resposta);
            return;
        }

        boolean removido = service.remover(id);
        if (removido) {
            ctx.status(204);
        } else {
            resposta.put("mensagem", "Erro ao remover pergunta");
            ctx.status(500).json(resposta);
        }
    }
}
