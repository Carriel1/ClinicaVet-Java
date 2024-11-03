package gui;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.services.FuncionarioService;

public class LoginFuncionarioController {

    private FuncionarioService service;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btLogin;

    @FXML
    private Button btCancel;

    @FXML
    private Label lblError;

    public void setFuncionarioService(FuncionarioService service) {
        this.service = service;
    }

    @FXML
    public void onBtLoginAction(ActionEvent event) {
        if (service == null) throw new IllegalStateException("Service was null");

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (service.authenticate(username, password)) {
            try {
                Stage stage = Utils.currentStage(event);
                stage.close();
            } catch (IllegalArgumentException e) {
                Alerts.showAlert("Erro no Login", null, "Não foi possível capturar a janela atual para fechamento.", AlertType.ERROR);
            }
        } else {
            lblError.setText("Invalid username or password");
            Alerts.showAlert("Login Error", null, "Username or password is incorrect.", AlertType.ERROR);
        }
    }



    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Stage stage = Utils.currentStage(event);
        stage.close();
    }
}

