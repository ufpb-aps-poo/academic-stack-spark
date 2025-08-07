package br.ufpb.ccae.dcx.service;

import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.repository.UsuarioRepository;
import br.ufpb.ccae.dcx.util.GerenciadorDeSenhas;
import io.javalin.http.ConflictResponse;

public class UsuarioService {
    private final UsuarioRepository repo = new UsuarioRepository();

    public void cadastrar(Usuario usuario)  {
        if (repo.buscarPorEmail(usuario.getEmail()) != null) {
            throw new ConflictResponse("Email já cadastrado");
        }
        usuario.setSenha(GerenciadorDeSenhas.gerarHashDaSenha(usuario.getSenha()));
        repo.salvar(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = repo.buscarPorEmail(email);
        if (usuario != null && GerenciadorDeSenhas.verificarSenha(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    public Usuario buscar(int id) {
        return repo.buscarPorId(id);
    }
}
