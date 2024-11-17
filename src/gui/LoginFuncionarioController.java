package gui;

import java.io.IOException;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
        if (service == null) {
            Alerts.showAlert("Erro no Login", null, "Service não foi configurado.", AlertType.ERROR);
            return;
        }

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (service.authenticate(username, password)) {
            try {
                System.out.println("Carregando Tela Principal...");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipalFuncionarioController.fxml"));
                
                if (loader.getLocation() == null) {
                    System.out.println("Arquivo FXML não encontrado.");
                    return;
                }

                AnchorPane pane = loader.load();

                TelaPrincipalFuncionarioController controller = loader.getController();
                controller.setWelcomeMessage("Bem-vindo, " + username + "!");

                Stage stage = new Stage();
                stage.setTitle("Tela Principal - Sistema de Gestão");
                stage.setScene(new Scene(pane));
                stage.show();

                Stage currentStage = Utils.currentStage(event);
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                Alerts.showAlert("Erro", null, "Falha ao carregar a Tela Principal.", AlertType.ERROR);
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


