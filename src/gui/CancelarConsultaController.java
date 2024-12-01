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

/**
 * Controlador responsável por cancelar consultas.
 * Este controlador é utilizado na tela para que um funcionário ou veterinário possa cancelar uma consulta pendente.
 */
public class CancelarConsultaController {

    @FXML
    private ComboBox<Consulta> comboConsulta;

    @FXML
    private Button btnCancelar;

    /**
     * Serviço responsável pela lógica de negócio da entidade Consulta.
     */
    private ConsultaService consultaService;

    /**
     * Método para injeção do serviço `ConsultaService`.
     * Este método é chamado para configurar o serviço necessário para o gerenciamento de consultas.
     * 
     * @param consultaService Serviço para manipulação dos dados de consultas.
     */
    public void setServices(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    /**
     * Método inicial que carrega as consultas pendentes no ComboBox. 
     * Caso ocorra algum erro ao carregar as consultas, um alerta é exibido.
     */
    @FXML
    public void initialize() {
        consultaService = new ConsultaService();
        try {
            // Preenche o ComboBox com as consultas pendentes
            comboConsulta.getItems().addAll(consultaService.findConsultasPendentes());
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao carregar as consultas.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método chamado quando o botão "Cancelar" é pressionado.
     * Realiza a validação da consulta selecionada e cancela a consulta se válida.
     * 
     * @param event Evento disparado ao clicar no botão "Cancelar".
     */
    @FXML
    public void onCancelar(ActionEvent event) {
        try {
            Consulta consultaSelecionada = comboConsulta.getSelectionModel().getSelectedItem();
            if (consultaSelecionada == null) {
                Alerts.showAlert("Erro", null, "Selecione uma consulta para cancelar.", Alert.AlertType.WARNING);
                return;
            }

            consultaService.cancelarConsulta(consultaSelecionada);

            Alerts.showAlert("Sucesso", null, "Consulta cancelada com sucesso!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao cancelar a consulta.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para fechar a tela de cancelamento de consulta.
     * 
     * @param event Evento disparado ao clicar no botão "Fechar".
     */
    @FXML
    public void onFechar(ActionEvent event) {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }
}

