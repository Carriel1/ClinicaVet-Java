package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
    private Pane rootPane;

    public MainController(Stage stage, Pane rootPane) {
        this.rootPane = rootPane;
        stage.setScene(new Scene(rootPane));  // Define a cena com rootPane como o contêiner principal
        stage.setTitle("Aplicação JavaFX"); // Defina um título para a janela
    }

    // Método para definir a tela usando o caminho do FXML
    public void setScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();
            rootPane.getChildren().clear();
            rootPane.getChildren().add(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para definir a tela usando um Parent diretamente (caso queira passar um elemento já carregado)
    public void setScreen(Parent screen) {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(screen);
    }

    // Método para exibir a tela de login
    public void showLoginView() {
        setScreen("/gui/LoginView.fxml");
    }
}

