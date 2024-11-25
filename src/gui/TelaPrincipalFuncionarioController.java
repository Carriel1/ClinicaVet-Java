package gui;

import java.io.IOException;

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
import model.entities.Cliente;
import model.entities.Veterinario;
import model.services.ClienteService;
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

    // Método chamado para cadastrar um novo veterinário
    @FXML
    public void onCadastrarVeterinario(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de cadastro de veterinário
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VeterinarioRegistro.fxml"));
            Parent parent = loader.load();

            // Configura o controlador da nova tela
            VeterinarioRegistroController controller = loader.getController();
            controller.setVeterinarioService(new VeterinarioService());  // Passando o serviço para o controlador
            controller.setVeterinario(new Veterinario());  // Inicializando a entidade no controlador

            // Exibe a tela em uma nova janela
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Veterinário");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); // Configura o stage pai
            stage.initModality(Modality.WINDOW_MODAL);  // Define a modalidade
            stage.showAndWait(); // Espera a tela ser fechada antes de continuar

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    // Método chamado para cadastrar um novo cliente
    @FXML
    public void onCadastrarCliente(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de cadastro de cliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ClienteRegistro.fxml"));
            Parent parent = loader.load();

            // Configura o controlador da nova tela
            ClienteRegistroController controller = loader.getController();
            controller.setClienteService(new ClienteService()); // Passando o serviço para o controlador
            controller.setCliente(new Cliente()); // Inicializando a entidade no controlador

            // Exibe a tela em uma nova janela
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cliente");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); // Configura o stage pai
            stage.initModality(Modality.WINDOW_MODAL); // Define a modalidade
            stage.showAndWait(); // Espera a tela ser fechada antes de continuar

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

   
    
    // Método chamado para aceitar ou marcar uma consulta
    @FXML
    public void onMarcarConsulta(ActionEvent event) {
        System.out.println("Aceitar ou Marcar uma Consulta.");
        // Lógica para abrir a tela de agendamento de consultas
    }

    // Método chamado para o controle de estoque
    @FXML
    public void onControleEstoque(ActionEvent event) {
        System.out.println("Controle de Estoque.");
        // Lógica para abrir a tela de controle de estoque
    }

    // Método chamado para sair da tela principal
    @FXML
    public void onSairAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();  // Fecha a janela
    }

    // Método para definir uma mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);  // Atualiza o texto da mensagem
        }
    }
}

