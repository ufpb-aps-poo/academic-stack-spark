package br.ufpb.ccae.dcx.repository;


import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.util.GerenciadorDeSenhas;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private int idCounter = 1;

    public UsuarioRepository() {
        // Usuário padrão para testes de login
        String senha = GerenciadorDeSenhas.gerarHashDaSenha("1234");
        Usuario usuarioPadrao  = new Usuario("Admin", "admin@email.com", senha);
        salvar(usuarioPadrao);
    }

    public void salvar(Usuario usuario) {
        usuario.setId(idCounter++);
        usuarios.add(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
