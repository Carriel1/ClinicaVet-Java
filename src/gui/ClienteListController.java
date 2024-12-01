package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.services.ClienteService;

/**
 * Controlador da tela de listagem de clientes. 
 * Responsável por gerenciar a exibição e criação de novos clientes, além de atualizar os dados quando necessário.
 */
public class ClienteListController implements Initializable, DataChangeListener {

    /**
     * Serviço responsável pela manipulação dos dados de clientes.
     */
    private ClienteService service;

    @FXML
    private Button btNew; // Botão para criar um novo cliente

    /**
     * Método chamado quando o botão "Novo Cliente" é pressionado.
     * Este método cria uma nova instância de Cliente e abre a tela de cadastro.
     * 
     * @param event Evento disparado ao clicar no botão.
     */
    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Cliente obj = new Cliente(); // Criação de um novo cliente
        createDialogForm(obj, "/gui/ClienteRegistro.fxml", parentStage); // Abre o formulário de cadastro
    }

    /**
     * Método para configurar o serviço de clientes.
     * 
     * @param service Serviço que manipula os dados dos clientes.
     */
    public void setClienteService(ClienteService service) {
        this.service = service;
    }

    /**
     * Método inicial de configuração da tela, implementado da interface `Initializable`.
     * 
     * @param url URL usada para localização dos recursos.
     * @param rb Bundle de recursos que pode ser utilizado na inicialização.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Implementação vazia, mas pode ser utilizada para inicializações necessárias
    }

    /**
     * Atualiza a tabela de clientes na tela.
     * Caso o serviço de clientes não esteja configurado, lança uma exceção.
     */
    public void updateTableView() {
        if (service == null) throw new IllegalStateException("Service was null");
    }

    /**
     * Cria um diálogo de formulário para a entrada de dados do cliente.
     * Este método abre a tela de registro de cliente e configura os dados de cliente para edição ou criação.
     * 
     * @param obj Objeto cliente a ser registrado ou editado.
     * @param absoluteName Caminho absoluto do arquivo FXML.
     * @param parentStage A janela pai (onde o formulário será exibido).
     */
    private void createDialogForm(Cliente obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            ClienteRegistroController controller = loader.getController();
            controller.setCliente(obj);
            controller.setClienteService(new ClienteService());
            controller.subscribeDataChangeListener(this); // Notificação de alteração de dados
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Cliente data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait(); // Exibe o diálogo de forma modal
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Método chamado quando os dados são alterados (quando um cliente é criado ou editado).
     * 
     * @see DataChangeListener#onDataChanged()
     */
    @Override
    public void onDataChanged() {
        updateTableView();
    }

    /**
     * Método chamado quando o botão "Cancelar" é pressionado.
     * Fecha a tela de listagem de clientes e retorna à tela principal.
     */
    @FXML
    public void onBtCancelAction() {
        loadView("/gui/MainView.fxml", controller -> {}); // Carrega a tela principal
    }

    /**
     * Método genérico para carregar uma nova view no lugar da atual.
     * 
     * @param fxmlPath Caminho do arquivo FXML da nova view.
     * @param initializingAction Ação para inicializar o controlador da nova view.
     * @param <T> Tipo do controlador da nova view.
     */
    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); // Root principal da aplicação

            // Substitui o conteúdo do ScrollPane pela nova view
            mainScrollPane.setContent(newView);

            // Inicializa o controlador da nova view
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}


