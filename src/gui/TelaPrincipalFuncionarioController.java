package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
        System.out.println("Cadastro de novo Veterinário.");
        // Lógica para abrir a tela de cadastro de veterinário
    }

    // Método chamado para cadastrar um novo cliente
    @FXML
    public void onCadastrarCliente(ActionEvent event) {
        System.out.println("Cadastro de novo Cliente.");
        // Lógica para abrir a tela de cadastro de cliente
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
        stage.close();
    }

    // Método para definir uma mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}
