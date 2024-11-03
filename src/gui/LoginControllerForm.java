package gui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControllerForm {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Aqui você pode adicionar a lógica para autenticar o usuário
        System.out.println("Tentativa de login:");
        System.out.println("Usuário: " + username);
        System.out.println("Senha: " + password);

        // Exemplo de navegação após login bem-sucedido
        if (authenticate(username, password)) {
            mainController.setScreen("/gui/MainView.fxml"); // Exibe a tela principal após login
        } else {
            // Exiba um alerta de erro
            System.out.println("Login falhou!");
        }
    }

    @FXML
    public void handleCancel() {
        // Lógica para cancelar o login
        System.out.println("Login cancelado.");
    }

    private boolean authenticate(String username, String password) {
        // Implementar a lógica de autenticação aqui (por exemplo, verificar em um banco de dados)
        return "admin".equals(username) && "admin".equals(password); // Exemplo de autenticação
    }
}
