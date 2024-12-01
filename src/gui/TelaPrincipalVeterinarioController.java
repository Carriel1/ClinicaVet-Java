package gui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import db.DB;
import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.entities.Relatorio;
import model.entities.Veterinario;
import model.services.AnimalService;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

/**
 * Controlador responsável por gerenciar as ações e a lógica da tela principal do veterinário.
 * A tela oferece funcionalidades como cadastro de animais, realização de consultas e visualização de relatórios.
 */
public class TelaPrincipalVeterinarioController {

    @FXML
    private Button btnCadastrarAnimal;

    @FXML
    private Button btnRealizarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Label lblWelcomeMessage; 

    private AnimalService animalService;
    private ClienteService clienteService;
    private ConsultaService consultaService;
    private VeterinarioService veterinarioService;
    
    /**
     * Construtor da classe. Inicializa os serviços necessários para a operação do controlador.
     * 
     * @param clienteService Serviço relacionado aos clientes.
     * @param animalService Serviço relacionado aos animais.
     * @param consultaService Serviço relacionado às consultas.
     * @param veterinarioService Serviço relacionado aos veterinários.
     */
    public TelaPrincipalVeterinarioController(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService, VeterinarioService veterinarioService) {
        this.clienteService = clienteService;
        this.consultaService = consultaService;
        this.animalService = animalService;
        this.veterinarioService = veterinarioService;
    }
    
    /**
     * Construtor vazio, necessário para o carregamento via FXML.
     */
    public TelaPrincipalVeterinarioController () {
    	
    }
    
    /**
     * Método para injeção de dependência dos serviços.
     * 
     * @param animalService Serviço de gerenciamento de animais.
     * @param clienteService Serviço de gerenciamento de clientes.
     * @param consultaService Serviço de gerenciamento de consultas.
     * @param veterinarioService Serviço de gerenciamento de veterinários.
     */
    public void setServices(AnimalService animalService, ClienteService clienteService, ConsultaService consultaService, VeterinarioService veterinarioService) {
        if (animalService == null || clienteService == null || consultaService == null || veterinarioService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.animalService = animalService;
        this.clienteService = clienteService;
        this.consultaService = consultaService; 
        this.veterinarioService = veterinarioService;
    }

    /**
     * Método acionado quando o botão "Realizar Consulta" é pressionado.
     * Carrega a tela de consultas pendentes.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onRealizarConsulta(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de consultas pendentes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultasPendentes.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de consultas pendentes
            ConsultasPendentesController consultasController = loader.getController();
                        
            // Passa os serviços para o controlador de ConsultasPendentes
            consultasController.setServices(clienteService, animalService, consultaService);

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de consultas pendentes.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método acionado quando o botão "Ver Relatórios" é pressionado.
     * Carrega a tela de relatórios.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onVerRelatorios(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de relatórios
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VerRelatorios.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de relatórios
            VerRelatoriosController controller = loader.getController();
            
            // Exibe a nova tela de relatórios
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de relatórios.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método acionado quando o botão "Cadastrar Animal" é pressionado.
     * Carrega a tela de cadastro de animal.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onCadastrarAnimal(ActionEvent event) {
        try {
            if (animalService == null || clienteService == null) {
                throw new IllegalStateException("Os serviços não foram configurados.");
            }

            // Carrega o FXML para a tela de cadastro de animal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CadastrarAnimalVeterinario.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de cadastro de animal
            CadastrarAnimalVeterinarioController controller = loader.getController();

            // Passa os serviços para o controlador de CadastrarAnimalVeterinario
            controller.setServices(animalService, clienteService);

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de cadastro de animal.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para a ação de sair da tela principal do veterinário e retornar à tela principal do sistema.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onSair(ActionEvent event) {
        try {
            // Carregar o FXML da tela principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            Parent mainViewParent = loader.load();

            Scene mainScene = Main.getMainScene();

            mainScene.setRoot(mainViewParent);

            // Atualizar a cena e exibir a janela
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);  
            stage.show();  

            stage.sizeToScene();  
            stage.centerOnScreen();  

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar a tela principal", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Define a mensagem de boas-vindas exibida na tela principal do veterinário.
     * 
     * @param message A mensagem de boas-vindas a ser exibida.
     */
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);  // Atualiza o texto do label
        } else {
            System.out.println("O Label de boas-vindas não foi inicializado.");
        }
    }
}
