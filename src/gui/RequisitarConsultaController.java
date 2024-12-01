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

    private ConsultaService consultaService; 
    private int clienteId;  
    private AnimalService animalService;
    
    @FXML
    private TextArea descricaoConsulta;  

    @FXML
    private Button btnRequisitarConsulta;

    @FXML
    public void initialize() {
    	  consultaService = new ConsultaService(); 
    	  animalService = new AnimalService();
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    private Animal obterAnimalAssociadoAoCliente(int clienteId) {
        AnimalService animalService = new AnimalService();
        List<Animal> animais = animalService.buscarPorClienteId(clienteId);
        
        if (animais.isEmpty()) {
            return null; 
        }
        return animais.get(0); 
    }

    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        try {
            String descricao = descricaoConsulta.getText();
            
            if (descricao == null || descricao.trim().isEmpty()) {
                Alerts.showAlert("Aviso", "Descrição inválida", "Por favor, forneça uma descrição para a consulta.", AlertType.WARNING);
                return;
            }

            // Configurando cliente com ID válido
            Cliente cliente = new Cliente();
            cliente.setId(clienteId); 

            // Buscando animal associado ao cliente
            Animal animal = obterAnimalAssociadoAoCliente(clienteId); 
            if (animal == null) {
                Alerts.showAlert("Erro", "Animal não selecionado", "Por favor, selecione um animal para a consulta.", AlertType.WARNING);
                return;
            }

            Veterinario veterinario = null;

            LocalDate dataConsulta = LocalDate.now(); 
            LocalTime horaConsulta = LocalTime.now(); 
            String criadoPor = "Cliente"; 
            String status = "Requisitada"; 

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
