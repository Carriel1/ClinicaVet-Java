package gui;

import java.io.IOException;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

/**
 * Controlador responsável pela tela de Primeiros Socorros.
 * Permite que o usuário visualize a tela de primeiros socorros e volte para a tela principal.
 */
public class PrimeirosSocorrosController {

    @FXML
    private Button voltarButton;  // Botão para voltar à tela principal

    /**
     * Método que é chamado quando o usuário clica no botão de voltar.
     * Carrega a tela principal novamente.
     */
    @FXML
    private void handleBackButtonAction() {
        loadView("/gui/MainView.fxml", controller -> {});
    }
    
    /**
     * Carrega uma nova view FXML e substitui o conteúdo atual do ScrollPane.
     * 
     * @param fxmlPath O caminho para o arquivo FXML da nova view.
     * @param initializingAction Ação a ser realizada no controlador da nova view.
     * @param <T> Tipo genérico para o controlador.
     */
    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load(); 

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); 

            // Substitui o conteúdo do ScrollPane pela nova view carregada
            mainScrollPane.setContent(newView);

            // Inicializa o controlador da nova view
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
