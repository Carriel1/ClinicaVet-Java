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

/**
 * Controlador responsável pela tela de login do cliente.
 * Realiza a autenticação do cliente, exibindo mensagens de erro caso haja falha
 * e redirecionando para a tela principal do cliente quando a autenticação for bem-sucedida.
 */
public class LoginClienteController {

    /**
     * Serviço de Cliente, usado para autenticar o login do cliente.
     */
    private ClienteService service;

    /**
     * Campo de texto para o nome de usuário do cliente.
     */
    @FXML
    private TextField txtUsername;

    /**
     * Campo de senha para a autenticação do cliente.
     */
    @FXML
    private PasswordField txtPassword;

    /**
     * Botão para realizar o login.
     */
    @FXML
    private Button btLogin;

    /**
     * Botão para cancelar a ação de login e voltar à tela principal.
     */
    @FXML
    private Button btCancel;

    /**
     * Label usada para exibir mensagens de erro durante o processo de login.
     */
    @FXML
    private Label lblError;

    /**
     * Define o serviço de Cliente que será utilizado para autenticar o login.
     *
     * @param service O serviço de Cliente.
     */
    public void setClienteService(ClienteService service) {
        this.service = service;
    }

    /**
     * Ação chamada quando o botão de login é pressionado.
     * Verifica se o nome de usuário e a senha foram preenchidos e tenta autenticar o cliente.
     * Se a autenticação for bem-sucedida, redireciona o cliente para a tela principal.
     * Caso contrário, exibe uma mensagem de erro.
     *
     * @param event O evento de ação do botão.
     */
    @FXML
    public void onBtLoginAction(ActionEvent event) {
        if (service == null) {
            Alerts.showAlert("Erro no Login", null, "Service não foi configurado.", AlertType.ERROR);
            return;
        }

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Verifica se os campos de username e senha não estão vazios
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
            Alerts.showAlert("Erro no Login", null, "Nome de usuário ou senha incorretos.", AlertType.ERROR);
        }
    }

    /**
     * Ação chamada quando o botão de cancelamento é pressionado.
     * Redireciona o usuário de volta à tela principal do sistema.
     */
    @FXML
    public void onBtCancelAction() {
        loadView("/gui/MainView.fxml", controller -> {});
    }

    /**
     * Método genérico para carregar uma nova view (tela) no sistema.
     * 
     * @param fxmlPath O caminho do arquivo FXML a ser carregado.
     * @param initializingAction Ação a ser realizada após a inicialização do controlador.
     */
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
            Alerts.showAlert("Exceção de IO", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Redireciona o cliente para a tela principal após o login bem-sucedido.
     * Exibe uma mensagem de boas-vindas e configura o ID do cliente na tela principal.
     * 
     * @param event O evento de ação do login.
     * @param cliente O cliente autenticado.
     */
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

