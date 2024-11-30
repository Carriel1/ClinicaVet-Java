package gui;

import java.io.IOException;
import java.util.List;

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

    @FXML
    public void initialize() {
        // Inicializando os serviços com suas dependências corretas
        if (clienteService == null) {
            clienteService = new ClienteService();  
        }
        if (veterinarioService == null) {
            veterinarioService = new VeterinarioService();
        }
        if (consultaService == null) {
            consultaService = new ConsultaService();
        }
        
        List<Cliente> clientes = clienteService.findAll(); // Isso agora deve funcionar
    }



    // Método chamado para cadastrar um novo veterinário
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

    // Método chamado para cadastrar um novo cliente
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

    // Método chamado para aceitar ou marcar uma consulta
    @FXML
    public void onMarcarConsulta(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de registro de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultaRegistro.fxml"));
            Parent parent = loader.load();

            // Configura o controlador da nova tela
            ConsultaRegistroController controller = loader.getController();
            controller.setConsultaService(consultaService); // Passando o serviço de consulta
            controller.setClienteService(clienteService);    // Passando o serviço de cliente
            controller.setVeterinarioService(veterinarioService); // Passando o serviço de veterinário

            Stage stage = new Stage();
            stage.setTitle("Registrar Consulta");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); // Configura o stage pai
            stage.initModality(Modality.WINDOW_MODAL);  // Define a modalidade
            stage.showAndWait(); // Espera a tela ser fechada antes de continuar

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onCancelarConsulta(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de cancelamento de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CancelarConsulta.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de cancelamento de consulta
            CancelarConsultaController cancelarConsultaController = loader.getController();
            
            // Passa os serviços necessários para o controlador de CancelarConsulta
            cancelarConsultaController.setServices(consultaService); // Passa o ConsultaService

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de cancelamento de consulta.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onModificarConsulta(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de modificação de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModificarConsulta.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de modificação de consulta
            ModificarConsultaController modificarConsultaController = loader.getController();
            
            // Passa os serviços necessários para o controlador de ModificarConsulta
            modificarConsultaController.setServices(consultaService); // Passa o ConsultaService
            modificarConsultaController.setClienteService(clienteService); // Passa o ClienteService
            modificarConsultaController.setVeterinarioService(veterinarioService); // Passa o VeterinarioService

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de modificação de consulta.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onExcluirVeterinario(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de exclusão de veterinário
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ExcluirVeterinario.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de exclusão de veterinário
            ExcluirVeterinarioController excluirVeterinarioController = loader.getController();
            
            // Passa o serviço necessário para o controlador de exclusão
            excluirVeterinarioController.setVeterinarioService(veterinarioService);

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Excluir Veterinário");
            stage.initOwner(Utils.currentStage(event)); // Configura o stage pai
            stage.initModality(Modality.WINDOW_MODAL);  // Define a modalidade
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onAprovarConsulta(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de aprovação de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultaAprovacao.fxml"));
            Parent parent = loader.load();

            // Configura o controlador da nova tela
            ConsultaAprovacaoController controller = loader.getController();
            controller.setConsultaService(consultaService); // Passando o serviço de consulta

            // Passando o clienteId para o novo controlador
            controller.setClienteId(clienteService.getLoggedClienteId());  // Assumindo que você tenha um método para obter o ID do cliente logado

            // Criação e exibição da nova janela de aprovação
            Stage stage = new Stage();
            stage.setTitle("Aprovação de Consulta");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); // Configura o stage pai
            stage.initModality(Modality.WINDOW_MODAL);  // Define a modalidade
            stage.showAndWait(); // Espera a tela ser fechada antes de continuar

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void ongerarRelatorio() {
        // Instanciando a classe RelatorioCTodosDadosDS
        RelatorioCTodosDadosDS relatorioCTodosDadosDS = new RelatorioCTodosDadosDS();

        // Chamando o método para salvar o relatório
        relatorioCTodosDadosDS.salvarRelatorio();

        // Exibindo um alerta de confirmação
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Relatório Gerado");
        alert.setHeaderText(null);
        alert.setContentText("O relatório foi gerado e salvo com sucesso!");
        alert.showAndWait();
    }


    // Método chamado para o controle de estoque
    @FXML
    public void onControleEstoque(ActionEvent event) {
        System.out.println("Controle de Estoque.");
    }

    // Método chamado para sair da tela principal
    @FXML
    public void onSairAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    // Método para definir uma mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}

