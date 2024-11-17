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
import model.entities.Cliente;
import model.services.ClienteService;

public class ClienteListController implements Initializable, DataChangeListener {

    private ClienteService service;

    @FXML
    private TableView<Cliente> tableViewCliente;

    @FXML
    private TableColumn<Cliente, Integer> tableColumnId;

    @FXML
    private TableColumn<Cliente, String> tableColumnName;

    @FXML
    private TableColumn<Cliente, String> tableColumnEmail;

    @FXML
    private TableColumn<Cliente, String> tableColumnPhone;

    @FXML
    private TableColumn<Cliente, Cliente> tableColumnEDIT;

    @FXML
    private TableColumn<Cliente, Cliente> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Cliente> obsList;

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
        initializeNodes();
    }

    // Inicializa as colunas da tabela
    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // Ajustar a altura da tabela com base no tamanho da janela
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewCliente.prefHeightProperty().bind(stage.heightProperty());
    }

    // Atualizar os dados na tabela
    public void updateTableView() {
        if (service == null) throw new IllegalStateException("Service was null");

        List<Cliente> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewCliente.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
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

    // Inicializa os botões de edição
    private void initEditButtons() {
        tableColumnEDIT.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
            private final Button button = new Button("Edit");

            @Override
            protected void updateItem(Cliente obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> createDialogForm(obj, "/gui/ClienteRegistro.fxml", Utils.currentStage(event)));
            }
        });
    }

    // Inicializa os botões de remoção
    private void initRemoveButtons() {
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
            private final Button button = new Button("Remove");

            @Override
            protected void updateItem(Cliente obj, boolean empty) {
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
}
