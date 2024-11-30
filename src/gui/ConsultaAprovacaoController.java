package gui;

import java.util.ArrayList;
import java.util.List;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Consulta;
import model.services.ConsultaService;

public class ConsultaAprovacaoController {

    @FXML
    private TableView<Consulta> tableConsultas;

    @FXML
    private TableColumn<Consulta, String> colCliente;

    @FXML
    private TableColumn<Consulta, String> colAnimal;

    @FXML
    private TableColumn<Consulta, String> colDataSolicitacao;

    @FXML
    private Button btnAceitar;

    @FXML
    private Button btnNegar;

    private ConsultaService consultaService;
    private List<Consulta> consultasPendentes;
    private Integer clienteId;  // Variável para armazenar o ID do cliente
    
    
    public ConsultaAprovacaoController() {
        this.consultaService = new ConsultaService();  // Instanciando o serviço manualmente
    }

    
    // Método para setar o clienteId
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public void setConsultaService(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        colAnimal.setCellValueFactory(new PropertyValueFactory<>("animalNome"));
        colDataSolicitacao.setCellValueFactory(new PropertyValueFactory<>("dataSolicitacao"));

        // Carregar as consultas requisitadas
        carregarConsultasRequisitadas();
    }


 // Método para carregar as consultas requisitadas
    public void carregarConsultasRequisitadas() {
        try {
            // Recupera a lista de consultas requisitadas, garantindo que não seja null
            List<Consulta> consultas = consultaService.buscarConsultasRequisitadas(); // Método alterado para buscar "Requisitadas"
            
            // Se a lista for null, inicializa com uma lista vazia
            if (consultas == null) {
                consultas = new ArrayList<>();
            }

            // Converte a lista para ObservableList
            ObservableList<Consulta> consultasRequisitadas = FXCollections.observableArrayList(consultas);
            
            // Atualiza a tabela com as consultas requisitadas
            tableConsultas.getItems().setAll(consultasRequisitadas);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar consultas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



 // Método para aprovar a consulta selecionada e mudar seu status para "Pendente"
    public void onAprovarConsulta(ActionEvent event) {
        Consulta consulta = tableConsultas.getSelectionModel().getSelectedItem();

        if (consulta == null) {
            Alerts.showAlert("Erro", "Selecione uma consulta", "Você precisa selecionar uma consulta para aprovar.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Verifica se a consulta tem o status "Requisitada"
            if ("Requisitada".equals(consulta.getStatus())) {
                consulta.setStatus("Pendente"); // Alterando o status para "Pendente"
                consultaService.aceitarConsulta(consulta); // Atualiza a consulta no banco de dados
                carregarConsultasRequisitadas(); // Atualiza a tabela com as novas consultas pendentes
                Alerts.showAlert("Sucesso", "Consulta aprovada", "A consulta foi aprovada com sucesso.", Alert.AlertType.INFORMATION);
            } else {
                Alerts.showAlert("Erro", "Status inválido", "A consulta não está no status 'Requisitada'.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao aprovar consulta", e.getMessage(), Alert.AlertType.ERROR);
        }
    }





    // Método para negar a consulta selecionada
    @FXML
    public void onNegarConsulta(ActionEvent event) {
        Consulta consulta = tableConsultas.getSelectionModel().getSelectedItem();

        if (consulta == null) {
            Alerts.showAlert("Erro", "Selecione uma consulta", "Você precisa selecionar uma consulta para negar.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Verifica se a consulta está no status "Requisitada"
            if ("Requisitada".equals(consulta.getStatus())) {
                consulta.setStatus("Negada"); // Alterando o status para "Negada"
                consultaService.negarConsulta(consulta); // Chama o serviço para negar a consulta
                carregarConsultasRequisitadas(); // Atualiza a tabela com as novas consultas pendentes
                Alerts.showAlert("Sucesso", "Consulta negada", "A consulta foi negada com sucesso.", Alert.AlertType.INFORMATION);
            } else {
                Alerts.showAlert("Erro", "Status inválido", "A consulta não está no status 'Requisitada'.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao negar consulta", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    // Método para fechar a tela de aprovação
    @FXML
    public void onFecharTela(ActionEvent event) {
        Stage stage = (Stage) btnAceitar.getScene().getWindow();
        stage.close();
    }
}

