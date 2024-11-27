package gui;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.services.AnimalService;
import model.services.ClienteService;

public class TelaPrincipalVeterinarioController {

    @FXML
    private Button btnCadastrarAnimal;

    @FXML
    private Button btnRealizarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Label lblWelcomeMessage; // Este Label é para exibir a mensagem de boas-vindas

    private AnimalService animalService;
    private ClienteService clienteService;

    // Método para injeção dos serviços
    public void setServices(AnimalService animalService, ClienteService clienteService) {
        if (animalService == null || clienteService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.animalService = animalService;
        this.clienteService = clienteService;
    }

    // Método para o evento do botão "Cadastrar Animal"
    @FXML
    public void onCadastrarAnimal(ActionEvent event) {
        try {
            if (animalService == null || clienteService == null) {
                throw new IllegalStateException("Os serviços não foram configurados.");
            }

            // Carrega o FXML para a tela de cadastro de animal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CadastrarAnimalVeterinario.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de cadastro de animal
            CadastrarAnimalVeterinarioController controller = loader.getController();

            // Passa os serviços para o controlador de CadastrarAnimalVeterinario
            controller.setServices(animalService, clienteService);

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de cadastro de animal.", Alert.AlertType.ERROR);
        }
    }

    // Método para o evento do botão "Realizar Consulta"
    @FXML
    public void onRealizarConsulta(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de realização de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/RealizarConsulta.fxml"));
            Parent root = loader.load();

            // Exibe a nova tela de consulta
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de realizar consulta.", Alert.AlertType.ERROR);
        }
    }

    // Método para o evento do botão "Ver Relatórios"
    @FXML
    public void onVerRelatorios(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de relatórios
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VerRelatorios.fxml"));
            Parent root = loader.load();

            // Exibe a nova tela de relatórios
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de relatórios.", Alert.AlertType.ERROR);
        }
    }

    // Método para o evento do botão "Sair"
    @FXML
    public void onSair(ActionEvent event) {
        try {
            // Fecha a aplicação ou redireciona para a tela de login
            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.close(); // Fecha a tela atual (pode ser alterado para redirecionar para login)
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao sair do sistema.", Alert.AlertType.ERROR);
        }
    }

    // Método para definir a mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);  // Atualiza o texto do label
        } else {
            System.out.println("O Label de boas-vindas não foi inicializado.");
        }
    }
}


