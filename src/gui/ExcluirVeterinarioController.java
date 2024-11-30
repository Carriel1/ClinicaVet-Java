package gui;

import java.util.List;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Veterinario;
import model.services.VeterinarioService;

public class ExcluirVeterinarioController {

    @FXML
    private TableView<Veterinario> tableVeterinarios;

    @FXML
    private TableColumn<Veterinario, String> columnNome;

    @FXML
    private TableColumn<Veterinario, String> columnTelefone;

    private VeterinarioService veterinarioService;

    private ObservableList<Veterinario> obsList;

    @FXML
    public void initialize() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }


    public void setVeterinarioService(VeterinarioService service) {
        this.veterinarioService = service;
        loadTableData();
    }

    private void loadTableData() {
        if (veterinarioService == null) {
            throw new IllegalStateException("VeterinarioService não foi injetado");
        }
        List<Veterinario> list = veterinarioService.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableVeterinarios.setItems(obsList);
    }

    @FXML
    public void onExcluir() {
        Veterinario selecionado = tableVeterinarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alerts.showAlert("Erro", null, "Nenhum veterinário selecionado.", Alert.AlertType.WARNING);
            return;
        }

        try {
            veterinarioService.remove(selecionado);
            obsList.remove(selecionado);
            Alerts.showAlert("Sucesso", null, "Veterinário excluído com sucesso!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            Alerts.showAlert("Erro", null, "Erro ao excluir veterinário: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onFechar() {
        Stage stage = (Stage) tableVeterinarios.getScene().getWindow();
        stage.close();
    }
}
