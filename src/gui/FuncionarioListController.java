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


public class FuncionarioListController implements Initializable, DataChangeListener {

    private FuncionarioService service;

    @FXML
    private Button btNew;

    @FXML
    private Button btCancel;

    public void setFuncionarioService(FuncionarioService service) {
        this.service = service;
    }

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Funcionario obj = new Funcionario();
        createDialogForm(obj, "/gui/FuncionarioForm.fxml", parentStage);
    }

    // Método de cancelamento para fechar a tela
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

            // Inicializar o controlador
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

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
            dialogStage.setTitle("Enter Funcionario data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
    }
}
