package gui;

import java.io.IOException;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.services.ClienteService;

public class LoginClienteController {

    private ClienteService service;

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

    public void setClienteService(ClienteService service) {
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

        // Verifique se os campos de username e senha não estão vazios
        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor, preencha todos os campos.");
            Alerts.showAlert("Erro no Login", null, "Nome de usuário ou senha não podem estar vazios.", AlertType.ERROR);
            return;
        }

        Cliente cliente = service.authenticate(username, password); 

        if (cliente != null) {
            loadTelaPrincipalCliente(event, cliente);
        } else {
            lblError.setText("Nome de usuário ou senha inválidos");
            Alerts.showAlert("Login Error", null, "Nome de usuário ou senha incorretos.", AlertType.ERROR);
        }
    }

    @FXML
    public void onBtCancelAction() {
    	loadView("/gui/MainView.fxml", controller -> {});
    }

    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load(); 

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot();

            mainScrollPane.setContent(newView);

            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    private void loadTelaPrincipalCliente(ActionEvent event, Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipalCliente.fxml"));
            Parent root = loader.load(); 
            Scene scene = new Scene(root); 

            TelaPrincipalClienteController controller = loader.getController();

            controller.setWelcomeMessage("Bem-vindo, " + cliente.getNome() + "!");
            controller.setClienteId(cliente.getId()); 

            Stage stage = (Stage) btLogin.getScene().getWindow(); 
            stage.setScene(scene);
            stage.show(); 
            
        } catch (IOException e) {
            Alerts.showAlert("Erro", null, "Não foi possível carregar a tela principal do cliente.", AlertType.ERROR);
            e.printStackTrace(); 
        }
    }
}
