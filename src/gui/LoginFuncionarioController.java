package gui;

import java.io.IOException;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.services.FuncionarioService;

/**
 * Controlador responsável pela tela de login do funcionário no sistema.
 * Realiza a autenticação do funcionário e, em caso de sucesso, redireciona
 * para a tela principal do sistema de gestão.
 */
public class LoginFuncionarioController {

    /**
     * Serviço responsável pelas operações relacionadas ao funcionário.
     */
    private FuncionarioService service;

    /**
     * Campo de texto para o nome de usuário.
     */
    @FXML
    private TextField txtUsername;

    /**
     * Campo de senha para a autenticação.
     */
    @FXML
    private PasswordField txtPassword;

    /**
     * Botão responsável por iniciar o processo de login.
     */
    @FXML
    private Button btLogin;

    /**
     * Botão responsável por cancelar a ação de login.
     */
    @FXML
    private Button btCancel;

    /**
     * Rótulo utilizado para exibir mensagens de erro.
     */
    @FXML
    private Label lblError;

    /**
     * Configura o serviço de autenticação de funcionário.
     * 
     * @param service O serviço de autenticação de funcionário.
     */
    public void setFuncionarioService(FuncionarioService service) {
        this.service = service;
    }

    /**
     * Ação chamada quando o botão de login é pressionado.
     * Realiza a autenticação do funcionário utilizando o nome de usuário e senha fornecidos.
     * Se a autenticação for bem-sucedida, redireciona o funcionário para a tela principal do sistema.
     * Caso contrário, exibe uma mensagem de erro.
     * 
     * @param event O evento de clique do botão de login.
     */
    @FXML
    public void onBtLoginAction(ActionEvent event) {
        if (service == null) {
            Alerts.showAlert("Erro no Login", null, "Service não foi configurado.", AlertType.ERROR);
            return;
        }

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Autenticação do funcionário
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

                // Cria uma nova janela para a tela principal do funcionário
                Stage stage = new Stage();
                stage.setTitle("Tela Principal - Sistema de Gestão");
                stage.setScene(new Scene(pane));
                stage.show();

                // Fecha a janela de login
                Stage currentStage = Utils.currentStage(event);
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                Alerts.showAlert("Erro", null, "Falha ao carregar a Tela Principal.", AlertType.ERROR);
            }
        } else {
            lblError.setText("Nome de usuário ou senha inválidos");
            Alerts.showAlert("Erro no Login", null, "Nome de usuário ou senha incorretos.", AlertType.ERROR);
        }
    }

    /**
     * Ação chamada quando o botão de cancelamento é pressionado.
     * Redireciona o usuário para a tela principal do sistema.
     */
    @FXML
    public void onBtCancelAction() {
        loadView("/gui/MainView.fxml", controller -> {});
    }

    /**
     * Carrega uma nova view (tela) no sistema.
     * 
     * @param fxmlPath O caminho para o arquivo FXML da view a ser carregada.
     * @param initializingAction Ação de inicialização que será executada no controlador da nova view.
     * @param <T> O tipo do controlador da nova view.
     */
    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load(); 

            // Carrega a nova view na tela principal
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
}
