package gui;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.entities.Consulta;
import model.services.ConsultaService;

public class CancelarConsultaController {

    @FXML
    private ComboBox<Consulta> comboConsulta;  // ComboBox para selecionar a consulta

    @FXML
    private Button btnCancelar;  // Botão para cancelar a consulta

    private ConsultaService consultaService;

    // Método para injeção do serviço
    public void setServices(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // Método para carregar as consultas disponíveis no ComboBox
    @FXML
    public void initialize() {
    	  consultaService = new ConsultaService(); 
    	try {
            // Preenche o ComboBox com as consultas disponíveis
            comboConsulta.getItems().addAll(consultaService.findConsultasPendentes()); 
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao carregar as consultas.", Alert.AlertType.ERROR);
        }
    }

    // Método para cancelar a consulta
    @FXML
    public void onCancelar(ActionEvent event) {
        try {
            Consulta consultaSelecionada = comboConsulta.getSelectionModel().getSelectedItem();
            if (consultaSelecionada == null) {
                Alerts.showAlert("Erro", null, "Selecione uma consulta para cancelar.", Alert.AlertType.WARNING);
                return;
            }

            // Cancela a consulta
            consultaService.cancelarConsulta(consultaSelecionada);

            Alerts.showAlert("Sucesso", null, "Consulta cancelada com sucesso!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao cancelar a consulta.", Alert.AlertType.ERROR);
        }
    }

    // Método para fechar a tela
    @FXML
    public void onFechar(ActionEvent event) {
        // Fecha a tela de cancelamento
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }
}
