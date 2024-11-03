package model.services;

public class ClienteService {

    // Método de autenticação que valida as credenciais do cliente
    public boolean authenticate(String username, String password) {
        // Exemplo simples de verificação
        // Você pode substituir por uma lógica de autenticação real, como consulta ao banco de dados

        // Suponha que esses sejam os dados de login válidos
        String validUsername = "cliente";
        String validPassword = "1234";

        // Verifica se as credenciais fornecidas coincidem com as válidas
        return username.equals(validUsername) && password.equals(validPassword);
    }
}
