package gui;

import java.io.IOException;
import java.util.List;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Veterinario;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

/**
 * Controlador da tela principal do funcionário, que gerencia as ações do menu principal.
 * Esta tela permite realizar ações como cadastro de veterinário e cliente, marcação e modificação de consultas,
 * controle de estoque, e geração de relatórios.
 */
public class TelaPrincipalFuncionarioController {

    @FXML
    private Label lblWelcomeMessage;

    @FXML
    private Button btnCadastrarVeterinario;

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private Button btnConsulta;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnSair;

    private ClienteService clienteService;
    private VeterinarioService veterinarioService;
    private ConsultaService consultaService;

    /**
     * Inicializa os serviços necessários para a tela principal do funcionário.
     * Esse método é chamado automaticamente ao carregar a tela.
     */
    @FXML
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
        
        List<Cliente> clientes = clienteService.findAll(); 
    }

    /**
     * Abre a tela de cadastro de veterinário quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onCadastrarVeterinario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VeterinarioRegistro.fxml"));
            Parent parent = loader.load();

            VeterinarioRegistroController controller = loader.getController();
            controller.setVeterinarioService(veterinarioService);
            controller.setVeterinario(new Veterinario());

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Veterinário");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre a tela de cadastro de cliente quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onCadastrarCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ClienteRegistro.fxml"));
            Parent parent = loader.load();

            ClienteRegistroController controller = loader.getController();
            controller.setClienteService(clienteService);
            controller.setCliente(new Cliente());

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cliente");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre a tela de marcação de consulta quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onMarcarConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultaRegistro.fxml"));
            Parent parent = loader.load();

            ConsultaRegistroController controller = loader.getController();
            controller.setConsultaService(consultaService); 
            controller.setClienteService(clienteService);    
            controller.setVeterinarioService(veterinarioService); 

            Stage stage = new Stage();
            stage.setTitle("Registrar Consulta");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); 
            stage.initModality(Modality.WINDOW_MODAL);  
            stage.showAndWait(); 

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre a tela de cancelamento de consulta quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onCancelarConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CancelarConsulta.fxml"));
            Parent root = loader.load();

            CancelarConsultaController cancelarConsultaController = loader.getController();
            
            cancelarConsultaController.setServices(consultaService); // Passa o ConsultaService

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de cancelamento de consulta.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre a tela de modificação de consulta quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onModificarConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModificarConsulta.fxml"));
            Parent root = loader.load();

            ModificarConsultaController modificarConsultaController = loader.getController();
            
            modificarConsultaController.setServices(consultaService); 
            modificarConsultaController.setClienteService(clienteService); 
            modificarConsultaController.setVeterinarioService(veterinarioService); 

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de modificação de consulta.", Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Abre a tela de exclusão de veterinário quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onExcluirVeterinario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ExcluirVeterinario.fxml"));
            Parent root = loader.load();

            ExcluirVeterinarioController excluirVeterinarioController = loader.getController();
            
            excluirVeterinarioController.setVeterinarioService(veterinarioService);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Excluir Veterinário");
            stage.initOwner(Utils.currentStage(event)); 
            stage.initModality(Modality.WINDOW_MODAL);  
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Abre a tela de aprovação de consulta quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onAprovarConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultaAprovacao.fxml"));
            Parent parent = loader.load();

            ConsultaAprovacaoController controller = loader.getController();
            controller.setConsultaService(consultaService); 

            controller.setClienteId(clienteService.getLoggedClienteId()); 

            Stage stage = new Stage();
            stage.setTitle("Aprovação de Consulta");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); 
            stage.initModality(Modality.WINDOW_MODAL);  
            stage.showAndWait(); 

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Gera e salva um relatório com todos os dados necessários.
     */
    @FXML
    private void ongerarRelatorio() {
        RelatorioCTodosDadosDS relatorioCTodosDadosDS = new RelatorioCTodosDadosDS();

        relatorioCTodosDadosDS.salvarRelatorio();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Relatório Gerado");
        alert.setHeaderText(null);
        alert.setContentText("O relatório foi gerado e salvo com sucesso!");
        alert.showAndWait();
    }

    /**
     * Abre a tela de controle de estoque quando o botão é pressionado.
     * 
     * @param event O evento de clique no botão.
     */
    @FXML
    public void onControleEstoque(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Estoque.fxml"));
            Parent estoqueView = loader.load();

            Scene estoqueScene = new Scene(estoqueView);
            Stage stage = new Stage();
            stage.setTitle("Controle de Estoque");
            stage.setScene(estoqueScene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Utils.currentStage(event));
            stage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("Erro", "Erro ao carregar a tela de controle de estoque", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Encerra a aplicação e fecha a tela principal.
     */    @FXML
    public void onSairAction(ActionEvent event) {
        try {
            // Carregar o FXML da tela principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            Parent mainViewParent = loader.load();

            // Obter a cena principal da aplicação
            Scene mainScene = Main.getMainScene();

            // Substituir o conteúdo da cena principal com o novo FXML (MainView)
            mainScene.setRoot(mainViewParent);

            // Atualizar a cena e exibir a janela
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene); 
            stage.show();  

            // Ajustar o tamanho da janela após a troca de cenas
            stage.sizeToScene();  // Ajusta a janela automaticamente ao conteúdo
            stage.centerOnScreen();  // Centraliza a janela na tela

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar a tela principal", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para definir uma mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}

