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
import model.services.DepartmentService;
import model.services.FuncionarioService;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemFuncionario;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private MenuBar registrationMenu;

    @FXML
    private MenuBar loginMenu;

    @FXML
    private MenuBar helpMenu;
    
    @FXML
    private MenuItem menuItemCliente; // Corrigido para MenuItem, não MenuBar

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

    // Método para ação do menu de Departamentos
    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }

    // Método para ação do menu Sobre
    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml", x -> {});
    }
    
    @FXML
    public void onMenuItemClienteAction(ActionEvent event) {
        // Lógica para quando o menu de cliente é clicado
        System.out.println("Menu Cliente clicado!");
    }
    
    @FXML
    public void onMenuItemLoginClienteAction() {
        // Ação do menu de login do cliente
        System.out.println("Menu Login Cliente clicado!");
    }
    
    @FXML
    public void onMenuItemLoginFuncionarioAction() {
        // Implementação da ação ao clicar no menu de login do funcionário
        System.out.println("Menu Login Funcionário clicado!");
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        

        // Define margens com objeto Insets para assegurar que o valor seja reconhecido
        if (registrationMenu != null && loginMenu != null && helpMenu != null) {
            VBox.setMargin(registrationMenu, new Insets(20, 0, 10, 0)); // Margem superior e inferior
            VBox.setMargin(loginMenu, new Insets(10, 0, 10, 0));        // Margem entre os menus
            VBox.setMargin(helpMenu, new Insets(10, 0, 20, 0));         // Margem entre os menus e abaixo
        }
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


