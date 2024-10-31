package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControllerForm {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Verificação simples (por exemplo, chamada ao LoginControllerList)
        if (LoginControllerList.isValidUser(username, password)) {
            // Limpa a mensagem de erro caso o login seja bem-sucedido
            errorLabel.setVisible(false);
            // Ação em caso de login bem-sucedido
            System.out.println("Login realizado com sucesso!");
        } else {
            // Mostra uma mensagem de erro caso o login falhe
            errorLabel.setText("Usuário ou senha incorretos");
            errorLabel.setVisible(true);
        }
    }
}
