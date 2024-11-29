package gui;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import gui.util.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.entities.Consulta;
import model.services.AnimalService;
import model.services.ClienteService;
import model.services.ConsultaService;

public class ConsultasPendentesController {

    @FXML
    private TableView<Consulta> tblConsultas;

    @FXML
    private TableColumn<Consulta, String> colCliente;

    @FXML
    private TableColumn<Consulta, String> colAnimal;

    @FXML
    private TableColumn<Consulta, String> colData;

    private ClienteService clienteService;
    private AnimalService animalService;
    private ConsultaService consultaService;

    public ConsultasPendentesController() {
    }
    
    public ConsultasPendentesController(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService) {
        this.clienteService = clienteService;
        this.animalService = animalService;
        this.consultaService = consultaService;
    }

    public void setServices(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService) {
        if (clienteService == null || animalService == null || consultaService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.clienteService = clienteService;
        this.animalService = animalService;
        this.consultaService = consultaService;
        loadConsultasPendentes();
    }

    private void loadConsultasPendentes() {
        try {
            List<Consulta> consultasPendentes = consultaService.findConsultasPendentes();

            if (consultasPendentes.isEmpty()) {
                showErrorAlert("Nenhuma consulta pendente encontrada.");
            }

            colCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
            colAnimal.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getAnimal() != null ? cellData.getValue().getAnimal().getNome() : "Sem animal"));
            colData.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

            tblConsultas.getItems().clear();
            tblConsultas.getItems().addAll(consultasPendentes);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Falha ao carregar as consultas pendentes.");
        }
    }

    private void showErrorAlert(String message) {
        Alerts.showAlert("Erro", null, message, Alert.AlertType.ERROR);
    }

    @FXML
    public void onRealizarConsulta() {
        try {
            Consulta consultaSelecionada = tblConsultas.getSelectionModel().getSelectedItem();
            if (consultaSelecionada == null) {
                showErrorAlert("Selecione uma consulta para realizar.");
                return;
            }

            consultaService.marcarConsultaComoRealizada(consultaSelecionada);
            Alerts.showAlert("Sucesso", null, "Consulta realizada com sucesso.", Alert.AlertType.INFORMATION);
            loadConsultasPendentes();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Falha ao realizar a consulta.");
        }
    }
    
    public void abrirTelaRelatorio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/relatorio.fxml"));
            loader.setController(new RelatorioController(new ConsultaService())); // Injeção do serviço ConsultaService
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Relatório de Consultas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Falha ao carregar tela de relatório", "Não foi possível carregar a tela de relatório.", Alert.AlertType.ERROR);
        }
    }

}
