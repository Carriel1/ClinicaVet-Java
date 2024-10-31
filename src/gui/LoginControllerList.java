package gui;

import java.util.HashMap;
import java.util.Map;

public class LoginControllerList {

    private static final Map<String, String> users = new HashMap<>();

    static {
        // Usuários simulados (exemplo)
        users.put("cliente", "senha123");
        users.put("funcionario", "admin123");
    }

    public static boolean isValidUser(String username, String password) {
        // Verifica se o usuário e a senha correspondem aos dados armazenados
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
