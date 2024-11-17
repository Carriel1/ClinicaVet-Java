package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TelaPrincipalClienteController {

    // Declarando os elementos do FXML para usá-los diretamente no controlador
    @FXML
    private Label lblWelcomeMessage;

    @FXML
    private Button btnRequisitarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnVerAnimaisRegistrados;

    @FXML
    private Button btnSair;

    // Método chamado quando o cliente requisita uma consulta
    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        System.out.println("Cliente requisitou consulta.");
        // Lógica adicional pode ser implementada aqui para navegar para uma tela de requisição de consulta
    }

    // Método chamado quando o cliente decide ver os relatórios
    @FXML
    public void onVerRelatorios(ActionEvent event) {
        System.out.println("Cliente visualizou relatórios dos animais.");
        // Lógica para abrir uma tela de relatórios ou gerar relatórios
    }

    // Método chamado quando o cliente decide ver os animais registrados
    @FXML
    public void onVerAnimaisRegistrados(ActionEvent event) {
        System.out.println("Cliente visualizou os animais registrados.");
        // Lógica para abrir uma tela com a lista de animais registrados
    }

    // Método chamado para sair da tela principal do cliente
    @FXML
    public void onSairAction(ActionEvent event) {
        // Fecha a janela da tela principal do cliente
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    // Esse método pode ser usado para atualizar a mensagem de boas-vindas, se necessário
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}
