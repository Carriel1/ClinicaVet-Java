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
import model.entities.Funcionario;
import model.services.FuncionarioService;

/**
 * Controlador responsável por gerenciar a lista de Funcionários.
 * Permite a exibição, criação de novos registros e navegação entre as telas do sistema.
 * Inclui funcionalidades para abrir formulários de cadastro e editar informações de Funcionários.
 */
public class FuncionarioListController implements Initializable, DataChangeListener {

    /**
     * Serviço utilizado para manipulação de dados dos Funcionários.
     */
    private FuncionarioService service;

    /**
     * Botão para criar um novo Funcionario.
     */
    @FXML
    private Button btNew;

    /**
     * Botão para cancelar e voltar à tela principal.
     */
    @FXML
    private Button btCancel;

    /**
     * Define o serviço de Funcionario que será utilizado para salvar ou manipular os dados dos Funcionários.
     * 
     * @param service O serviço de Funcionario.
     */
    public void setFuncionarioService(FuncionarioService service) {
        this.service = service;
    }

    /**
     * Ação chamada quando o botão de "Novo Funcionario" é clicado.
     * Abre o formulário para criar um novo Funcionario.
     * 
     * @param event O evento de ação do botão.
     */
    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Funcionario obj = new Funcionario();
        createDialogForm(obj, "/gui/FuncionarioForm.fxml", parentStage);
    }

    /**
     * Ação chamada quando o botão de cancelamento é clicado.
     * Retorna à tela principal do sistema.
     */
    @FXML
    public void onBtCancelAction() {
        loadView("/gui/MainView.fxml", controller -> {});
    }

    /**
     * Método genérico para carregar uma nova view do sistema.
     * 
     * @param fxmlPath O caminho do arquivo FXML a ser carregado.
     * @param initializingAction Ação que será realizada após a inicialização do controlador.
     */
    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();  

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); 

            mainScrollPane.setContent(newView);

            // Inicializar o controlador da nova view
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Exceção de IO", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método de inicialização do controlador. 
     * Não há implementação específica para a inicialização dessa tela.
     * 
     * @param url O local do arquivo FXML.
     * @param rb Os recursos para o local atual.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nenhuma inicialização específica.
    }

    /**
     * Cria e exibe o formulário de cadastro ou edição de Funcionario.
     * 
     * @param obj O Funcionario a ser editado ou criado.
     * @param absoluteName O caminho do arquivo FXML que será carregado.
     * @param parentStage A janela pai para a modificação do modal.
     */
    private void createDialogForm(Funcionario obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            FuncionarioFormController controller = loader.getController();
            controller.setFuncionario(obj);
            controller.setFuncionarioService(new FuncionarioService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Funcionario");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("Exceção de IO", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Método chamado quando os dados são alterados. 
     * Atualmente, não há implementação específica.
     */
    @Override
    public void onDataChanged() {
    }
}

