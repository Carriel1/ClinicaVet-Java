package application;

import java.io.IOException;

import gui.MainViewController; // Importando o controlador
import gui.util.Alerts; // Certifique-se de que Alerts é uma classe existente no seu pacote
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega a MainView.fxml e associa o FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();

            // Configura o ScrollPane para se ajustar corretamente
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            // Cria a cena principal e a define no estágio
            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Clinica Vet");

            // Acesso ao controlador da MainView
            MainViewController controller = loader.getController();
            if (controller != null) {
                // Chamando o método para carregar a tela de login do funcionário por padrão
                controller.onMenuItemLoginFuncionarioAction(); // Altere se desejar usar o cliente como padrão
            } else {
                System.err.println("Erro: Controlador MainViewController não pôde ser obtido.");
            }

            // Exibe a janela principal
            primaryStage.show();
        } catch (IOException e) {
            // Tratamento de exceção para falhas de IO
            Alerts.showAlert("IO Exception", "Erro ao carregar a interface", e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Tratamento de exceção genérica para outras falhas
            Alerts.showAlert("Unexpected Exception", "Erro inesperado", e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

    // Método para obter a cena principal
    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}

