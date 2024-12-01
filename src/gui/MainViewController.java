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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load(); // Parent é genérico e funciona para qualquer root

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); // Supondo que o root principal seja um ScrollPane

            // Substituir o conteúdo do ScrollPane pela nova view carregada
            mainScrollPane.setContent(newView);

            // Inicializar o controlador
            T controller = loader.getController();
            initializingAction.accept(controller);

            // Ajustar o tamanho da janela automaticamente para o conteúdo
            Stage stage = (Stage) mainScene.getWindow();
            stage.sizeToScene();  // Ajusta a janela ao conteúdo
            stage.centerOnScreen();  // Centraliza a janela na tela

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }




    // Carregar tela de Funcionários
    @FXML
    public void onMenuItemFuncionarioAction() {
        loadView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) -> {
            controller.setFuncionarioService(new FuncionarioService());
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
        loadView("/gui/LoginCliente.fxml", (LoginClienteController controller) -> {
            controller.setClienteService(new ClienteService());
        });
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
