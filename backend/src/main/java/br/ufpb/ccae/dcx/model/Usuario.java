package br.ufpb.ccae.dcx.model;
public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha; // Em um projeto real, isso deveria estar criptografado


    public Usuario() {
        // Construtor vazio para uso com bibliotecas de serialização/deserialização
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
