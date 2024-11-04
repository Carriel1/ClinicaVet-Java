package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType; // Importar AlertType diretamente
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.services.VeterinarioService;

public class LoginVeterinarioController {

    @FXML
    private TextField txtNome;

    @FXML
    private PasswordField txtSenha;

    private VeterinarioService veterinarioService;

    public void setVeterinarioService(VeterinarioService service) {
        this.veterinarioService = service;
    }

    @FXML
    private void onBtLoginAction() {
        String nome = txtNome.getText();
        String senha = txtSenha.getText();

        if (veterinarioService.authenticate(nome, senha)) {
            // Ação em caso de sucesso no login (pode redirecionar para outra tela)
            Alerts.showAlert("Login", null, "Login realizado com sucesso!", AlertType.INFORMATION);
        } else {
            Alerts.showAlert("Login", null, "Nome ou senha inválidos!", AlertType.ERROR);
        } 
    }

    @FXML
    private void onBtCancelarAction() {
        // Limpar campos ou fechar a tela
        txtNome.clear();
        txtSenha.clear();
    }
}

