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

        System.out.println("Tentativa de login:");
        System.out.println("Usuário: " + username);
        System.out.println("Senha: " + password);

        if (authenticate(username, password)) {
            mainController.setScreen("/gui/MainView.fxml"); 
        } else {
            System.out.println("Login falhou!");
        }
    }

    @FXML
    public void handleCancel() {
        System.out.println("Login cancelado.");
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "admin".equals(password); 
    }
}
