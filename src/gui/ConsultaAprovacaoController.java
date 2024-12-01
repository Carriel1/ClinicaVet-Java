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

/**
 * Controlador para a tela de aprovação de consultas.
 * Permite ao usuário aprovar ou negar consultas requisitadas.
 */
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

    /**
     * Construtor da classe. Instancia o serviço de consulta.
     */
    public ConsultaAprovacaoController() {
        this.consultaService = new ConsultaService();  // Instanciando o serviço manualmente
    }

    /**
     * Define o ID do cliente.
     * 
     * @param clienteId O ID do cliente.
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Define o serviço de consulta.
     * 
     * @param consultaService O serviço de consulta.
     */
    public void setConsultaService(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    /**
     * Inicializa o controlador. Configura as colunas da tabela e carrega as consultas.
     */
    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        colAnimal.setCellValueFactory(new PropertyValueFactory<>("animalNome"));
        colDataSolicitacao.setCellValueFactory(new PropertyValueFactory<>("dataSolicitacao"));

        // Carregar as consultas requisitadas
        carregarConsultasRequisitadas();
    }

    /**
     * Carrega as consultas requisitadas e as exibe na tabela.
     * Caso ocorra um erro, exibe uma mensagem de alerta.
     */
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

    /**
     * Aprova a consulta selecionada e altera seu status para "Pendente".
     * Exibe uma mensagem de sucesso ou erro dependendo do resultado da operação.
     * 
     * @param event O evento gerado pelo clique do botão.
     */
    @FXML
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

    /**
     * Nega a consulta selecionada e altera seu status para "Negada".
     * Exibe uma mensagem de sucesso ou erro dependendo do resultado da operação.
     * 
     * @param event O evento gerado pelo clique do botão.
     */
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

    /**
     * Fecha a tela de aprovação de consultas.
     * 
     * @param event O evento gerado pelo clique do botão.
     */
    @FXML
    public void onFecharTela(ActionEvent event) {
        Stage stage = (Stage) btnAceitar.getScene().getWindow();
        stage.close();
    }
}

