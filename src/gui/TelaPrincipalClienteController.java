package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPrincipalClienteController {

    @FXML
    private Label lblWelcomeMessage;

    @FXML
    private Button btnRequisitarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnVerAnimaisRegistrados;

    @FXML
    private Button btnRegistrarAnimal;

    @FXML
    private Button btnSair;

    private Integer clienteId; // ID do cliente logado

    public void setClienteId(Integer clienteId) {
        System.out.println("Setando clienteId: " + clienteId);
        this.clienteId = clienteId;
    }

    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        System.out.println("Cliente requisitou consulta.");
        // Lógica adicional pode ser implementada aqui
    }

    @FXML
    public void onVerRelatorios(ActionEvent event) {
        System.out.println("Cliente visualizou relatórios dos animais.");
        // Lógica para abrir uma tela de relatórios
    }

    @FXML
    public void onVerAnimaisRegistrados(ActionEvent event) {
        System.out.println("Cliente visualizou os animais registrados.");
        // Lógica para abrir uma tela com a lista de animais registrados
    }

    @FXML
    public void onRegistrarAnimal(ActionEvent event) {
        try {
            if (clienteId == null) {
                System.out.println("Erro: Cliente não identificado. Defina clienteId antes de abrir a tela.");
                return;
            }

            // Carrega o FXML da tela de registro de animal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AnimalRegistro.fxml"));
            Parent parent = loader.load();

            // Obtém o controlador e passa o ID do cliente
            AnimalController controller = loader.getController();
            controller.setClienteId(clienteId);

            // Configura a nova janela
            Stage stage = new Stage();
            stage.setTitle("Registrar Animal");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL); // Modal para bloquear a janela principal
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela de registro de animais: " + e.getMessage());
        }
    }

    @FXML
    public void onSairAction(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}
