package br.ufpb.ccae.dcx.controller;

import br.ufpb.ccae.dcx.model.Resposta;
import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.service.RespostaService;
import io.javalin.http.Context;

public class RespostaController {
    private final RespostaService service = new RespostaService();

    public void listarPorPergunta(Context ctx) {
        int perguntaId = Integer.parseInt(ctx.pathParam("perguntaId"));
        ctx.json(service.listarPorPergunta(perguntaId));
    }

    public void criar(Context ctx) {
        Usuario autor = ctx.attribute("usuarioAutenticado");

        int perguntaId = Integer.parseInt(ctx.pathParam("perguntaId"));
        Resposta resposta = ctx.bodyAsClass(Resposta.class);
        resposta.setAutor(autor);
        resposta.setIdPergunta(perguntaId);

        service.criar(resposta);
        ctx.status(201).json(resposta);
    }
}
