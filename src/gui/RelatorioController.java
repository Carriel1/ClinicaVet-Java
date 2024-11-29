package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.entities.Consulta;
import model.services.ConsultaService;

import java.util.List;

public class RelatorioController {

    private ConsultaService consultaService;

    // Construtor do controlador com injeção do ConsultaService
    public RelatorioController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // Método para gerar relatório de consultas
    @FXML
    public void gerarRelatorio() {
        try {
            // Buscar as consultas pendentes utilizando o ConsultaService
            List<Consulta> consultasPendentes = consultaService.findConsultasPendentes();

            if (consultasPendentes.isEmpty()) {
                Alerts.showAlert("Aviso", null, "Nenhuma consulta pendente para gerar o relatório.", Alert.AlertType.INFORMATION);
            } else {
                // Lógica para gerar o relatório
                for (Consulta consulta : consultasPendentes) {
                    // Aqui, você pode gerar um relatório ou processar a lista de consultas pendentes
                    System.out.println("Consulta Pendente: " + consulta);
                }
                Alerts.showAlert("Sucesso", null, "Relatório gerado com sucesso!", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Erro ao gerar o relatório.", Alert.AlertType.ERROR);
        }
    }
}

