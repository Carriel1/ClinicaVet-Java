package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

public class ConsultaRegistroController {

    @FXML
    private ComboBox<Cliente> comboBoxCliente;

    @FXML
    private ComboBox<Veterinario> comboBoxVeterinario;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private TextField txtHora;

    @FXML
    private TextArea textAreaDescricao;

    private ConsultaService consultaService;
    private ClienteService clienteService;
    private VeterinarioService veterinarioService;

    // Dependência para obter o nome do usuário (funcionário, por exemplo)
    private String criadoPor;

    public void setConsultaService(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void setVeterinarioService(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }
    
    public ConsultaRegistroController () {
    	
    }
    
    public ConsultaRegistroController(ClienteService clienteService, VeterinarioService veterinarioService, ConsultaService consultaService ) {
        this.clienteService = clienteService;
        this.veterinarioService = veterinarioService;
    }
    
    public void initialize() {
      
    	 if (clienteService == null) {
             clienteService = new ClienteService(); // Instanciando manualmente se necessário
    	 }	
    	 
    	  if (veterinarioService == null) {
              veterinarioService = new VeterinarioService();
          }
    	  
    	  if (consultaService == null) {
              consultaService = new ConsultaService();
          }
    	 
    	// Carregar os clientes e veterinários nos ComboBoxes
        List<Cliente> clientes = clienteService.findAll();
        comboBoxCliente.setItems(FXCollections.observableArrayList(clientes));

        List<Veterinario> veterinarios = veterinarioService.findAll();
        comboBoxVeterinario.setItems(FXCollections.observableArrayList(veterinarios));
    }

    @FXML
    private void onSalvarConsulta() {
        try {
           // if (!validateFields()) {
                Cliente cliente = comboBoxCliente.getValue();
                Veterinario veterinario = comboBoxVeterinario.getValue();
                LocalDate data = datePickerData.getValue();
                String descricao = textAreaDescricao.getText();

                // Validação para garantir que todos os campos obrigatórios estão preenchidos
                if (cliente == null || veterinario == null || data == null || descricao.isEmpty()) {
                    Alerts.showAlert("Erro", "Campos obrigatórios", "Todos os campos devem ser preenchidos!", AlertType.WARNING);
                    return;
                }

                // Validação da hora: Verifique se o campo 'hora' não está vazio
                LocalTime hora = null;
                String horaTexto = txtHora.getText();
                if (horaTexto.isEmpty()) {
                    Alerts.showAlert("Erro", "Hora obrigatória", "Informe a hora para a consulta!", AlertType.WARNING);
                    return; // Retorna para não continuar o processo de salvamento
                } else {
                    hora = parseHora(horaTexto); // Parse da hora
                    if (hora == null) {
                        Alerts.showAlert("Erro", "Formato inválido", "A hora informada não é válida!", AlertType.WARNING);
                        return;
                    }
                }

                // Criação da consulta com todos os dados validados
                Consulta consulta = new Consulta(null, cliente, veterinario, data, hora, descricao, "Aprovada", criadoPor);

                // Salvar ou atualizar a consulta
                consultaService.salvarOuAtualizar(cliente, veterinario, data, hora, descricao, criadoPor);

                // Mostrar mensagem de sucesso
                Alerts.showAlert("Sucesso", null, "Consulta salva com sucesso!", AlertType.INFORMATION);

                // Fechar a tela de cadastro
                Stage stage = (Stage) comboBoxCliente.getScene().getWindow();
                stage.close();
              //  return;
         //   }
        } catch (Exception e) {
            Alerts.showAlert("Erro", "Erro ao salvar consulta", e.getMessage(), AlertType.ERROR);
        }
    }


    private boolean validateFields() {
        if (comboBoxCliente.getValue() == null) {
            Alerts.showAlert("Erro", "Cliente obrigatório", "Selecione um cliente.", AlertType.WARNING);
            return false;
        }
        if (comboBoxVeterinario.getValue() == null) {
            Alerts.showAlert("Erro", "Veterinário obrigatório", "Selecione um veterinário.", AlertType.WARNING);
            return false;
        }
        if (datePickerData.getValue() == null) {
            Alerts.showAlert("Erro", "Data obrigatória", "Selecione uma data.", AlertType.WARNING);
            return false;
        }
        return true;
    }


    @FXML
    private void onCancelar() {
        Stage stage = (Stage) comboBoxCliente.getScene().getWindow();
        stage.close(); // Fecha a janela sem salvar
    }

    private LocalTime parseHora(String hora) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(hora, formatter);
        } catch (DateTimeParseException e) {
            Alerts.showAlert("Erro", "Hora inválida", "Formato correto: HH:mm", AlertType.ERROR);
            return null;
        }
    }
}

