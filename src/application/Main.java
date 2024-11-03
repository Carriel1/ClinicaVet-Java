package application;

import java.io.IOException;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Clinica Vet");
/*
            // Acesso ao controlador da MainView para mostrar a tela de login
            MainViewController controller = loader.getController();
            controller.loadLoginView(); // Agora não passa mais o caminho do FXML
*/
            primaryStage.show();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Erro ao carregar a interface", e.getMessage(), javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
