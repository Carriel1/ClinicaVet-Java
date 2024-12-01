package application;

import java.io.IOException;

import gui.util.Alerts; 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * Classe principal do aplicativo ClinicaVet.
 * Esta classe é responsável por iniciar a aplicação JavaFX,
 * carregar a interface principal e gerenciar a cena principal.
 */
public class Main extends Application {
    
    /**
     * Cena principal da aplicação, utilizada para trocar e exibir diferentes telas.
     */
    private static Scene mainScene;

    /**
     * Método principal do ciclo de vida da aplicação JavaFX.
     * Inicializa a interface gráfica carregando o arquivo FXML e configura a cena principal.
     *
     * @param primaryStage O estágio principal fornecido pelo JavaFX.
     */
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

    /**
     * Obtém a cena principal da aplicação.
     *
     * @return A cena principal utilizada para gerenciar as telas.
     */
    public static Scene getMainScene() {
        return mainScene;
    }
    
    /**
     * Define a cena principal da aplicação.
     *
     * @param scene A nova cena principal a ser definida.
     */
    public static void setMainScene(Scene scene) {
        mainScene = scene;
    }

    /**
     * Método principal da aplicação. 
     * Este método inicia o ciclo de vida da aplicação JavaFX.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}


