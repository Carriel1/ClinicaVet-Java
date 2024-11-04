package gui;

import java.util.List;

import gui.util.Alerts;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.Veterinario;
import model.services.VeterinarioService;

public class VeterinarioListController {

    private VeterinarioService service;

    @FXML
    private TableView<Veterinario> tableViewVeterinarios;

    @FXML
    private TableColumn<Veterinario, Integer> tableColumnId;

    @FXML
    private TableColumn<Veterinario, String> tableColumnNome;

    @FXML
    private TableColumn<Veterinario, String> tableColumnEmail;

    @FXML
    private TableColumn<Veterinario, String> tableColumnTelefone;

    @FXML
    private Button btNewVeterinario;

    @FXML
    private Button btEditVeterinario;

    @FXML
    private Button btDeleteVeterinario;

    public void setVeterinarioService(VeterinarioService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        initializeNodes();
    }

    private void initializeNodes() {
        // Configura as colunas da tabela
        tableColumnId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tableColumnNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        tableColumnEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tableColumnTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));

        updateTableView(); // Atualiza a tabela logo que os nós são inicializados
    }


    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("VeterinarioService is null");
        }

        // Obter a lista de veterinários do serviço
        List<Veterinario> veterinarios = service.listarVeterinarios();
        ObservableList<Veterinario> obsList = FXCollections.observableArrayList(veterinarios);
        tableViewVeterinarios.setItems(obsList);
    }

    @FXML
    private void onBtNewVeterinarioAction() {
        // Ação para adicionar um novo veterinário
        Alerts.showAlert("Novo Veterinário", null, "Ação para adicionar um novo veterinário", AlertType.INFORMATION);
    }

    @FXML
    private void onBtEditVeterinarioAction() {
        // Ação para editar um veterinário selecionado
        Alerts.showAlert("Editar Veterinário", null, "Ação para editar um veterinário selecionado", AlertType.INFORMATION);
    }

    @FXML
    private void onBtDeleteVeterinarioAction() {
        // Ação para excluir um veterinário selecionado
        Alerts.showAlert("Excluir Veterinário", null, "Ação para excluir um veterinário selecionado", AlertType.INFORMATION);
    }
}

