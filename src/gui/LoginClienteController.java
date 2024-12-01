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

        Cliente cliente = service.authenticate(username, password); // Agora o método retorna um Cliente

        if (cliente != null) {
            // Após o login bem-sucedido, carregar a tela principal do cliente
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
            Parent newView = loader.load(); // Parent é genérico e funciona para qualquer root

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); // Supondo que o root principal seja um ScrollPane

            // Substituir o conteúdo do ScrollPane pela nova view carregada
            mainScrollPane.setContent(newView);

            // Inicializar o controlador
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    private void loadTelaPrincipalCliente(ActionEvent event, Cliente cliente) {
        try {
            // Passo 1: Carregar o arquivo FXML da TelaPrincipalCliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipalCliente.fxml"));
            Parent root = loader.load(); // Carrega a interface gráfica da tela principal
            Scene scene = new Scene(root); // Cria a cena com o conteúdo carregado do FXML

            // Passo 2: Obter o controlador da TelaPrincipalCliente
            TelaPrincipalClienteController controller = loader.getController();

            // Passo 3: Passar dados para o controlador
            controller.setWelcomeMessage("Bem-vindo, " + cliente.getNome() + "!");
            controller.setClienteId(cliente.getId()); // Passa o ID do cliente para o controlador

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
