package br.ufpb.ccae.dcx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import br.ufpb.ccae.dcx.model.Resposta;
import br.ufpb.ccae.dcx.model.Usuario;
import br.ufpb.ccae.dcx.service.RespostaService;

public class RespostaServiceTest {

    private RespostaService respostaService;

    @BeforeEach
    public void setup() {
        respostaService = new RespostaService();
    }

    @Test
    public void criarRespostaDeveSalvarComIdEAutor() {
        Usuario autor = new Usuario();
        autor.setId(1);
        autor.setNome("Autor Teste");

        Resposta resposta = new Resposta();
        resposta.setConteudo("Conteúdo da resposta");
        resposta.setAutor(autor);
        resposta.setIdPergunta(10);

        respostaService.criar(resposta);

        assertTrue(resposta.getId() > 0, "Resposta deve receber um id ao ser salva");
        assertEquals(autor, resposta.getAutor(), "Autor deve ser o mesmo atribuído");
        assertEquals(10, resposta.getIdPergunta(), "PerguntaId deve ser o mesmo atribuído");
    }

    @Test
    public void listarPorPerguntaDeveRetornarRespostasApenasDessaPergunta() {
        Usuario autor = new Usuario();
        autor.setId(1);
        autor.setNome("Autor Teste");

        // Resposta para pergunta 5
        Resposta r1 = new Resposta();
        r1.setConteudo("Resposta para pergunta 5");
        r1.setAutor(autor);
        r1.setIdPergunta(5);
        respostaService.criar(r1);

        // Resposta para pergunta 7
        Resposta r2 = new Resposta();
        r2.setConteudo("Resposta para pergunta 7");
        r2.setAutor(autor);
        r2.setIdPergunta(7);
        respostaService.criar(r2);

        List<Resposta> respostasPergunta5 = respostaService.listarPorPergunta(5);
        assertEquals(1, respostasPergunta5.size(), "Deve retornar uma resposta para pergunta 5");
        assertEquals("Resposta para pergunta 5", respostasPergunta5.get(0).getConteudo());

        List<Resposta> respostasPergunta7 = respostaService.listarPorPergunta(7);
        assertEquals(1, respostasPergunta7.size(), "Deve retornar uma resposta para pergunta 7");
        assertEquals("Resposta para pergunta 7", respostasPergunta7.get(0).getConteudo());
    }
}
