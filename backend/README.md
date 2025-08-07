**Universidade Federal da Paraíba - UFPB** \
**Centro de Ciências Exatas e Educação - CCAE** \
**Departamento de Ciências Exatas - DCX**

**Professor:** [Matheus Barbosa de Oliveira](https://github.com/barbosamaatheus)

---
# Stack Spark - Backend - Projeto Acadêmico

Este projeto é um sistema de perguntas e respostas inspirado no Stack Overflow, desenvolvido como trabalho prático para a disciplina de Programação.
O objetivo é permitir que os alunos pratiquem conceitos de programação orientada a objetos, arquitetura de software, padrões de projeto e testes unitários.

## Tecnologias utilizadas

* Java 21
* [Javalin 6](https://javalin.io/)
* Maven
* JUnit 5
* BCrypt para criptografia de senhas
* Persistência em memória (com possibilidade de expansão para arquivos ou PostgreSQL via Docker)

## Estrutura do projeto

```
academic-stack-spark-backend/
 ├── pom.xml
 └── src
     ├── main
     │    ├── java/br/ufpb/ccae/dcx/
     │    │    ├── Main.java
     │    │    ├── config/ServerConfig.java
     │    │    ├── controller/
     │    │    ├── model/
     │    │    ├── repository/
     │    │    ├── service/
     │    │    └── util/
     │    └── resources/
     └── test
          └── java/br/ufpb/ccae/dcx/
```

## Funcionalidades iniciais

* Cadastro e autenticação de usuários
* Criação, listagem, atualização e remoção de perguntas
* Criação e listagem de respostas
* API REST para consumo por frontend

## Como executar

1. **Clonar o repositório**

```bash
git clone https://github.com/seuusuario/academic-stack-spark-backend.git
cd academic-stack-spark-backend
```

2. **Compilar e executar**

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.seuprojeto.Main"
```

3. **Acessar**
   O backend estará disponível em:

```
http://localhost:7000
```

## Endpoints principais

### Autenticação

* `POST /login` - Autentica um usuário existente
* `POST /usuarios` - Cadastra um novo usuário

### Perguntas

* `GET /perguntas` - Lista todas as perguntas
* `POST /perguntas` - Cria uma nova pergunta
* `GET /perguntas/{id}` - Busca uma pergunta pelo ID
* `DELETE /perguntas/{id}` - Remove uma pergunta

### Respostas

* `GET /perguntas/{id}/respostas` - Lista respostas de uma pergunta
* `POST /perguntas/{id}/respostas` - Cria uma nova resposta para uma pergunta

## Testes

O projeto possui testes unitários com JUnit 5.
Para executar:

```bash
mvn test
```

## Atividades acadêmicas

O projeto será evoluído semanalmente com base nos seguintes tópicos:

1. Padrões GRASP
2. Princípios SOLID (LSP e ISP)
3. Refatoração
4. Arquitetura em camadas, microsserviços e APIs RESTful
5. DIP e injeção de dependências
6. Padrões comportamentais: Strategy, Template Method e Observer
7. Padrões estruturais: Facade, Composite e Decorator
8. Padrões criacionais: Singleton e Abstract Factory
9. Integração de múltiplos padrões no projeto