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
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.services.AnimalService;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

/**
 * Controlador responsável pela tela de login do veterinário no sistema.
 * Realiza a autenticação do veterinário e, em caso de sucesso, redireciona
 * para a tela principal do veterinário.
 */
public class LoginVeterinarioController {

    /**
     * Campo de texto para o nome do veterinário.
     */
    @FXML
    private TextField txtNome;

    /**
     * Campo de senha para a autenticação do veterinário.
     */
    @FXML
    private PasswordField txtSenha;

    /**
     * Serviço responsável pelas operações relacionadas ao veterinário.
     */
    private VeterinarioService veterinarioService;

    /**
     * Configura o serviço de autenticação do veterinário.
     * 
     * @param service O serviço de autenticação do veterinário.
     */
    public void setVeterinarioService(VeterinarioService service) {
        this.veterinarioService = service;
    }

    /**
     * Ação chamada quando o botão de login é pressionado.
     * Realiza a autenticação do veterinário utilizando o nome e a senha fornecidos.
     * Se a autenticação for bem-sucedida, redireciona o veterinário para a tela principal do sistema.
     * Caso contrário, exibe uma mensagem de erro.
     * 
     * @param event O evento de clique do botão de login.
     */
    @FXML
    private void onBtLoginAction(ActionEvent event) {
        String nome = txtNome.getText();
        String senha = txtSenha.getText();

        // Verifica se o serviço foi configurado
        if (veterinarioService == null) {
            Alerts.showAlert("Erro no Login", null, "Service não foi configurado.", AlertType.ERROR);
            return;
        }

        // Verifica as credenciais do veterinário usando o método authenticate
        boolean autenticado = veterinarioService.authenticate(nome, senha);
        if (autenticado) {
            // Se o login for bem-sucedido, carrega a tela principal do veterinário
            loadTelaPrincipalVeterinario(event);
        } else {
            // Caso contrário, exibe um alerta de erro
            Alerts.showAlert("Login", null, "Nome ou senha inválidos!", AlertType.ERROR);
        }
    }

    /**
     * Carrega a tela principal do veterinário após a autenticação bem-sucedida.
     * Inicializa os serviços necessários e passa-os para o controlador da tela principal.
     * 
     * @param event O evento de clique do botão de login.
     */
    private void loadTelaPrincipalVeterinario(ActionEvent event) {
        try {
            // Carregar o FXML da tela principal do veterinário
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipalVeterinario.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Obter o controlador da tela principal do veterinário
            TelaPrincipalVeterinarioController controller = loader.getController();
            controller.setWelcomeMessage("Bem-vindo, Veterinário!");

            // Criar instâncias dos serviços
            AnimalService animalService = new AnimalService();
            ClienteService clienteService = new ClienteService();
            ConsultaService consultaService = new ConsultaService();  // Adiciona o ConsultaService
            VeterinarioService veterinarioService = new VeterinarioService();
            
            // Passar os serviços para o controlador
            controller.setServices(animalService, clienteService, consultaService, veterinarioService);

            // Obter a janela atual e mudar a cena
            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alerts.showAlert("Erro", null, "Não foi possível carregar a tela principal do veterinário.", AlertType.ERROR);
            e.printStackTrace();
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

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); 

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
}
