package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import db.DbIntegrityException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.services.ClienteService;

public class ClienteListController implements Initializable, DataChangeListener {

    private ClienteService service;

    @FXML
    private Button btNew;

    // Método para criar um novo cliente
    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Cliente obj = new Cliente();
        createDialogForm(obj, "/gui/ClienteRegistro.fxml", parentStage);
    }

    // Configurar o serviço
    public void setClienteService(ClienteService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // Atualizar os dados na tela
    public void updateTableView() {
        if (service == null) throw new IllegalStateException("Service was null");

        List<Cliente> list = service.findAll();
    }

    // Método para criar o diálogo de formulário
    private void createDialogForm(Cliente obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            ClienteRegistroController controller = loader.getController();
            controller.setCliente(obj);
            controller.setClienteService(new ClienteService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Cliente data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    // Método para remover um cliente
    private void removeEntity(Cliente obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure you want to delete?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (service == null) throw new IllegalStateException("Service was null");

            try {
                service.remove(obj);
                updateTableView();
            } catch (DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
            }
        }
    }

    // Método chamado quando os dados são alterados (novo cliente ou edição)
    @Override
    public void onDataChanged() {
        updateTableView();
    }

    // Método para cancelar a operação
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
}

