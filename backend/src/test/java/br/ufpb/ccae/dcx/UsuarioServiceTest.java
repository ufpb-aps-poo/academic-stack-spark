package br.ufpb.ccae.dcx;
import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    private static  UsuarioService usuarioService;

    @BeforeAll
    public static void setupOnce() {
        usuarioService = new UsuarioService();

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Teste");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("senha123");
        usuarioService.cadastrar(usuario);
    }

    @Test
    public void autenticarComCredenciaisValidasDeveRetornarUsuario() {
        Usuario usuario = usuarioService.autenticar("teste@teste.com", "senha123");
        assertNotNull(usuario, "Usuário deve ser autenticado com credenciais corretas");
        assertEquals("Teste", usuario.getNome());
    }

    @Test
    public void autenticarComSenhaInvalidaDeveRetornarNull() {
        Usuario usuario = usuarioService.autenticar("teste@teste.com", "senhaErrada");
        assertNull(usuario, "Usuário não deve ser autenticado com senha incorreta");
    }

    @Test
    public void autenticarComEmailInvalidoDeveRetornarNull() {
        Usuario usuario = usuarioService.autenticar("invalido@teste.com", "senha123");
        assertNull(usuario, "Usuário não deve ser autenticado com email inexistente");
    }
    @Test
    public void cadastrarUsuarioNovoDeveFuncionar() {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Novo Usuário");
        novoUsuario.setEmail("novo@teste.com");
        novoUsuario.setSenha("minhasenha");

        assertDoesNotThrow(() -> usuarioService.cadastrar(novoUsuario));

        Usuario buscado = usuarioService.autenticar("novo@teste.com", "minhasenha");
        assertNotNull(buscado, "Usuário novo deve ser autenticado");
        assertEquals("Novo Usuário", buscado.getNome());
    }

    @Test
    public void cadastrarUsuarioComEmailExistenteDeveLancarExcecao() {
        Usuario duplicado = new Usuario();
        duplicado.setNome("Duplicado");
        duplicado.setEmail("teste@teste.com");  // email já usado no setup
        duplicado.setSenha("senha123");

        Exception excecao = assertThrows(Exception.class, () -> {
            usuarioService.cadastrar(duplicado);
        });
        assertTrue(excecao.getMessage().contains("Email já cadastrado"));
    }
}

