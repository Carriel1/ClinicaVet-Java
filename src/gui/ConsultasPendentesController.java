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

/**
 * Controlador responsável por gerenciar a tela de consultas pendentes no sistema.
 * Esta classe permite visualizar, selecionar e realizar consultas pendentes.
 */
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

    /**
     * Construtor padrão da classe.
     */
    public ConsultasPendentesController() {
    }

    /**
     * Construtor com os serviços necessários.
     * 
     * @param clienteService o serviço de clientes
     * @param animalService o serviço de animais
     * @param consultaService o serviço de consultas
     */
    public ConsultasPendentesController(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService) {
        this.clienteService = clienteService;
        this.animalService = animalService;
        this.consultaService = consultaService;
    }

    /**
     * Configura os serviços necessários e carrega as consultas pendentes.
     * 
     * @param clienteService o serviço de clientes
     * @param animalService o serviço de animais
     * @param consultaService o serviço de consultas
     */
    public void setServices(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService) {
        if (clienteService == null || animalService == null || consultaService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.clienteService = clienteService;
        this.animalService = animalService;
        this.consultaService = consultaService;
        loadConsultasPendentes();
    }

    /**
     * Carrega as consultas pendentes na tabela.
     * Atualiza os dados das colunas e exibe os dados de clientes, animais e data de consulta.
     */
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

    /**
     * Exibe um alerta de erro com a mensagem fornecida.
     * 
     * @param message a mensagem a ser exibida no alerta
     */
    private void showErrorAlert(String message) {
        Alerts.showAlert("Erro", null, message, Alert.AlertType.ERROR);
    }

    /**
     * Método chamado ao clicar no botão "Realizar Consulta".
     * Marca a consulta como realizada e abre a tela de relatório correspondente.
     */
    @FXML
    public void onRealizarConsulta() {
        try {
            Consulta consultaSelecionada = tblConsultas.getSelectionModel().getSelectedItem();
            if (consultaSelecionada == null) {
                showErrorAlert("Selecione uma consulta para realizar.");
                return;
            }

            // Marcar a consulta como realizada
            consultaService.marcarConsultaComoRealizada(consultaSelecionada);

            // Abrir a tela de relatório vinculada à consulta selecionada
            abrirTelaRelatorio(consultaSelecionada);

            Alerts.showAlert("Sucesso", null, "Consulta realizada com sucesso.", Alert.AlertType.INFORMATION);
            loadConsultasPendentes();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Falha ao realizar a consulta.");
        }
    }

    /**
     * Abre a tela de relatório para a consulta selecionada.
     * 
     * @param consulta a consulta para a qual o relatório será gerado
     */
    public void abrirTelaRelatorio(Consulta consulta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/relatorio.fxml"));
            Parent root = loader.load();

            // Obter o controlador e passar a consulta
            RelatorioController relatorioController = loader.getController();
            relatorioController.setConsulta(consulta);

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
