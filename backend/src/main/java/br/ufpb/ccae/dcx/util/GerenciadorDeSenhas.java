package br.ufpb.ccae.dcx.util;

import org.mindrot.jbcrypt.BCrypt;

public class GerenciadorDeSenhas {

    // Gera o hash
    public static String gerarHashDaSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Verifica se a senha informada corresponde ao hash
    public static boolean verificarSenha(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }
}