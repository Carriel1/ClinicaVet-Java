package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Cliente;
import model.services.ClienteService;

public class ClienteListController implements Initializable {

    private ClienteService service;

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TableColumn<Cliente, Integer> tableColumnId;

    @FXML
    private TableColumn<Cliente, String> tableColumnName;

    @FXML
    private TableColumn<Cliente, String> tableColumnEmail;

    @FXML
    private TableColumn<Cliente, String> tableColumnPhone;

    @FXML
    private Button btNewCliente;

    @FXML
    private Button btEditCliente;

    @FXML
    private Button btDeleteCliente;

    public void setClienteService(ClienteService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        // Inicializa as colunas da tabela com as propriedades da entidade Cliente
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Cliente> list = service.listarClientes();
        tableViewClientes.getItems().setAll(list);
    }

    @FXML
    private void onBtNewClienteAction() {
        Alerts.showAlert("Novo Cliente", null, "Ação para adicionar um novo cliente", AlertType.INFORMATION);
    }

    @FXML
    private void onBtEditClienteAction() {
        Alerts.showAlert("Editar Cliente", null, "Ação para editar um cliente selecionado", AlertType.INFORMATION);
    }

    @FXML
    private void onBtDeleteClienteAction() {
        Alerts.showAlert("Excluir Cliente", null, "Ação para excluir um cliente selecionado", AlertType.INFORMATION);
    }
}
