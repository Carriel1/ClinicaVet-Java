package gui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador responsável pela tela de login no sistema.
 * Realiza a autenticação do usuário e redireciona para a tela principal
 * caso o login seja bem-sucedido.
 */
public class LoginControllerForm {
    
    /**
     * Campo de texto para o nome de usuário.
     */
    @FXML
    private TextField usernameField;
    
    /**
     * Campo de senha para a autenticação.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Controlador principal do sistema, usado para redirecionar para a tela principal.
     */
    private MainController mainController;

    /**
     * Configura o controlador principal do sistema.
     * 
     * @param mainController O controlador principal.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Ação chamada quando o botão de login é pressionado.
     * Realiza a autenticação utilizando o nome de usuário e senha fornecidos,
     * redirecionando para a tela principal em caso de sucesso, ou exibindo 
     * uma mensagem de falha no login.
     */
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Tentativa de login:");
        System.out.println("Usuário: " + username);
        System.out.println("Senha: " + password);

        // Verifica se o login foi bem-sucedido
        if (authenticate(username, password)) {
            mainController.setScreen("/gui/MainView.fxml"); 
        } else {
            System.out.println("Login falhou!");
        }
    }

    /**
     * Ação chamada quando o botão de cancelamento é pressionado.
     * Exibe uma mensagem indicando que o login foi cancelado.
     */
    @FXML
    public void handleCancel() {
        System.out.println("Login cancelado.");
    }

    /**
     * Método de autenticação simples para validar o nome de usuário e senha.
     * Neste caso, o login é bem-sucedido se o nome de usuário e senha forem "admin".
     * 
     * @param username O nome de usuário inserido.
     * @param password A senha inserida.
     * @return Retorna true se o nome de usuário e a senha forem "admin", caso contrário, retorna false.
     */
    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "admin".equals(password); 
    }
}
