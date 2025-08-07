package br.ufpb.ccae.dcx.middleware;

import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.service.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

import java.io.IOException;

public class Autenticacao {

    private static final UsuarioService usuarioService = new UsuarioService();

    public static void autenticar(Context ctx) throws IOException {
        Usuario usuario = ctx.sessionAttribute("usuarioAutenticado");

        // Se não está na sessão, tenta pelo Bearer Token
        if (usuario == null) {
            String authHeader = ctx.header("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // remove "Bearer "
                usuario = validarToken(token);
            }
        }

        // Se não autenticou de nenhuma forma, bloqueia
        if (usuario == null) {
            throw new UnauthorizedResponse("Usuário não autenticado");
        } else {
            ctx.attribute("usuarioAutenticado", usuario);
        }
    }

    private static Usuario validarToken(String token) {
        // Exemplo: token "fake-jwt-token-{id}"
        if (token.startsWith("fake-jwt-token-")) {
            try {
                int id = Integer.parseInt(token.replace("fake-jwt-token-", ""));
                return usuarioService.buscar(id);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

}
