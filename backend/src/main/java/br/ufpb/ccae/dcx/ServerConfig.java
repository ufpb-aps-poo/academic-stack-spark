package br.ufpb.ccae.dcx;

import br.ufpb.ccae.dcx.controller.PerguntaController;
import br.ufpb.ccae.dcx.controller.RespostaController;
import io.javalin.Javalin;
import br.ufpb.ccae.dcx.controller.UsuarioController;
import io.javalin.http.Handler;
import io.javalin.plugin.bundled.CorsPluginConfig;

import static br.ufpb.ccae.dcx.middleware.Autenticacao.autenticar;

public class ServerConfig {
    public void start() {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";

            config.bundledPlugins.enableCors(cors -> {
                // Permite qualquer origem
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });

        }).start(7000);

        UsuarioController usuarioController = new UsuarioController();
        PerguntaController perguntaController = new PerguntaController();
        RespostaController respostaController = new RespostaController();

        // Rotas para usuários
        app.post("/login", usuarioController::login);
        app.post("/cadastrar", usuarioController::cadastrar);

        // Rotas para perguntas
        app.before(protegerRotasPerguntas());

        app.get("/perguntas", perguntaController::listar);
        app.post("/perguntas", perguntaController::criar);
        app.get("/perguntas/{id}", perguntaController::buscar);
        app.put("/perguntas/{id}", perguntaController::editar);
        app.delete("/perguntas/{id}", perguntaController::deletar);

        // Rotas para respostas
        app.get("/perguntas/{perguntaId}/respostas", respostaController::listarPorPergunta);
        app.post("/perguntas/{perguntaId}/respostas", respostaController::criar);

    }
    private static Handler protegerRotasPerguntas() {
        return ctx -> {
            String path = ctx.path().toLowerCase();
            String metodo = ctx.method().name();

            // Liberar sempre OPTIONS para CORS preflight sem autenticar
            if (metodo.equalsIgnoreCase("OPTIONS")) {
                ctx.status(200).result("OK");
                return; // devolve 200 OK sem autenticar
            }

            if (path.startsWith("/perguntas") && !metodo.equalsIgnoreCase("GET")) {
                autenticar(ctx);
            }
        };
    }
}
