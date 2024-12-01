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

/**
 * Controlador da tela principal da aplicação.
 * Responsável pela navegação entre diferentes telas (views) do sistema,
 * como a tela de funcionários, clientes, login e tela sobre.
 */
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

    /**
     * Método de inicialização da tela principal. Configura as margens dos menus.
     * Este método é chamado automaticamente quando a tela é carregada.
     * 
     * @param uri O local da URL do arquivo FXML.
     * @param rb O objeto que contém os recursos.
     */
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        if (registrationMenu != null && loginMenu != null && helpMenu != null) {
            VBox.setMargin(registrationMenu, new Insets(20, 0, 10, 0));
            VBox.setMargin(loginMenu, new Insets(10, 0, 10, 0));
            VBox.setMargin(helpMenu, new Insets(10, 0, 20, 0));
        }
    }

    /**
     * Método genérico para carregar uma nova tela (view) no painel principal.
     * Substitui o conteúdo do ScrollPane com a nova tela e inicializa seu controlador.
     * Ajusta o tamanho da janela automaticamente para o conteúdo.
     * 
     * @param fxmlPath O caminho para o arquivo FXML da tela.
     * @param initializingAction A ação de inicialização que será aplicada ao controlador da nova tela.
     * @param <T> Tipo do controlador da nova tela.
     */
    private synchronized <T> void loadView(String fxmlPath, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load(); 

            Scene mainScene = Main.getMainScene();
            ScrollPane mainScrollPane = (ScrollPane) mainScene.getRoot(); 

            mainScrollPane.setContent(newView);

            // Inicializar o controlador
            T controller = loader.getController();
            initializingAction.accept(controller);

            // Ajustar o tamanho da janela automaticamente para o conteúdo
            Stage stage = (Stage) mainScene.getWindow();
            stage.sizeToScene();  // Ajusta a janela ao conteúdo
            stage.centerOnScreen();  

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Carrega a tela de Funcionários ao clicar no item de menu "Funcionários".
     */
    @FXML
    public void onMenuItemFuncionarioAction() {
        loadView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) -> {
            controller.setFuncionarioService(new FuncionarioService());
        });
    }

    /**
     * Carrega a tela "Sobre" ao clicar no item de menu "Sobre".
     */
    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml", x -> {});
    }

    /**
     * Carrega a tela de Clientes ao clicar no item de menu "Clientes".
     */
    @FXML
    public void onMenuItemClienteAction() {
        loadView("/gui/ClienteList.fxml", (ClienteListController controller) -> {
            controller.setClienteService(new ClienteService());
            controller.updateTableView();
        });
    }

    /**
     * Carrega a tela de Login do Cliente ao clicar no item de menu "Login Cliente".
     */
    @FXML
    public void onMenuItemLoginClienteAction() {
        loadView("/gui/LoginCliente.fxml", (LoginClienteController controller) -> {
            controller.setClienteService(new ClienteService());
        });
    }

    /**
     * Carrega a tela de Login do Funcionário ao clicar no item de menu "Login Funcionário".
     */
    @FXML
    public void onMenuItemLoginFuncionarioAction() {
        loadView("/gui/LoginFuncionario.fxml", (LoginFuncionarioController controller) -> {
            controller.setFuncionarioService(new FuncionarioService());
        });
    }

    /**
     * Carrega a tela de Login do Veterinário ao clicar no item de menu "Login Veterinário".
     */
    @FXML
    public void onMenuItemLoginVeterinarioAction() {
        loadView("/gui/LoginVeterinario.fxml", (LoginVeterinarioController controller) -> {
            controller.setVeterinarioService(new VeterinarioService());
        });
    }
}
