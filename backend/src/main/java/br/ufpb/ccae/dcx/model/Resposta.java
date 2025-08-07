package br.ufpb.ccae.dcx.model;

public class Resposta {
    private int id;
    private int idPergunta;
    private String conteudo;
    private Usuario autor;

    public Resposta() {}

    public Resposta(int id, int idPergunta, String conteudo, Usuario autor) {
        this.id = id;
        this.idPergunta = idPergunta;
        this.conteudo = conteudo;
        this.autor = autor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPergunta() { return idPergunta; }
    public void setIdPergunta(int idPergunta) { this.idPergunta = idPergunta; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
}

