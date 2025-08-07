package br.ufpb.ccae.dcx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pergunta {
    private int id;
    private String titulo;
    private String conteudo;
    private Usuario autor;
    private String dataCriacao = LocalDateTime.now().toString();
    private List<Resposta> respostas = new ArrayList<>();

    private List<String> tags = new ArrayList<>(); // opcional

    public Pergunta() {
    }

    public Pergunta(String titulo, String conteudo, Usuario autor) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        if (tags != null) {
            this.tags = tags;
        }
    }
}
