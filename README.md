**Universidade Federal da Paraíba - UFPB** \
**Centro de Ciências Exatas e Educação - CCAE** \
**Departamento de Ciências Exatas - DCX**

**Professor:** [Matheus Barbosa de Oliveira](https://github.com/barbosamaatheus)

---

# Stack Spark - Fullstack - Projeto Acadêmico

Este é um projeto de referência para os alunos das disciplinas de Análise e Projeto de Sistemas e Projeto de Sistemas Orientados a Objetos dos cursos de Sistemas de Informação e Lic. em Ciência da Computação da UFPB, campus IV em Rio Tinto.

🚨 Atenção: Este projeto foi desenvolvido para servir como base para a implementação do projeto da disciplina. Ele é incompleto e possui vários pontos de melhoria propositais, que serão discutidos em sala de aula. Deste modo, não considere cegamente este projeto como uma referência para boas práticas de programação e um bom design. Repito, eu coloquei propositalmente alguns problemas de design que serão discutidos em sala de aula.

Trata-se de um projeto de controle de Projetos, onde, por enquanto é possível cadastrar projetos e participantes.

Este repositório contém a aplicação **fullstack**, que integra:

- **Frontend** → [frontend](./frontend) _(submódulo Git — repositório separado)_
- **Backend** → código interno neste repositório

---

## Estrutura do projeto

```

academic-stack-spark/
│
├── frontend/   # Aplicação frontend (submódulo)
├── backend/    # API backend (código interno)
└── .gitmodules # Configuração do submódulo

```

---

## Clonar o projeto com submódulo

> **IMPORTANTE:** Ao clonar este repositório, use o parâmetro `--recursive` para trazer o código do frontend junto.

```bash
git clone --recursive https://github.com/ufpb-aps-poo/academic-stack-spark.git
```

Se já clonou sem `--recursive`:

```bash
git submodule update --init --recursive
```

---

## Atualizar o submódulo (frontend)

Quando houver mudanças no **frontend**:

```bash
cd frontend
git pull origin main
```

---

## Executar o projeto

### Backend (interno)

LEIA: [README - BACKEND](./backend/README.md)

### Frontend (submódulo)

LEIA: [README - FRONTEND](./frontend/README.md)

---

## Permissões no submódulo

- O **frontend** é um repositório separado.
- Para alterar e enviar mudanças diretamente, você precisa ter **permissão de escrita** nele.
- Caso não tenha permissão:

  1. Crie um **fork** do frontend no GitHub.
  2. Faça as alterações no seu fork.
  3. Abra um **Pull Request** para o repositório original.
