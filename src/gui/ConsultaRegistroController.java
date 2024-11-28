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
import model.entities.Animal;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;
import model.services.AnimalService;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

public class ConsultaRegistroController {

    @FXML
    private ComboBox<Cliente> comboBoxCliente;

    @FXML
    private ComboBox<Veterinario> comboBoxVeterinario;

    @FXML
    private ComboBox<Animal> comboBoxAnimal;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private TextField txtHora;

    @FXML
    private TextArea textAreaDescricao;

    private ConsultaService consultaService;
    private ClienteService clienteService;
    private VeterinarioService veterinarioService;
    private AnimalService animalService;

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

    public void setAnimalService(AnimalService animalService) {
        this.animalService = animalService;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public ConsultaRegistroController() {
    }

    public ConsultaRegistroController(ClienteService clienteService, VeterinarioService veterinarioService, ConsultaService consultaService, AnimalService animalService) {
        this.clienteService = clienteService;
        this.veterinarioService = veterinarioService;
        this.consultaService = consultaService;
        this.animalService = animalService;
    }

    public void initialize() {
        if (clienteService == null) {
            clienteService = new ClienteService();
        }

        if (veterinarioService == null) {
            veterinarioService = new VeterinarioService();
        }

        if (consultaService == null) {
            consultaService = new ConsultaService();
        }

        if (animalService == null) {
            animalService = new AnimalService();
        }

        // Carregar os clientes e veterinários nos ComboBoxes
        List<Cliente> clientes = clienteService.findAll();
        comboBoxCliente.setItems(FXCollections.observableArrayList(clientes));

        List<Veterinario> veterinarios = veterinarioService.findAll();
        comboBoxVeterinario.setItems(FXCollections.observableArrayList(veterinarios));

        // Carregar os animais do cliente selecionado na ComboBox de Animal
        comboBoxCliente.setOnAction(event -> carregarAnimaisDoCliente());
    }

    private void carregarAnimaisDoCliente() {
        Cliente cliente = comboBoxCliente.getValue();
        if (cliente != null) {
        	Integer clienteId = cliente.getId();
        	List<Animal> animais = animalService.findByClienteId(clienteId);
            comboBoxAnimal.setItems(FXCollections.observableArrayList(animais));
        }
    }

    @FXML
    private void onSalvarConsulta() {
        try {
            Cliente cliente = comboBoxCliente.getValue();
            Veterinario veterinario = comboBoxVeterinario.getValue();
            Animal animal = comboBoxAnimal.getValue();
            LocalDate data = datePickerData.getValue();
            String descricao = textAreaDescricao.getText();

            // Validação para garantir que todos os campos obrigatórios estão preenchidos
            if (cliente == null || veterinario == null || data == null || descricao.isEmpty() || animal == null) {
                Alerts.showAlert("Erro", "Campos obrigatórios", "Todos os campos devem ser preenchidos!", AlertType.WARNING);
                return;
            }

            // Validação da hora
            LocalTime hora = null;
            String horaTexto = txtHora.getText();
            if (horaTexto.isEmpty()) {
                Alerts.showAlert("Erro", "Hora obrigatória", "Informe a hora para a consulta!", AlertType.WARNING);
                return;
            } else {
                hora = parseHora(horaTexto); // Parse da hora
                if (hora == null) {
                    Alerts.showAlert("Erro", "Formato inválido", "A hora informada não é válida!", AlertType.WARNING);
                    return;
                }
            }

            // Criação da consulta com todos os dados validados
            Consulta consulta = new Consulta(null, cliente, veterinario, data, hora, descricao, "Pendente", criadoPor, animal);

            // Salvar ou atualizar a consulta
            consultaService.salvarOuAtualizar(consulta);
	
            // Mostrar mensagem de sucesso
            Alerts.showAlert("Sucesso", null, "Consulta salva com sucesso!", AlertType.INFORMATION);

            // Fechar a tela de cadastro
            Stage stage = (Stage) comboBoxCliente.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alerts.showAlert("Erro", "Erro ao salvar consulta", e.getMessage(), AlertType.ERROR);
        }
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
