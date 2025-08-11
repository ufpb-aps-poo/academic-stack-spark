# Roteiro do Projeto Semanal: Evoluindo o Academic Stack

Este documento serve como um guia semanal para o projeto da disciplina de Programação. O objetivo é evoluir, de forma incremental, o sistema **Academic Stack** (um clone do Stack Overflow), aplicando conceitos essenciais de design de software, arquitetura e padrões de projeto diretamente no código-fonte existente.

**Projeto Base:** Academic Stack (Backend Java/Javalin, Frontend TypeScript/React)
**Foco:** Backend em Java
**Testes:** JUnit

---

## Semana 1: Padrões GRASP e Análise de Responsabilidades

**Conceito:** GRASP (General Responsibility Assignment Software Patterns) nos ajuda a decidir onde colocar a lógica de negócio. Focaremos em **Especialista (Information Expert)** e **Controlador (Controller)** para analisar e validar a estrutura atual do nosso sistema.

**Análise do Projeto Atual:**
O projeto já possui uma boa separação de responsabilidades inicial:
-   **Controllers:** Recebem requisições HTTP e delegam para os serviços. (Ex: `UsuarioController`).
-   **Services:** Contêm a lógica de negócio (atualmente simples). (Ex: `UsuarioService`).
-   **Repositories:** Abstraem o acesso aos dados (atualmente em memória). (Ex: `UsuarioRepository`).
-   **Models:** Representam os dados do domínio. (Ex: `Usuario`, `Pergunta`).

Esta semana, o objetivo é formalizar essa análise e fazer pequenas melhorias.

### Tarefas da Semana

1.  **Documentar as Responsabilidades (GRASP):**
    -   **Descrição:** Para cada classe de `Model`, `Repository`, `Service` e `Controller`, escreva um pequeno comentário no topo do arquivo explicando qual a sua principal responsabilidade e qual padrão GRASP ela melhor representa.
    -   **Exemplo (`UsuarioRepository.java`):** `// Responsabilidade: Gerenciar a coleção de objetos Usuario em memória. Padrão GRASP: Especialista da Informação em relação à lista de usuários.`

2.  **Analisar o `SalesController`:**
    -   **Descrição:** O padrão **Controlador** sugere que o controlador deve ser leve, delegando o trabalho para a camada de serviço. Analise o `UsuarioController`, `PerguntaController` e `RespostaController`. Eles estão delegando toda a lógica de negócio ou estão fazendo mais do que deveriam?
    -   **Ação:** Discuta em grupo se há alguma lógica (ex: validação complexa) que poderia ser movida do controlador para o serviço. Por enquanto, apenas documente as conclusões.

3.  **Refinar o Especialista: `Usuario.java`:**
    -   **Descrição:** O `Usuario` é o especialista em seus próprios dados. No entanto, o `UsuarioService` atualmente lida com a lógica de senha. Vamos mover a responsabilidade de *verificar* a senha para a própria classe `Usuario`.
    -   **Ação:** Adicione um método `public boolean verificarSenha(String senhaFornecida)` dentro da classe `Usuario`. Este método usará o `GerenciadorDeSenhas` para comparar a `senhaFornecida` com a senha armazenada (`this.senha`).

4.  **Refinar o Especialista: `Pergunta.java`:**
    -   **Descrição:** A classe `Pergunta` é a especialista em suas respostas.
    -   **Ação:** Adicione um método `public void adicionarResposta(Resposta resposta)` na classe `Pergunta`. Este método simplesmente adiciona uma resposta à sua lista interna de `respostas`. O `RespostaService` usará este método no futuro.

5.  **Escrever Testes para os Novos Métodos:**
    -   **Descrição:** Valide os novos métodos de negócio adicionados aos modelos.
    -   **Ação:** Crie/atualize as classes de teste para `UsuarioTest` e `PerguntaTest`. Teste o método `verificarSenha` (com senhas corretas e incorretas) e o método `adicionarResposta`.

### Testes JUnit

```java
// Exemplo de teste para Usuario.java
class UsuarioTest {
    private final GerenciadorDeSenhas gerenciador = new GerenciadorDeSenhas();

    @Test
    void testVerificarSenha() {
        String senhaOriginal = "senha123";
        String hash = gerenciador.hashSenha(senhaOriginal);
        Usuario usuario = new Usuario("Teste", "teste@email.com", hash);

        assertTrue(usuario.verificarSenha(senhaOriginal));
        assertFalse(usuario.verificarSenha("senhaErrada"));
    }
}

// Exemplo de teste para Pergunta.java
class PerguntaTest {
    @Test
    void testAdicionarResposta() {
        Pergunta pergunta = new Pergunta("Título", "Conteúdo", new Usuario());
        assertEquals(0, pergunta.getRespostas().size());

        pergunta.adicionarResposta(new Resposta());
        assertEquals(1, pergunta.getRespostas().size());
    }
}
```

---

## Semana 2: Princípios SOLID - LSP e ISP

**Conceitos:**
-   **LSP (Liskov Substitution Principle):** Subtipos devem ser substituíveis por seus tipos base.
-   **ISP (Interface Segregation Principle):** Clientes não devem ser forçados a depender de interfaces que não usam.

**Ideia de Aplicação:** Introduzir diferentes tipos de `Usuario` (Admin, Membro) para aplicar LSP. Introduzir o conceito de "Votos" em Perguntas e Respostas usando uma interface segregada (ISP).

### Tarefas da Semana

1.  **Aplicar LSP: Abstrair `Usuario`:**
    -   **Descrição:** Nem todo usuário terá as mesmas permissões. Vamos criar uma hierarquia de usuários.
    -   **Ação:** Transforme `Usuario` em uma classe abstrata. Crie duas classes concretas que herdam de `Usuario`: `Membro` e `Admin`. Por enquanto, elas não precisam ter lógicas diferentes, apenas existir.

2.  **Refatorar Serviços para Usar a Abstração:**
    -   **Descrição:** O `UsuarioService` e outros locais que usam `Usuario` agora devem se referir à classe base abstrata, permitindo que um `Membro` ou `Admin` seja usado no lugar.
    -   **Ação:** Altere as assinaturas de métodos e variáveis de `new Usuario(...)` para `new Membro(...)` onde fizer sentido. Garanta que os testes continuem passando.

3.  **Aplicar ISP: Criar Interface `Votable`:**
    -   **Descrição:** Tanto `Pergunta` quanto `Resposta` podem receber votos (upvotes/downvotes). Esta é uma funcionalidade comum que pode ser abstraída.
    -   **Ação:** Crie uma interface `Votable` com os métodos `void upvote()`, `void downvote()` e `int getVotos()`.

4.  **Implementar `Votable`:**
    -   **Descrição:** Faça `Pergunta` e `Resposta` implementarem a nova interface.
    -   **Ação:** Adicione um atributo `private int votos = 0;` em ambas as classes. Implemente os métodos da interface para incrementar, decrementar e retornar o valor deste atributo.

5.  **Criar um `VotoController`:**
    -   **Descrição:** Crie um novo controlador para lidar com as ações de voto.
    -   **Ação:** Crie `VotoController` com dois endpoints: `POST /perguntas/{id}/upvote` e `POST /respostas/{id}/upvote` (e os análogos para downvote). Este controlador encontrará o objeto (`Pergunta` ou `Resposta`) e chamará o método apropriado da interface `Votable`.

### Testes JUnit

```java
// Teste para LSP - mostrando que o serviço pode lidar com subtipos
class UsuarioServiceTest {
    @Test
    void testCriarDiferentesTiposDeUsuario() {
        UsuarioService service = new UsuarioService();
        Usuario membro = new Membro("João", "joao@email.com", "123");
        Usuario admin = new Admin("Admin", "admin@email.com", "abc");

        // O serviço deve ser capaz de criar ambos sem erros
        service.criar(membro);
        service.criar(admin);

        assertNotNull(service.buscarPorEmail("joao@email.com"));
        assertNotNull(service.buscarPorEmail("admin@email.com"));
    }
}

// Teste para ISP - tratando objetos diferentes de forma uniforme
class VotableTest {
    @Test
    void testUpvote() {
        Votable pergunta = new Pergunta("Título", "...", new Membro());
        assertEquals(0, pergunta.getVotos());

        pergunta.upvote();
        assertEquals(1, pergunta.getVotos());
    }
}
```

---

## Semana 3: Refatoração

**Conceito:** Melhorar a estrutura do código existente sem alterar seu comportamento externo, corrigindo "Code Smells".

**Ideia de Aplicação:** Identificar e corrigir problemas no código atual. Um bom candidato é o `UsuarioService` que lida com criação, busca e gerenciamento de senhas. Outro é o `Pergunta.dataCriacao` que está como `String`.

### Tarefas da Semana

1.  **Code Smell: Tipo Primitivo Obcecado (`dataCriacao`):**
    -   **Descrição:** A classe `Pergunta` armazena `dataCriacao` como `String`. Isso dificulta a manipulação (cálculos, formatação). Devemos usar um objeto de data/hora real.
    -   **Ação:** Altere o tipo do atributo `dataCriacao` de `String` para `java.time.LocalDateTime`. Atualize o construtor e o getter. Garanta que a conversão para JSON continue funcionando corretamente.

2.  **Code Smell: Classe Grande (`UsuarioService`):**
    -   **Descrição:** O `UsuarioService` lida com a lógica de negócio de usuários e também com a autenticação (verificação de senha). Vamos extrair a lógica de autenticação para sua própria classe.
    -   **Ação:** Crie uma nova classe `AutenticacaoService`. Mova a lógica de `login` (que provavelmente existe ou existiria no `UsuarioService`) para esta nova classe. O `AutenticacaoService` terá uma dependência do `UsuarioRepository` para buscar usuários.

3.  **Refatorar o `UsuarioController`:**
    -   **Descrição:** O `UsuarioController` agora deve usar tanto o `UsuarioService` (para CRUD) quanto o `AutenticacaoService` (para login).
    -   **Ação:** Adicione um endpoint de `POST /login` no `UsuarioController` (ou em um novo `AutenticacaoController`) que chama o `AutenticacaoService`.

4.  **Code Smell: Comentário como Explicação (`Usuario.senha`):**
    -   **Descrição:** O atributo `senha` na classe `Usuario` tem um comentário `// Em um projeto real, isso deveria estar criptografado`. Vamos remover o comentário e garantir que a criptografia *sempre* aconteça.
    -   **Ação:** Mova a lógica de "hash" da senha, que está no `UsuarioService`, para o construtor ou um `setSenha` da classe `Usuario`. O `Usuario` se torna responsável por garantir que sua senha nunca seja armazenada em texto plano. O `GerenciadorDeSenhas` pode ser passado como parâmetro ou usado como estático.

5.  **Garantir que os Testes Continuem Passando:**
    -   **Descrição:** A principal regra da refatoração é não quebrar nada.
    -   **Ação:** Execute todos os testes existentes. Crie novos testes para o `AutenticacaoService`. Ajuste os testes do `UsuarioService` para refletir a remoção da responsabilidade de autenticação.

### Testes JUnit

```java
// Teste para o novo AutenticacaoService
class AutenticacaoServiceTest {
    @Test
    void testLoginComSucesso() {
        // Setup: criar um usuário e salvá-lo no repositório (mockado)
        UsuarioRepository mockRepo = mock(UsuarioRepository.class);
        Usuario usuario = new Membro(...);
        usuario.setSenha(new GerenciadorDeSenhas().hashSenha("senha123"));
        when(mockRepo.buscarPorEmail(anyString())).thenReturn(usuario);

        AutenticacaoService authService = new AutenticacaoService(mockRepo);
        // Supondo que o login retorna um "token" ou o próprio usuário
        assertNotNull(authService.login("email@valido.com", "senha123"));
    }

    @Test
    void testLoginComFalha() {
        // ... análogo ao de sucesso, mas com senha errada
    }
}
```

---

## Semana 4: Arquitetura de Camadas e APIs REST

**Conceito:** Aprofundar o entendimento da arquitetura de camadas e da importância de uma API REST bem definida.

**Ideia de Aplicação:** Formalizar a API existente, introduzir DTOs (Data Transfer Objects) para desacoplar a API dos modelos de domínio e discutir a evolução da arquitetura.

### Tarefas da Semana

1.  **Introduzir DTOs para Criação de Usuário:**
    -   **Descrição:** Atualmente, os controllers provavelmente recebem o objeto `Usuario` completo no corpo da requisição JSON, incluindo o `id`. Isso é uma falha de segurança e de design. Devemos usar um DTO.
    -   **Ação:** Crie uma classe `UsuarioRequestDTO` contendo apenas `nome`, `email` e `senha`. O `UsuarioController`, no método de `criar`, receberá este DTO e o usará para criar um objeto de domínio `Usuario`.

2.  **Introduzir DTOs para Respostas:**
    -   **Descrição:** Nunca exponha o modelo de domínio diretamente na API, especialmente campos sensíveis como a senha com hash.
    -   **Ação:** Crie uma classe `UsuarioResponseDTO` com `id`, `nome` e `email` (sem a senha). O `UsuarioController` deve converter os objetos `Usuario` para `UsuarioResponseDTO` antes de enviá-los como resposta JSON.

3.  **Documentar a API REST:**
    -   **Descrição:** Use anotações (se o framework suportar, como o Javalin com OpenAPI) ou um arquivo Markdown separado (`API.md`) para documentar todos os endpoints existentes.
    -   **Ação:** Para cada endpoint, documente: o Verbo HTTP (GET/POST/...), a URL, os parâmetros, o corpo da requisição (usando os DTOs) e os possíveis corpos de resposta.

4.  **Discussão: Monolito vs. Microsserviços:**
    -   **Descrição:** O Academic Stack é um monolito. Discuta em grupo: quais seriam os prós e contras de extrair a funcionalidade de `Notificações` ou `Reputação` para um microsserviço separado?
    -   **Ação:** Escreva um pequeno parágrafo no `README.md` do backend resumindo a discussão. Não é preciso implementar nada, apenas refletir sobre a arquitetura.

5.  **Refatorar todos os Controllers para usar DTOs:**
    -   **Descrição:** Aplique o padrão DTO para `Pergunta` e `Resposta` também.
    -   **Ação:** Crie `PerguntaRequestDTO`, `PerguntaResponseDTO`, `RespostaRequestDTO`, etc. Refatore todos os endpoints para usar este padrão, garantindo um desacoplamento completo entre a camada de API e o domínio.

### Testes JUnit

```java
// Teste do Controller (agora mais focado na conversão DTO)
class UsuarioControllerTest {
    @Test
    void testPostUsuarioConverteDTOparaModelo() {
        // Mockar o serviço
        UsuarioService mockService = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(mockService);

        UsuarioRequestDTO dto = new UsuarioRequestDTO("Novo Usuário", "novo@email.com", "senha");
        // Simula a chamada do endpoint
        controller.criarUsuario(dto); // Supondo que o método exista no controller

        // ArgumentCaptor para verificar se o objeto passado para o serviço está correto
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(mockService).criar(usuarioCaptor.capture());

        assertEquals("Novo Usuário", usuarioCaptor.getValue().getNome());
    }
}
```

---

## Semana 5: DIP e Injeção de Dependências (DI)

**Conceito:**
-   **DIP (Dependency Inversion Principle):** Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações (interfaces).
-   **DI (Dependency Injection):** A forma mais comum de aplicar o DIP, injetando dependências em vez de criá-las internamente.

**Ideia de Aplicação:** O maior "defeito" arquitetural do projeto atual é o forte acoplamento (`service = new Service()`, `repo = new Repo()`). Vamos consertar isso, o que tornará o código imensamente mais testável e flexível.

### Tarefas da Semana

1.  **Criar Interfaces para Repositórios:**
    -   **Descrição:** O primeiro passo para a inversão de dependência é depender de abstrações.
    -   **Ação:** Crie as interfaces `IUsuarioRepository`, `IPerguntaRepository`, `IRespostaRepository`. Elas devem conter as assinaturas dos métodos públicos dos repositórios atuais. As classes de repositório existentes devem implementar essas novas interfaces.

2.  **Criar Interfaces para Serviços:**
    -   **Descrição:** Faça o mesmo para a camada de serviço.
    -   **Ação:** Crie as interfaces `IUsuarioService`, `IPerguntaService`, etc. As classes de serviço atuais devem implementá-las.

3.  **Aplicar Injeção de Dependência via Construtor:**
    -   **Descrição:** Refatore as classes de serviço e de controller para receberem suas dependências pelo construtor, em vez de usar a palavra-chave `new`.
    -   **Ação:**
        -   No `UsuarioService`, remova `private final UsuarioRepository repo = new UsuarioRepository();`.
        -   Crie um construtor: `public UsuarioService(IUsuarioRepository repository) { this.repo = repository; }`.
        -   Faça o mesmo para todos os serviços e controllers. Por exemplo, `UsuarioController` dependerá de `IUsuarioService`.

4.  **Criar um "Container" de DI Manual:**
    -   **Descrição:** Agora que ninguém mais cria suas dependências, precisamos de um lugar central para "montar" a aplicação.
    -   **Ação:** Crie uma classe `DependencyManager` ou `AppConfig`. Esta classe terá métodos que instanciam e conectam os objetos.
        ```java
        public class DependencyManager {
            public static void configure(Javalin app) {
                // Instancia as implementações
                IUsuarioRepository userRepo = new UsuarioRepository();
                IUsuarioService userService = new UsuarioService(userRepo);
                // ... outros repos e services

                // Configura os controllers com suas dependências
                new UsuarioController(app, userService);
                new PerguntaController(app, perguntaService, ...);
            }
        }
        ```

5.  **Atualizar a Classe `App`:**
    -   **Descrição:** A classe principal `App.java` agora deve usar o `DependencyManager` para configurar tudo.
    -   **Ação:** O método `main` em `App` ficará muito mais limpo. Ele criará a instância do Javalin e chamará `DependencyManager.configure(app)`.

### Testes JUnit

Seus testes que usam `mock()` já se beneficiam da ideia de DI. Agora, o código de produção também é assim! Os testes existentes para serviços e controllers precisarão ser atualizados para injetar mocks no construtor, em vez de usar reflection (`@InjectMocks`).

```java
class UsuarioServiceDITest {
    private IUsuarioService usuarioService;
    private IUsuarioRepository mockRepository;

    @BeforeEach
    void setUp() {
        // Injeção de dependência manual no teste
        mockRepository = mock(IUsuarioRepository.class);
        usuarioService = new UsuarioService(mockRepository);
    }

    @Test
    void testCriarUsuarioChamaRepositorio() {
        Usuario usuario = new Membro(...);
        usuarioService.criar(usuario);
        verify(mockRepository, times(1)).salvar(usuario);
    }
}
```

---

## Semana 6: Padrões Comportamentais

**Conceitos:**
-   **Strategy:** Define uma família de algoritmos, encapsula-os e os torna intercambiáveis.
-   **Observer:** Define uma dependência um-para-muitos, notificando objetos quando o estado de outro muda.
-   **Template Method:** Define o esqueleto de um algoritmo, deixando as subclasses redefinirem etapas específicas.

**Ideia de Aplicação:** Implementar ordenação de perguntas (Strategy), notificar um usuário quando sua pergunta é respondida (Observer) e criar um processo de validação de conteúdo (Template Method).

### Tarefas da Semana

1.  **Aplicar Strategy para Ordenação de Perguntas:**
    -   **Descrição:** Na página inicial, o usuário pode querer ver as perguntas ordenadas por mais recentes, mais votadas ou não respondidas.
    -   **Ação:**
        -   Crie a interface `SortingStrategy` com um método `sort(List<Pergunta> perguntas)`.
        -   Crie as classes `SortByDate`, `SortByVotes`, `SortByUnanswered` que implementam a interface.
        -   No `PerguntaController`, o endpoint `GET /perguntas` pode receber um parâmetro de query (ex: `?sort=votes`). O controller escolherá a estratégia apropriada e a aplicará na lista retornada pelo `PerguntaService`.

2.  **Aplicar Observer para Notificar sobre Novas Respostas:**
    -   **Descrição:** Quando uma `Resposta` é criada para uma `Pergunta`, o `autor` da pergunta deve ser notificado.
    -   **Ação:**
        -   Crie a interface `QuestionObserver` com um método `notifyNewAnswer(Pergunta pergunta, Resposta resposta)`.
        -   Faça a classe `Usuario` (ou uma nova classe `NotificationManager`) implementar este observador.
        -   O `PerguntaService` (o Sujeito) terá métodos `addObserver(QuestionObserver o)` e `notifyObservers()`.
        -   Quando o `RespostaService` cria uma nova resposta, ele chama o `PerguntaService`, que então notifica seus observadores.

3.  **Aplicar Template Method para Validação de Conteúdo:**
    -   **Descrição:** A validação de `Pergunta` e `Resposta` é similar, mas não idêntica (ex: pergunta precisa de título).
    -   **Ação:**
        -   Crie a classe abstrata `ContentValidator` com o método `final validate()`.
        -   Este método chama os passos: `checkContent()`, `checkLength()`, e um método abstrato `checkSpecificRules()`.
        -   Crie as classes `QuestionValidator` e `AnswerValidator` que herdam de `ContentValidator` e implementam `checkSpecificRules()` com suas próprias lógicas.

4.  **Integrar Validação nos Serviços:**
    -   **Descrição:** Use os validadores antes de salvar perguntas ou respostas.
    -   **Ação:** O `PerguntaService` e o `RespostaService`, em seus métodos `criar`, devem instanciar e usar o validador apropriado. Se a validação falhar, eles devem lançar uma exceção.

5.  **Escrever Testes para os Novos Padrões:**
    -   **Ação:** Crie testes unitários para as diferentes `SortingStrategy`. Teste se o `Observer` é notificado corretamente (usando mocks). Teste os `ContentValidator`s para garantir que eles barram conteúdo inválido.

### Testes JUnit

```java
// Teste do Strategy
@Test
void testSortByVotesStrategy() {
    Pergunta p1 = new Pergunta(); p1.upvote(); // 1 voto
    Pergunta p2 = new Pergunta(); p2.upvote(); p2.upvote(); // 2 votos
    List<Pergunta> perguntas = Arrays.asList(p1, p2);

    SortingStrategy strategy = new SortByVotes();
    strategy.sort(perguntas);

    assertEquals(p2, perguntas.get(0)); // A mais votada deve vir primeiro
}

// Teste do Observer
@Test
void testObserverIsnotifiedOnNewAnswer() {
    // Mock do observer
    QuestionObserver mockObserver = mock(QuestionObserver.class);
    PerguntaService perguntaService = new PerguntaService(...);
    perguntaService.addObserver(mockObserver);

    Pergunta p = new Pergunta();
    Resposta r = new Resposta();

    // Simula a adição de uma resposta através do serviço
    perguntaService.addAnswerToQuestion(p, r);

    verify(mockObserver, times(1)).notifyNewAnswer(p, r);
}
```

---

## Semana 7: Padrões Estruturais

**Conceitos:**
-   **Facade:** Fornece uma interface simplificada para um subsistema complexo.
-   **Decorator:** Anexa dinamicamente novas responsabilidades a um objeto.
-   **Composite:** Compõe objetos em estruturas de árvore, tratando objetos individuais e composições de maneira uniforme.

**Ideia de Aplicação:** Criar uma `SearchFacade` para unificar buscas, decorar usuários com "Badges" (medalhas) e usar o Composite para organizar `Tags` em categorias.

### Tarefas da Semana

1.  **Aplicar Facade para Busca:**
    -   **Descrição:** Um usuário pode querer buscar por texto em perguntas, respostas e tags ao mesmo tempo. Orquestrar isso no controller é complexo.
    -   **Ação:** Crie uma `SearchFacade` que depende do `PerguntaRepository`, `RespostaRepository` e `TagRepository` (que talvez precise ser criado). A facade terá um método `search(String query)` que busca em todos os repositórios e retorna um `SearchResultDTO` consolidado.

2.  **Aplicar Decorator para Medalhas de Usuário (Badges):**
    -   **Descrição:** Usuários podem ganhar medalhas ("Bom Perfil", "Primeira Pergunta") que são exibidas em seu perfil.
    -   **Ação:**
        -   Crie uma interface `IBadge` com um método `getName()` e `getDescription()`.
        -   Crie uma classe `UserProfiler` que recebe um `Usuario` e tem um método `getProfile()`.
        -   Crie uma classe abstrata `BadgeDecorator` que implementa `IUserProfiler` e encapsula outro `IUserProfiler`.
        -   Crie decoradores concretos como `GoodQuestionBadgeDecorator`. O método `getProfile()` dele adicionaria a informação da medalha ao perfil.

3.  **Aplicar Composite para Tags:**
    -   **Descrição:** Tags podem ser organizadas em categorias (ex: "Linguagens" -> "Java", "Python"). Queremos poder buscar tanto por uma tag específica quanto por uma categoria inteira.
    -   **Ação:**
        -   Crie uma interface `TagComponent` com um método `getName()`.
        -   Crie a classe `Tag` (folha) que implementa `TagComponent`.
        -   Crie a classe `TagCategory` (composite) que também implementa `TagComponent` e contém uma lista de `TagComponent`s.

4.  **Integrar a Busca por Tags com Composite:**
    -   **Descrição:** A `SearchFacade` deve ser capaz de usar a estrutura Composite das tags.
    -   **Ação:** Adicione um método `searchByTag(String tagName)` na `SearchFacade`. Se o `tagName` for uma categoria, a busca deve retornar perguntas de todas as tags dentro daquela categoria.

5.  **Escrever Testes para os Padrões Estruturais:**
    -   **Ação:** Teste a `SearchFacade` (com mocks para os repositórios). Teste se os `BadgeDecorator`s adicionam as medalhas corretamente. Teste a estrutura `Composite` das tags.

### Testes JUnit

```java
// Teste do Decorator
@Test
void testUserBadgeDecorator() {
    Usuario u = new Membro("Ana", "ana@email.com", "123");
    // Supondo uma implementação de Profile
    Profile basicProfile = new UserProfile(u);

    // Decora o perfil com uma medalha
    Profile decoratedProfile = new GoodQuestionBadgeDecorator(basicProfile);

    assertTrue(decoratedProfile.getBadges().contains("Boa Pergunta"));
}

// Teste do Composite
@Test
void testTagCategoryContainsTags() {
    TagComponent java = new Tag("Java");
    TagComponent python = new Tag("Python");
    TagCategory a = new TagCategory("Linguagens");
    a.add(java);
    a.add(python);

    // O composite deve conter os filhos
    assertTrue(a.getComponents().contains(java));
}
```

---

## Semana 8: Padrões Criacionais

**Conceitos:**
-   **Singleton:** Garante que uma classe tenha apenas uma instância e fornece um ponto de acesso global.
-   **Abstract Factory:** Fornece uma interface para criar famílias de objetos relacionados sem especificar suas classes concretas.

**Ideia de Aplicação:** Usar um Singleton para configurações da aplicação. Preparar o terreno para persistência em banco de dados usando uma `Abstract Factory` para os repositórios.

### Tarefas da Semana

1.  **Implementar `AppConfig` como Singleton:**
    -   **Descrição:** Crie uma classe para carregar configurações de um arquivo (ex: `config.properties`), como a porta do servidor ou o nome do banco de dados. Queremos garantir que essas configurações sejam carregadas apenas uma vez.
    -   **Ação:** Crie a classe `AppConfig` usando o padrão Singleton. Ela terá um método `getProperty(String key)` para ler as configurações.

2.  **Usar o Singleton na Configuração do Servidor:**
    -   **Descrição:** O `App.java` deve usar o `AppConfig` para obter a porta do servidor.
    -   **Ação:** No `main`, em vez de usar um número fixo, use `Javalin.create().start(AppConfig.getInstance().getServerPort())`.

3.  **Criar a Abstract Factory (`RepositoryFactory`):**
    -   **Descrição:** Para facilitar a troca da persistência em memória por um banco de dados no futuro, vamos criar uma fábrica abstrata.
    -   **Ação:** Crie a interface `RepositoryFactory` com os métodos:
        -   `IUsuarioRepository createUsuarioRepository();`
        -   `IPerguntaRepository createPerguntaRepository();`
        -   `IRespostaRepository createRespostaRepository();`

4.  **Criar a Fábrica Concreta para Persistência em Memória:**
    -   **Descrição:** Crie uma implementação da fábrica que retorna nossas instâncias de repositório em memória existentes.
    -   **Ação:** Crie a classe `InMemoryRepositoryFactory` que implementa `RepositoryFactory`. Seus métodos simplesmente retornarão `new UsuarioRepository()`, `new PerguntaRepository()`, etc.

5.  **Refatorar o `DependencyManager` para usar a Abstract Factory:**
    -   **Descrição:** O `DependencyManager` agora não deve mais instanciar os repositórios diretamente. Ele deve usar a fábrica.
    -   **Ação:**
        -   O `DependencyManager` decide qual fábrica usar (por enquanto, apenas a `InMemoryRepositoryFactory`).
        -   Ele instancia a fábrica: `RepositoryFactory factory = new InMemoryRepositoryFactory();`.
        -   Ele usa a fábrica para criar os repositórios: `IUsuarioRepository userRepo = factory.createUsuarioRepository();`.
        -   O resto da injeção de dependência continua igual.

### Testes JUnit

```java
// Teste do Singleton
@Test
void testConfigSingletonInstancesAreTheSame() {
    AppConfig config1 = AppConfig.getInstance();
    AppConfig config2 = AppConfig.getInstance();
    assertSame(config1, config2);
}

// Teste da Abstract Factory
@Test
void testInMemoryFactoryCreatesInMemoryRepos() {
    RepositoryFactory factory = new InMemoryRepositoryFactory();
    IUsuarioRepository userRepo = factory.createUsuarioRepository();
    IPerguntaRepository questionRepo = factory.createPerguntaRepository();

    assertTrue(userRepo instanceof UsuarioRepository);
    assertTrue(questionRepo instanceof PerguntaRepository);
}
```

---

## Semana 9: Integração e Sistema de Reputação

**Conceito:** Consolidar o conhecimento aplicando múltiplos padrões para resolver um requisito de negócio complexo.

**Ideia de Aplicação:** Implementar um sistema de reputação para os usuários, onde eles ganham pontos por ações positivas (perguntas/respostas bem votadas).

**Requisito:**
"Quando uma pergunta ou resposta de um usuário recebe um `upvote`, a reputação do autor deve aumentar. O número de pontos ganhos pode variar (ex: +5 para upvote em pergunta, +10 para upvote em resposta). Esta é uma **Strategy**. O `Usuario` deve ser notificado sobre seu ganho de reputação (**Observer**). Se a reputação ultrapassar certos limites, o usuário ganha uma nova medalha (**Decorator**), que é criada por uma `BadgeFactory` (**Factory**). Todo esse processo é orquestrado por um `ReputationService`."

### Tarefas da Semana

1.  **Criar o `ReputationService`:**
    -   **Descrição:** Este serviço centralizará a lógica de reputação.
    -   **Ação:** Crie a classe `ReputationService`. Ele dependerá do `IUsuarioService` para atualizar o usuário.

2.  **Criar as Estratégias de Cálculo de Reputação (Strategy):**
    -   **Descrição:** Defina os algoritmos para ganho de pontos.
    -   **Ação:** Crie a interface `ReputationStrategy` com o método `calculate(Votable item)`. Crie as implementações `QuestionUpvoteStrategy` (+5 pontos) e `AnswerUpvoteStrategy` (+10 pontos).

3.  **Integrar o `ReputationService` com Votos (Observer):**
    -   **Descrição:** O `ReputationService` precisa "saber" quando um voto acontece. Ele pode ser um observador.
    -   **Ação:** Faça o `ReputationService` implementar uma interface `VoteObserver`. O `VotoController` (ou um `VotoService`), após registrar um voto, notificará seus observadores, incluindo o `ReputationService`.

4.  **Implementar o `ReputationService`:**
    -   **Descrição:** Ao ser notificado de um voto, o serviço deve usar a estratégia correta para calcular os pontos, atualizar a reputação do autor do item votado e salvá-lo.
    -   **Ação:** Implemente o método `update` do observer no `ReputationService`. Ele escolherá a estratégia, calculará os pontos e chamará o `IUsuarioService` para atualizar o usuário.

5.  **Juntar Tudo em um Teste de Integração:**
    -   **Descrição:** Crie um teste que simula todo o fluxo.
    -   **Ação:**
        -   Crie um usuário, uma pergunta dele e um `ReputationService`.
        -   Simule um `upvote` na pergunta.
        -   Verifique (usando mocks e `ArgumentCaptor`) se o `ReputationService` foi notificado.
        -   Verifique se o `IUsuarioService` foi chamado para salvar o usuário com a reputação atualizada (ex: +5 pontos).

### Testes JUnit

```java
@ExtendWith(MockitoExtension.class)
class ReputationSystemIntegrationTest {

    @Mock
    private IUsuarioService mockUsuarioService;

    @InjectMocks
    private ReputationService reputationService;

    @Test
    void testUpvoteOnQuestionIncreasesReputation() {
        // Setup
        Usuario autor = new Membro("Autor", "autor@email.com", "123");
        autor.setReputacao(100); // Reputação inicial
        Pergunta pergunta = new Pergunta("Título", "Conteúdo", autor);

        // Escolha da Strategy
        ReputationStrategy strategy = new QuestionUpvoteStrategy();
        reputationService.setStrategy(strategy); // O serviço usa a estratégia

        // Ação - simula a notificação do observer
        reputationService.onUpvote(pergunta);

        // Capturar o usuário que foi passado para o método de atualização
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(mockUsuarioService, times(1)).atualizar(usuarioCaptor.capture());

        // Assert
        Usuario usuarioAtualizado = usuarioCaptor.getValue();
        assertEquals(105, usuarioAtualizado.getReputacao()); // 100 + 5
    }
}
```
