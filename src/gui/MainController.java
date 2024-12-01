package gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controlador principal da aplicação JavaFX.
 * Responsável pela manipulação das telas da aplicação, carregando e trocando entre elas
 * conforme a navegação do usuário.
 */
public class MainController {
    /**
     * O painel (pane) principal onde as telas serão carregadas.
     */
    private Pane rootPane;

    /**
     * Constrói o controlador principal da aplicação, configurando a cena principal.
     * 
     * @param stage O estágio (janela) principal da aplicação.
     * @param rootPane O painel onde as telas serão carregadas.
     */
    public MainController(Stage stage, Pane rootPane) {
        this.rootPane = rootPane;
        stage.setScene(new Scene(rootPane));  
        stage.setTitle("Aplicação JavaFX"); 
    }

    /**
     * Define a tela a ser exibida utilizando o caminho do arquivo FXML.
     * Substitui o conteúdo atual do painel principal com a nova tela carregada.
     * 
     * @param fxmlPath O caminho para o arquivo FXML da tela a ser carregada.
     */
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

    /**
     * Define a tela a ser exibida passando diretamente o objeto `Parent` que representa a tela.
     * Substitui o conteúdo atual do painel principal com a nova tela fornecida.
     * 
     * @param screen O objeto `Parent` que representa a tela a ser exibida.
     */
    public void setScreen(Parent screen) {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(screen);
    }

    /**
     * Exibe a tela de login carregando o arquivo FXML correspondente.
     */
    public void showLoginView() {
        setScreen("/gui/LoginView.fxml");
    }
}
