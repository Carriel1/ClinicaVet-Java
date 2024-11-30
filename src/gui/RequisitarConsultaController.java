package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.entities.Animal;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;
import model.services.AnimalService;
import model.services.ConsultaService;

public class RequisitarConsultaController {

    private ConsultaService consultaService; // Acesso ao serviço de consultas
    private int clienteId;  // ID do cliente que requisitou a consulta
    private AnimalService animalService;
    
    @FXML
    private TextArea descricaoConsulta;  // Área para o cliente descrever a consulta

    @FXML
    private Button btnRequisitarConsulta;

    @FXML
    public void initialize() {
    	  consultaService = new ConsultaService(); 
    	  animalService = new AnimalService();
    }
    
    // Método para setar o ID do cliente
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    private Animal obterAnimalAssociadoAoCliente(int clienteId) {
        AnimalService animalService = new AnimalService();
        List<Animal> animais = animalService.buscarPorClienteId(clienteId);
        
        if (animais.isEmpty()) {
            return null; // Nenhum animal associado ao cliente
        }
        // Aqui, você pode retornar o primeiro animal ou mostrar uma lista para o usuário escolher
        return animais.get(0); // Exemplo: retorna o primeiro animal
    }

    // Método chamado ao clicar em "Requisitar Consulta"
    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        try {
            // Obter a descrição da consulta fornecida pelo cliente
            String descricao = descricaoConsulta.getText();
            
            if (descricao == null || descricao.trim().isEmpty()) {
                Alerts.showAlert("Aviso", "Descrição inválida", "Por favor, forneça uma descrição para a consulta.", AlertType.WARNING);
                return;
            }

            // Configurando cliente com ID válido
            Cliente cliente = new Cliente();
            cliente.setId(clienteId); // O ID foi atribuído anteriormente pelo método setClienteId

            // Buscando animal associado ao cliente
            Animal animal = obterAnimalAssociadoAoCliente(clienteId); // Crie este método para retornar o animal correto
            if (animal == null) {
                Alerts.showAlert("Erro", "Animal não selecionado", "Por favor, selecione um animal para a consulta.", AlertType.WARNING);
                return;
            }

            // Veterinário pode ser nulo neste momento
            Veterinario veterinario = null;

            // Configurando outros atributos da consulta
            LocalDate dataConsulta = LocalDate.now(); // Ajuste conforme sua lógica
            LocalTime horaConsulta = LocalTime.now(); // Ajuste conforme sua lógica
            String criadoPor = "Cliente"; // Marcado como cliente
            String status = "Requisitada"; // Status inicial

            // Criar a consulta
            Consulta consulta = new Consulta(null, cliente, veterinario, dataConsulta, horaConsulta, descricao, status, criadoPor, animal);

            // Salvar a consulta
            consultaService.salvarOuAtualizar(consulta);

            // Exibir mensagem de sucesso
            Alerts.showAlert("Sucesso", "Consulta requisitada", "Sua consulta foi registrada com sucesso e está aguardando aprovação.", AlertType.INFORMATION);

            // Fechar a janela após requisitar a consulta
            Stage stage = (Stage) btnRequisitarConsulta.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao requisitar consulta", "Ocorreu um erro ao requisitar a consulta. Tente novamente.", AlertType.ERROR);
        }
    }

}
