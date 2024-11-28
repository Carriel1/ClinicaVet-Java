package gui;

import java.io.IOException;
import java.util.List;

import db.DB;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.impl.ConsultaDaoJDBC;
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
            consultaService = new ConsultaService(new ConsultaDaoJDBC(DB.getConnection()));
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

