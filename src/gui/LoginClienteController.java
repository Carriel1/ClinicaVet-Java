package gui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginClienteController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLoginCliente() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Aqui você pode adicionar a lógica para autenticar o cliente
        System.out.println("Tentativa de login do Cliente:");
        System.out.println("Usuário: " + username);
        System.out.println("Senha: " + password);
    }

    @FXML
    public void handleCancel() {
        // Lógica para cancelar o login, como fechar a janela ou limpar os campos
        System.out.println("Login cancelado.");
    }
}
