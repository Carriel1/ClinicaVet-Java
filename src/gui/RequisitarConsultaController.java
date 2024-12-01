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

/**
 * Controlador responsável pela interface de requisição de consulta.
 * Permite que o cliente forneça uma descrição para a consulta e selecione um animal
 * para associar à consulta. A consulta será criada e registrada no sistema.
 */
public class RequisitarConsultaController {

    private ConsultaService consultaService;  // Serviço para salvar consultas
    private int clienteId;  // ID do cliente que está requisitando a consulta
    private AnimalService animalService;  // Serviço para interagir com os animais
    
    @FXML
    private TextArea descricaoConsulta;  // Área de texto para inserir a descrição da consulta

    @FXML
    private Button btnRequisitarConsulta;  // Botão para requisitar a consulta

    /**
     * Inicializa os serviços necessários para a operação do controlador.
     * Este método é chamado automaticamente ao carregar o controlador.
     */
    @FXML
    public void initialize() {
        consultaService = new ConsultaService(); 
        animalService = new AnimalService();
    }
    
    /**
     * Define o ID do cliente que está requisitando a consulta.
     * 
     * @param clienteId O ID do cliente.
     */
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    /**
     * Obtém o animal associado ao cliente, com base no ID do cliente.
     * 
     * @param clienteId O ID do cliente.
     * @return O animal associado ao cliente, ou null se não houver animais associados.
     */
    private Animal obterAnimalAssociadoAoCliente(int clienteId) {
        AnimalService animalService = new AnimalService();
        List<Animal> animais = animalService.buscarPorClienteId(clienteId);
        
        if (animais.isEmpty()) {
            return null;  // Retorna null caso não exista nenhum animal associado
        }
        return animais.get(0);  // Retorna o primeiro animal encontrado
    }

    /**
     * Método que é chamado quando o botão de requisitar consulta é clicado.
     * Verifica a validade da descrição da consulta, associa o animal ao cliente,
     * cria a consulta e a salva no sistema.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        try {
            String descricao = descricaoConsulta.getText();
            
            // Verifica se a descrição da consulta é válida
            if (descricao == null || descricao.trim().isEmpty()) {
                Alerts.showAlert("Aviso", "Descrição inválida", "Por favor, forneça uma descrição para a consulta.", AlertType.WARNING);
                return;
            }

            // Configura o cliente com o ID válido
            Cliente cliente = new Cliente();
            cliente.setId(clienteId); 

            // Obtém o animal associado ao cliente
            Animal animal = obterAnimalAssociadoAoCliente(clienteId); 
            if (animal == null) {
                Alerts.showAlert("Erro", "Animal não selecionado", "Por favor, selecione um animal para a consulta.", AlertType.WARNING);
                return;
            }

            // Inicializa o veterinário como null, pois o cliente está requisitando a consulta
            Veterinario veterinario = null;

            // Define a data e hora atuais para a consulta
            LocalDate dataConsulta = LocalDate.now(); 
            LocalTime horaConsulta = LocalTime.now(); 
            String criadoPor = "Cliente";  // A consulta foi requisitada pelo cliente
            String status = "Requisitada";  // O status inicial da consulta

            // Cria a consulta com os dados fornecidos
            Consulta consulta = new Consulta(null, cliente, veterinario, dataConsulta, horaConsulta, descricao, status, criadoPor, animal);

            // Salva a consulta no banco de dados
            consultaService.salvarOuAtualizar(consulta);

            // Exibe uma mensagem de sucesso
            Alerts.showAlert("Sucesso", "Consulta requisitada", "Sua consulta foi registrada com sucesso e está aguardando aprovação.", AlertType.INFORMATION);

            // Fecha a janela após requisitar a consulta
            Stage stage = (Stage) btnRequisitarConsulta.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao requisitar consulta", "Ocorreu um erro ao requisitar a consulta. Tente novamente.", AlertType.ERROR);
        }
    }
}
