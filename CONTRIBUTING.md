## Guia de Contribuição

Este projeto é aberto à colaboração dos estudantes das disciplinas da UFPB – CCAE - DCX, e utiliza **GitHub** para o controle de versões. Aqui você aprenderá a contribuir de forma organizada, segura e seguindo as boas práticas.

---

## Passo a passo para contribuir

### 1. **Escolha uma tarefa**

Todas as tarefas estão descritas nas **Issues** do repositório. Para começar:

* Acesse a aba **Issues** no GitHub.
* Escolha uma issue **disponível** (sem ninguém trabalhando).
* Atribua a **issue** para você

---
### 2. Faça um fork do repositório
Acesse o repositório original.

Clique em Fork (canto superior direito).

Isso criará uma cópia sua do projeto no GitHub, por exemplo:
https://github.com/seu-usuario/academic-stack-spark

---

### 3. **Configure o repositório localmente**

Caso ainda não tenha clonado o projeto:

```bash
git clone --recursive <URL_DO_SEU_FORK>
cd academic-stack-spark
```

Se já tiver clonado, atualize os submódulos:

```bash
git submodule update --init --recursive
```

Adicione o repositório oficial como `upstream`:

```bash
git remote add upstream <URL_DO_PROJETO_ORIGINAL>
```

Verifique se está certo:

```bash
git remote -v
```

Deve aparecer:

```bash
origin    <URL_DO_SEU_FORK>
upstream  <URL_DO_PROJETO_ORIGINAL>
```

---

### 4. **Crie uma nova branch**

Sempre que for trabalhar em uma nova tarefa:

```bash
git checkout main
git pull upstream main        # sincronize com a versão mais recente
git checkout -b nome-da-branch
```

Use nomes de branch descritivos, como:

```
feat/adicionar-login
fix/corrigir-erro-de-cadastro
docs/atualizar-readme
```

---

### 5. **Implemente a solução**

Realize a implementação conforme solicitado na **issue** atribuída.

---

### 6. **Faça commits com mensagens padronizadas (Conventional Commits)**

Siga o padrão [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) para as mensagens:

#### Exemplos:

```bash
git add .
git commit -m "feat(backend): adicionar endpoint de login"
```

#### Prefixos comuns:

| Tipo       | Significado                                        |
| ---------- | -------------------------------------------------- |
| `feat`     | Nova funcionalidade                                |
| `fix`      | Correção de bug                                    |
| `docs`     | Documentação                                       |
| `refactor` | Refatoração de código (sem alterar funcionalidade) |
| `style`    | Mudanças de formatação                             |
| `test`     | Adição ou alteração de testes                      |
| `chore`    | Atualizações diversas (build, CI, etc)             |

---

### 7. **Envie sua branch para o GitHub**

```bash
git push origin nome-da-branch
```

---

### 8. **Crie um Pull Request (PR)**

* Acesse seu repositório no GitHub.
* Clique em **"Compare & Pull Request"**.
* Descreva **o que você fez** e **qual issue resolve** (use `Closes #ID_DA_ISSUE`).
* Aguarde a **revisão** do professor ou de outro colaborador.

---

## Como revisar Pull Requests de colegas

Revisar o código dos outros é parte fundamental do trabalho em equipe!

1. Vá até a aba **Pull Requests**.
2. Escolha um PR **aberto**.
3. Leia a descrição, veja qual issue ele resolve.
4. Clique em **Files changed** para revisar o código.
5. Se necessário, comente diretamente em trechos específicos do código usando o botão "+" ao lado da linha.
6. Depois de revisar tudo, clique em **Review changes** (canto superior direito) e escolha uma das opções:

### Opções ao revisar:

| Opção                 | Quando usar                                                    |
| --------------------- | -------------------------------------------------------------- |
| ✅ **Approve**         | O código está certo e pronto para ser integrado                |
| 💬 **Comment**        | Você quer **fazer comentários ou sugestões**, mas não bloquear |
| ❌ **Request changes** | Há **erros ou melhorias obrigatórias** antes de aceitar o PR   |


### Exemplo de uso:

* Use **Comment** se quiser sugerir uma melhoria de performance, mas não é algo que obrigatoriamente precisa ser feito agora.
* Use **Request changes** se houver um bug, problema de lógica, código mal formatado ou violação de alguma regra da equipe.
* Use **Approve** apenas se você leu tudo e está confiante de que está correto.

### Dica:

Antes de aprovar um PR, verifique:

* O código funciona?
* Resolve a issue corretamente?
* Está seguindo o padrão de código do projeto?
* Os commits estão bem nomeados (Conventional Commits)?
* Não está quebrando funcionalidades existentes?

> ⚠️ Nunca aprove um PR sem revisar o código com atenção.

---

## Atualizar seu repositório antes de começar uma nova contribuição

Antes de iniciar uma nova tarefa:

```bash
git checkout main
git pull upstream main
```

Se estiver trabalhando em um fork:

```bash
git fetch upstream
git checkout main
git merge upstream/main
```

---

## Checklist antes de abrir um PR

* [ ] A tarefa foi atribuída a você?
* [ ] Você criou uma branch com nome apropriado?
* [ ] A funcionalidade está funcionando?
* [ ] A mensagem do commit segue o padrão?
* [ ] Você testou suas alterações?
* [ ] O PR referencia a issue correta (`Closes #...`)?

---

## Dicas finais

* Nunca faça alterações direto na branch `main`.
* Commits pequenos e frequentes ajudam na revisão.
* Escreva código limpo e bem comentado.
* Peça ajuda se tiver dúvidas — aprender em grupo é mais legal!
