package gui;

import java.io.IOException;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

        if (service.authenticate(username, password)) {
            // Após o login bem-sucedido, carregar a tela principal do cliente
            loadTelaPrincipalCliente(event);
        } else {
            lblError.setText("Nome de usuário ou senha inválidos");
            Alerts.showAlert("Login Error", null, "Nome de usuário ou senha incorretos.", AlertType.ERROR);
        }
    }


    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Stage stage = Utils.currentStage(event);
        stage.close();
    }

    private void loadTelaPrincipalCliente(ActionEvent event) {
        try {
            // Passo 1: Carregar o arquivo FXML da TelaPrincipalCliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipalCliente.fxml"));
            Parent root = loader.load(); // Carrega a interface gráfica da tela principal
            Scene scene = new Scene(root); // Cria a cena com o conteúdo carregado do FXML

            // Passo 2: Obter o controlador da TelaPrincipalCliente
            TelaPrincipalClienteController controller = loader.getController();

            // Passo 3: Passar dados para o controlador
            // Aqui, passamos a mensagem de boas-vindas, usando o nome de usuário
            controller.setWelcomeMessage("Bem-vindo, " + txtUsername.getText() + "!");

            // Passo 4: Configurar a nova cena no Stage
            Stage stage = (Stage) btLogin.getScene().getWindow(); // Pega o Stage atual (a janela)
            stage.setScene(scene); // Define a nova cena
            stage.show(); // Exibe a nova cena (TelaPrincipalCliente)
            
        } catch (IOException e) {
            // Caso ocorra algum erro, exibe uma mensagem de erro
            Alerts.showAlert("Erro", null, "Não foi possível carregar a tela principal do cliente.", AlertType.ERROR);
            e.printStackTrace(); // Exibe o erro no console para depuração
        }
      }
    }
