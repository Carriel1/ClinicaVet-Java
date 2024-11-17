package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ClienteService;
import model.services.FuncionarioService;
import model.services.VeterinarioService;

public class MainViewController implements Initializable {

    // Menu items
    @FXML
    private MenuItem menuItemAbout;
    @FXML
    private MenuItem menuItemCliente;
    @FXML
    private MenuItem menuItemLoginFuncionario;
    @FXML
    private MenuItem menuItemLoginVeterinario;
    
    @FXML
    private MenuBar registrationMenu;
    @FXML
    private MenuBar loginMenu;
    @FXML
    private MenuBar helpMenu;

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // Configurações iniciais dos menus
        if (registrationMenu != null && loginMenu != null && helpMenu != null) {
            VBox.setMargin(registrationMenu, new Insets(20, 0, 10, 0));
            VBox.setMargin(loginMenu, new Insets(10, 0, 10, 0));
            VBox.setMargin(helpMenu, new Insets(10, 0, 20, 0));
        }
    }

    // Método para carregar diferentes views e injetar serviços
    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            // Mantém o menu fixo e troca apenas o conteúdo
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            // Inicializa o controller da nova view
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
        }
    }

    // Carregar tela de Funcionários
    @FXML
    public void onMenuItemFuncionarioAction() {
        loadView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) -> {
            controller.setFuncionarioService(new FuncionarioService());
            controller.updateTableView();
        });
    }

    // Carregar tela Sobre
    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml", x -> {});
    }

    // Carregar tela de Clientes
    @FXML
    public void onMenuItemClienteAction() {
        loadView("/gui/ClienteList.fxml", (ClienteListController controller) -> {
            controller.setClienteService(new ClienteService());
            controller.updateTableView();
        });
    }

    // Carregar tela de Login do Cliente
    @FXML
    public void onMenuItemLoginClienteAction() {
        loadView("/gui/LoginCliente.fxml", x -> {});
    }

    // Carregar tela de Login do Funcionário
    @FXML
    public void onMenuItemLoginFuncionarioAction() {
        loadView("/gui/LoginFuncionario.fxml", (LoginFuncionarioController controller) -> {
            controller.setFuncionarioService(new FuncionarioService());
        });
    }

    // Carregar tela de Login do Veterinário
    @FXML
    public void onMenuItemLoginVeterinarioAction() {
        loadView("/gui/LoginVeterinario.fxml", (LoginVeterinarioController controller) -> {
            controller.setVeterinarioService(new VeterinarioService());
        });
    }
}
