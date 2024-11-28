package gui;

import java.util.List;

import gui.util.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    
    

	public void setServices(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService) {
	    if (clienteService == null || animalService == null || consultaService == null) {
	        throw new IllegalStateException("Os serviços não foram configurados corretamente.");
	    }
	    this.clienteService = clienteService;
	    this.animalService = animalService;
	    this.consultaService = consultaService;
	    loadConsultasPendentes();
	}
    
    public ConsultasPendentesController(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService ) {
        this.clienteService = clienteService;
        this.consultaService = consultaService;
        this.animalService = animalService;
    }
    
    public ConsultasPendentesController () {

    }
    

    private void loadConsultasPendentes() {
        try {
            List<Consulta> consultasPendentes = consultaService.findConsultasPendentes();
            System.out.println("Consultas pendentes: " + consultasPendentes);

            if (consultasPendentes.isEmpty()) {
                System.out.println("Nenhuma consulta pendente encontrada.");
            } else {
                System.out.println("Consultas pendentes carregadas: " + consultasPendentes.size());
            }

            // Depuração para checar os dados
            for (Consulta consulta : consultasPendentes) {
                System.out.println("Consulta ID: " + consulta.getId());
                System.out.println("Cliente: " + consulta.getCliente().getNome());
                if (consulta.getAnimal() != null) {
                    System.out.println("Animal: " + consulta.getAnimal().getNome());
                } else {
                    System.out.println("Animal: Não informado");
                }
                System.out.println("Data: " + consulta.getData());
                System.out.println("Hora: " + consulta.getHora());
                System.out.println("Descrição: " + consulta.getDescricao());
                System.out.println("------------------------");
            }

            // Definindo como exibir os dados nas colunas
            colCliente.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
            colAnimal.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getAnimal() != null ? cellData.getValue().getAnimal().getNome() : "Sem animal"));
            colData.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getData().toString())); // Converte a data para String
            
            // Limpa a tabela e adiciona as consultas pendentes
            tblConsultas.getItems().clear();
            tblConsultas.getItems().addAll(consultasPendentes);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao carregar as consultas pendentes.", Alert.AlertType.ERROR);
        }
    }




    // Método para selecionar uma consulta e marcar como realizada
    @FXML
    public void onRealizarConsulta() {
        try {
            Consulta consultaSelecionada = tblConsultas.getSelectionModel().getSelectedItem();
            if (consultaSelecionada == null) {
                Alerts.showAlert("Erro", null, "Selecione uma consulta para realizar.", Alert.AlertType.ERROR);
                return;
            }

            // Marque a consulta como realizada (você precisa implementar isso no seu serviço)
            consultaService.marcarConsultaComoRealizada(consultaSelecionada);

            Alerts.showAlert("Sucesso", null, "Consulta realizada com sucesso.", Alert.AlertType.INFORMATION);
            loadConsultasPendentes();  // Atualiza a tabela com as consultas restantes
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao realizar a consulta.", Alert.AlertType.ERROR);
        }
    }
}
