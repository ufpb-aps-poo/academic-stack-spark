package br.ufpb.ccae.dcx.controller;

import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.service.UsuarioService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class UsuarioController {
    private final UsuarioService service = new UsuarioService();

    public void login(Context ctx) {
        Usuario credenciais = ctx.bodyAsClass(Usuario.class);
        Usuario usuario = service.autenticar(credenciais.getEmail(), credenciais.getSenha());
        Map<String, Object> resposta = new HashMap<>();
        if (usuario != null) {
            resposta.put("token", "fake-jwt-token-" + usuario.getId());

            Map<String, Object> user = new HashMap<>();
            user.put("id", usuario.getId());
            user.put("nome", usuario.getNome());
            user.put("email", usuario.getEmail());

            resposta.put("user", user);

            ctx.json(resposta);
        } else {
            resposta.put("messagem","Credenciais inválidas");
            ctx.status(401).json(resposta);
        }
    }

    public void cadastrar(Context ctx) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            service.cadastrar(usuario);
            resposta.put("mensagem", "Usuário cadastrado com sucesso");
            ctx.status(201).json(resposta);
        } catch (Exception e) {
            resposta.put("mensagem", e.getMessage());
            ctx.status(400).json(resposta);
        }
    }
}
