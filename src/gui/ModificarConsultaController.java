package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.entities.Consulta;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

/**
 * Controlador responsável por modificar uma consulta já existente.
 * Permite ao usuário selecionar uma consulta pendente e editar seus dados, como descrição, data e hora.
 */
public class ModificarConsultaController {

    @FXML
    private ComboBox<Consulta> comboConsulta;  // ComboBox para selecionar a consulta pendente

    @FXML
    private TextField txtDescricao;  // Campo de texto para editar a descrição da consulta

    @FXML
    private TextField txtData;  // Campo de texto para editar a data da consulta

    @FXML
    private TextField txtHora;  // Campo de texto para editar a hora da consulta

    @FXML
    private Button btnSalvar;  // Botão para salvar as modificações

    private ConsultaService consultaService;  // Serviço responsável pelas operações de consulta
    private ClienteService clienteService;  // Serviço responsável pelas operações de cliente
    private VeterinarioService veterinarioService;  // Serviço responsável pelas operações de veterinário

    /**
     * Define o serviço de consulta a ser usado neste controlador.
     * 
     * @param consultaService O serviço de consultas a ser usado.
     */
    public void setServices(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    /**
     * Define o serviço de cliente a ser usado neste controlador.
     * 
     * @param clienteService O serviço de cliente a ser usado.
     */
    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Define o serviço de veterinário a ser usado neste controlador.
     * 
     * @param veterinarioService O serviço de veterinário a ser usado.
     */
    public void setVeterinarioService(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    /**
     * Inicializa a tela, carregando as consultas pendentes no ComboBox.
     * Este método é chamado automaticamente pelo JavaFX durante a inicialização da tela.
     */
    @FXML
    public void initialize() {
        consultaService = new ConsultaService();
        try {
            // Carrega todas as consultas pendentes no ComboBox
            comboConsulta.getItems().addAll(consultaService.findConsultasPendentes()); 
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao carregar as consultas.", Alert.AlertType.ERROR);
        }

        // Adiciona listener para carregar os dados da consulta selecionada
        comboConsulta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                carregarConsulta(newValue);  // Carregar os dados da consulta
            }
        });
    }

    /**
     * Carrega os dados de uma consulta selecionada nos campos de edição.
     * 
     * @param consulta A consulta cujos dados serão carregados.
     */
    private void carregarConsulta(Consulta consulta) {
        txtDescricao.setText(consulta.getDescricao());
        txtData.setText(consulta.getData().toString());  
        txtHora.setText(consulta.getHora().toString());  
    }

    /**
     * Método chamado ao clicar no botão "Salvar".
     * Realiza a validação e salva as modificações feitas na consulta.
     * 
     * @param event O evento de clique do botão.
     */
    @FXML
    public void onSalvar(ActionEvent event) {
        try {
            // Obtém a consulta selecionada no ComboBox
            Consulta consultaSelecionada = comboConsulta.getSelectionModel().getSelectedItem();
            if (consultaSelecionada == null) {
                Alerts.showAlert("Erro", null, "Selecione uma consulta para modificar.", Alert.AlertType.WARNING);
                return;
            }

            // Obtém os valores dos campos de entrada
            String descricao = txtDescricao.getText();
            String dataStr = txtData.getText();
            String horaStr = txtHora.getText();

            LocalDate data = null;
            LocalTime hora = null;

            try {
                // Define os formatos esperados para a data e hora
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Altere o formato se necessário
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");     // Ajuste para 'HH:mm:ss' se necessário

                // Converte os valores de String para LocalDate e LocalTime
                data = LocalDate.parse(dataStr, dateFormatter);
                hora = LocalTime.parse(horaStr, timeFormatter);
            } catch (DateTimeParseException e) {
                // Mostra um alerta em caso de formato inválido
                Alerts.showAlert("Erro", null, "Formato de data ou hora inválido. Use os formatos 'yyyy-MM-dd' para data e 'HH:mm' para hora.", Alert.AlertType.ERROR);
                return;
            }

            // Atualiza os dados da consulta com as informações inseridas
            consultaSelecionada.setDescricao(descricao);
            consultaSelecionada.setData(data);
            consultaSelecionada.setHora(hora);

            // Chama o serviço para salvar as modificações no banco de dados
            consultaService.salvarOuAtualizar(consultaSelecionada);

            // Exibe mensagem de sucesso
            Alerts.showAlert("Sucesso", null, "Consulta modificada com sucesso!", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            // Trata exceções gerais e exibe alerta de erro
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao salvar as modificações.", Alert.AlertType.ERROR);
        }
    }
}
