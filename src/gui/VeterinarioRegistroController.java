package gui;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.services.VeterinarioService;

public class VeterinarioRegistroController {

    private VeterinarioService service;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btRegistrar;

    @FXML
    private Button btCancelar;

    public void setVeterinarioService(VeterinarioService service) {
        this.service = service;
    }

    @FXML
    public void onBtRegistrarAction(ActionEvent event) {
        if (service == null) {
            Alerts.showAlert("Erro", "Serviço não inicializado", "O serviço de veterinário está nulo.", AlertType.ERROR);
            return;
        }

        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        String senha = txtSenha.getText();

        if (service.registrarVeterinario(nome, email, telefone, senha)) {
            Alerts.showAlert("Sucesso", null, "Veterinário registrado com sucesso!", AlertType.INFORMATION);
            limparCampos();
        } else {
            Alerts.showAlert("Erro", "Erro ao registrar", "Não foi possível registrar o veterinário.", AlertType.ERROR);
        }
    }

    @FXML
    public void onBtCancelarAction(ActionEvent event) {
        // Fechar a janela
    }

    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtTelefone.clear();
        txtSenha.clear();
    }
}
