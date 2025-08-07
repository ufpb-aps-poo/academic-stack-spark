package br.ufpb.ccae.dcx;

import br.ufpb.ccae.dcx.model.Pergunta;
import br.ufpb.ccae.dcx.service.PerguntaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PerguntaServiceTest {

    private PerguntaService perguntaService;

    @BeforeEach
    public void setup() {
        perguntaService = new PerguntaService();

        // Criar perguntas de teste
        Pergunta p1 = new Pergunta();
        p1.setId(1);
        p1.setTitulo("Pergunta 1");
        p1.setConteudo("Conteúdo da pergunta 1");

        Pergunta p2 = new Pergunta();
        p2.setId(2);
        p2.setTitulo("Pergunta 2");
        p2.setConteudo("Conteúdo da pergunta 2");

        perguntaService.criar(p1);
        perguntaService.criar(p2);
    }

    @Test
    public void listarDeveRetornarTodasAsPerguntas() {
        List<Pergunta> perguntas = perguntaService.listar();
        assertEquals(2, perguntas.size(), "Deve haver 2 perguntas cadastradas");
    }

    @Test
    public void buscarPerguntaExistenteDeveRetornarObjetoCorreto() {
        Pergunta p = perguntaService.buscar(1);
        assertNotNull(p, "Pergunta deve existir");
        assertEquals("Pergunta 1", p.getTitulo());
    }

    @Test
    public void buscarPerguntaInexistenteDeveRetornarNull() {
        Pergunta p = perguntaService.buscar(99);
        assertNull(p, "Pergunta não deve existir");
    }

    @Test
    public void criarPerguntaDeveAumentarLista() {
        Pergunta nova = new Pergunta();
        nova.setId(3);
        nova.setTitulo("Pergunta 3");
        nova.setConteudo("Conteúdo da pergunta 3");

        perguntaService.criar(nova);

        assertEquals(3, perguntaService.listar().size(), "Deve haver 3 perguntas cadastradas agora");
    }

    @Test
    public void atualizarPerguntaDeveAlterarDados() {
        Pergunta pAtualizada = new Pergunta();
        pAtualizada.setId(1);
        pAtualizada.setTitulo("Pergunta 1 - Atualizada");
        pAtualizada.setConteudo("Conteúdo atualizado da pergunta 1");

        perguntaService.atualizar(pAtualizada);

        Pergunta pBuscada = perguntaService.buscar(1);
        assertNotNull(pBuscada);
        assertEquals("Pergunta 1 - Atualizada", pBuscada.getTitulo());
        assertEquals("Conteúdo atualizado da pergunta 1", pBuscada.getConteudo());
    }

    @Test
    public void removerPerguntaExistenteDeveRemoverERetornarTrue() {
        boolean resultado = perguntaService.remover(2);
        assertTrue(resultado, "Remoção deve retornar true");
        assertNull(perguntaService.buscar(2), "Pergunta 2 deve ter sido removida");
        assertEquals(1, perguntaService.listar().size(), "Deve sobrar 1 pergunta após remoção");
    }

    @Test
    public void removerPerguntaInexistenteDeveRetornarFalse() {
        boolean resultado = perguntaService.remover(99);
        assertFalse(resultado, "Remoção de pergunta inexistente deve retornar false");
    }
}