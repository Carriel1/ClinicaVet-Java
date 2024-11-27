package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TelaPrincipalVeterinarioController {

    @FXML
    private Label lblWelcomeMessage;

    @FXML
    private Button btnRealizarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnCadastrarAnimal;

    @FXML
    private Button btnSair;

    @FXML
    public void onRealizarConsulta(ActionEvent event) {
        System.out.println("Veterinário requisitou consulta.");
    }

    @FXML
    public void onVerRelatorios(ActionEvent event) {
        System.out.println("Veterinário visualizou relatórios.");
    }

    @FXML
    public void onCadastrarAnimal(ActionEvent event) {
        System.out.println("Veterinário cadastrou um animal.");
    }

    @FXML
    public void onSairAction(ActionEvent event) {
        System.out.println("Veterinário saiu.");
    }

    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}
