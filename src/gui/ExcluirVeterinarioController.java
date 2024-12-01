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

/**
 * Controlador da tela de exclusão de veterinários.
 * Gerencia a exibição e remoção de veterinários na tabela, interagindo com o serviço VeterinarioService.
 */
public class ExcluirVeterinarioController {

    @FXML
    private TableView<Veterinario> tableVeterinarios; // Tabela que exibe os veterinários

    @FXML
    private TableColumn<Veterinario, String> columnNome; // Coluna que exibe o nome dos veterinários

    @FXML
    private TableColumn<Veterinario, String> columnTelefone; // Coluna que exibe o telefone dos veterinários

    private VeterinarioService veterinarioService; // Serviço para acessar e manipular dados de veterinários

    private ObservableList<Veterinario> obsList; // Lista observável para armazenar veterinários

    /**
     * Método chamado automaticamente pelo JavaFX para inicializar o controlador.
     * Configura as colunas da tabela com os valores corretos.
     */
    @FXML
    public void initialize() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    /**
     * Injeta o serviço VeterinarioService e carrega os dados dos veterinários na tabela.
     *
     * @param service O serviço de veterinários que será usado para acessar dados.
     */
    public void setVeterinarioService(VeterinarioService service) {
        this.veterinarioService = service;
        loadTableData();
    }

    /**
     * Carrega os dados de veterinários na tabela a partir do serviço VeterinarioService.
     * Caso o serviço não tenha sido injetado, uma exceção será lançada.
     */
    private void loadTableData() {
        if (veterinarioService == null) {
            throw new IllegalStateException("VeterinarioService não foi injetado");
        }
        List<Veterinario> list = veterinarioService.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableVeterinarios.setItems(obsList);
    }

    /**
     * Exclui o veterinário selecionado da tabela e do banco de dados.
     * Exibe uma mensagem de sucesso ou erro após a tentativa de exclusão.
     */
    @FXML
    public void onExcluir() {
        Veterinario selecionado = tableVeterinarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alerts.showAlert("Erro", null, "Nenhum veterinário selecionado.", Alert.AlertType.WARNING);
            return;
        }

        try {
            veterinarioService.remove(selecionado); // Remove o veterinário do banco de dados
            obsList.remove(selecionado); // Remove o veterinário da lista observável
            Alerts.showAlert("Sucesso", null, "Veterinário excluído com sucesso!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            Alerts.showAlert("Erro", null, "Erro ao excluir veterinário: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Fecha a janela atual.
     * Este método é chamado quando o usuário deseja fechar a tela de exclusão.
     */
    @FXML
    public void onFechar() {
        Stage stage = (Stage) tableVeterinarios.getScene().getWindow();
        stage.close();
    }
}
