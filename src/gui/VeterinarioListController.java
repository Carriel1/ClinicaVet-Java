package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Veterinario;
import model.services.VeterinarioService;

public class VeterinarioListController implements Initializable, DataChangeListener {

    private VeterinarioService service;

    @FXML
    private TableView<Veterinario> tableViewVeterinario;

    @FXML
    private TableColumn<Veterinario, Integer> tableColumnId;

    @FXML
    private TableColumn<Veterinario, String> tableColumnName;

    @FXML
    private TableColumn<Veterinario, String> tableColumnEmail;

    @FXML
    private TableColumn<Veterinario, String> tableColumnTelefone;

    @FXML
    private TableColumn<Veterinario, Veterinario> tableColumnEDIT;

    @FXML
    private TableColumn<Veterinario, Veterinario> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Veterinario> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Veterinario obj = new Veterinario();
        createDialogForm(obj, "/gui/VeterinarioRegistro.fxml", parentStage);
    }

    public void setVeterinarioService(VeterinarioService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewVeterinario.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null) throw new IllegalStateException("Service was null");

        List<Veterinario> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewVeterinario.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Veterinario obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            VeterinarioRegistroController controller = loader.getController();
            controller.setVeterinario(obj);
            controller.setVeterinarioService(new VeterinarioService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Veterinario data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellFactory(param -> new TableCell<Veterinario, Veterinario>() {
            private final Button button = new Button("Edit");

            @Override
            protected void updateItem(Veterinario obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> createDialogForm(obj, "/gui/VeterinarioRegistro.fxml", Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Veterinario, Veterinario>() {
            private final Button button = new Button("Remove");

            @Override
            protected void updateItem(Veterinario obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Veterinario obj) {
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

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
