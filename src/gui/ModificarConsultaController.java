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

public class ModificarConsultaController {

    @FXML
    private ComboBox<Consulta> comboConsulta;  
    
    @FXML
    private TextField txtDescricao;  

    @FXML
    private TextField txtData;  
    
    @FXML
    private TextField txtHora;  

    @FXML
    private Button btnSalvar;  

    private ConsultaService consultaService;
    private ClienteService clienteService;
    private VeterinarioService veterinarioService;

    public void setServices(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void setVeterinarioService(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    // Método para carregar as consultas no ComboBox
    @FXML
    public void initialize() {
  	  consultaService = new ConsultaService(); 
    	try {
            comboConsulta.getItems().addAll(consultaService.findConsultasPendentes()); 
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao carregar as consultas.", Alert.AlertType.ERROR);
        }

        comboConsulta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                carregarConsulta(newValue);  // Carregar os dados da consulta
            }
        });
    }

    // Método para carregar os dados da consulta selecionada
    private void carregarConsulta(Consulta consulta) {
        txtDescricao.setText(consulta.getDescricao());
        txtData.setText(consulta.getData().toString());  
        txtHora.setText(consulta.getHora().toString());  
    }

 // Método para salvar as modificações na consulta
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
