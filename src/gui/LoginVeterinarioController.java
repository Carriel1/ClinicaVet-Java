package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.services.VeterinarioService;
import gui.util.Alerts;

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
    private void onBtLoginAction(ActionEvent event) {
        String nome = txtNome.getText();
        String senha = txtSenha.getText();

        if (veterinarioService == null) {
            Alerts.showAlert("Erro no Login", null, "Service não foi configurado.", AlertType.ERROR);
            return;
        }

        // Verifica as credenciais do veterinário usando o método authenticate
        boolean autenticado = veterinarioService.authenticate(nome, senha);
        if (autenticado) {
            // Se o login for bem-sucedido, carrega a tela principal do veterinário
            loadTelaPrincipalVeterinario(event);
        } else {
            // Caso contrário, exibe um alerta de erro
            Alerts.showAlert("Login", null, "Nome ou senha inválidos!", AlertType.ERROR);
        }
    }

    private void loadTelaPrincipalVeterinario(ActionEvent event) {
        try {
            // Carregar o FXML da tela principal do veterinário
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaPrincipalVeterinario.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Obter o controlador da tela principal do veterinário
            TelaPrincipalVeterinarioController controller = loader.getController();
            controller.setWelcomeMessage("Bem-vindo, Veterinário!");

            // Obter a janela atual e mudar a cena
            Stage stage = (Stage) txtNome.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alerts.showAlert("Erro", null, "Não foi possível carregar a tela principal do veterinário.", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtCancelarAction() {
        // Limpar campos ou fechar a tela
        txtNome.clear();
        txtSenha.clear();
    }
}
