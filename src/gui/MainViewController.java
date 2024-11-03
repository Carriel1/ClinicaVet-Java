package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.FuncionarioService;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private MenuBar registrationMenu;

    @FXML
    private MenuBar loginMenu;

    @FXML
    private MenuBar helpMenu;

    @FXML
    private MenuItem menuItemCliente;

    @FXML
    private MenuItem menuItemLoginFuncionario;

    // Método para ação do menu de Funcionários
    @FXML
    public void onMenuItemFuncionarioAction() {
        loadView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) -> {
            controller.setFuncionarioService(new FuncionarioService());
            controller.updateTableView();
        });
    }

    // Método para ação do menu Sobre
    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml", x -> {});
    }

    // Método para ação do menu Cliente
    @FXML
    public void onMenuItemClienteAction(ActionEvent event) {
        System.out.println("Menu Cliente clicado!");
    }

    // Método para abrir a tela de login do cliente
    @FXML
    public void onMenuItemLoginClienteAction() {
        loadView("/gui/LoginCliente.fxml", x -> {}); // Certifique-se de que o caminho está correto
    }

    // Método para abrir a tela de login do funcionário
    @FXML
    public void onMenuItemLoginFuncionarioAction() {
        loadView("/gui/LoginFuncionario.fxml", x -> {}); // Certifique-se de que o caminho está correto
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // Configurações iniciais dos menus
        if (registrationMenu != null && loginMenu != null && helpMenu != null) {
            VBox.setMargin(registrationMenu, new Insets(20, 0, 10, 0));
            VBox.setMargin(loginMenu, new Insets(10, 0, 10, 0));
            VBox.setMargin(helpMenu, new Insets(10, 0, 20, 0));
        }
    }

    // Método para carregar a LoginView
    public void loadLoginView() {
        String fxmlPath = "/gui/view/LoginView.fxml"; // Altere se necessário
        System.out.println("Tentando carregar o FXML: " + fxmlPath);
        loadView(fxmlPath, controller -> {
            // Inicialize o controlador se necessário
        });
    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
        }
    }
}
