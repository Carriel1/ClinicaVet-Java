package gui;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.impl.AnimalDaoJDBC;
import model.entities.Animal;
import application.Main;
import db.DB;
import gui.util.Alerts;

/**
 * Controlador da tela principal do cliente. Esta classe gerencia a exibição
 * das informações do cliente, incluindo a lista de animais registrados e 
 * a interação com outras funcionalidades, como requisitar consultas, 
 * visualizar relatórios e registrar novos animais.
 */
public class TelaPrincipalClienteController {

    @FXML
    private Label lblWelcomeMessage;  // Mensagem de boas-vindas ao cliente

    @FXML
    private Button btnRequisitarConsulta;  // Botão para requisitar uma consulta

    @FXML
    private Button btnVerRelatorios;  // Botão para visualizar relatórios de animais

    @FXML
    private Button btnVerAnimaisRegistrados;  // Botão para visualizar os animais registrados

    @FXML
    private Button btnRegistrarAnimal;  // Botão para registrar um novo animal

    @FXML
    private Button btnSair;  // Botão para sair da aplicação

    @FXML
    private TableView<Animal> tableAnimais;  // Tabela para exibir os animais registrados

    @FXML
    private TableColumn<Animal, String> colNome;  // Coluna para o nome do animal

    @FXML
    private TableColumn<Animal, Integer> colIdade;  // Coluna para a idade do animal

    @FXML
    private TableColumn<Animal, String> colRaca;  // Coluna para a raça do animal

    @FXML
    private TableColumn<Animal, String> colEspecie;  // Coluna para a espécie do animal

    private Integer clienteId;  // ID do cliente logado

    /**
     * Define o ID do cliente logado.
     * 
     * @param clienteId O ID do cliente.
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Abre a tela para requisitar uma consulta.
     * A tela é carregada em uma janela modal.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        try {
            // Carregar a tela de requisitar consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/RequisitarConsulta.fxml"));
            Parent parent = loader.load();

            // Obter o controlador da tela de requisitar consulta
            RequisitarConsultaController controller = loader.getController();
            controller.setClienteId(clienteId);  // Passar o ID do cliente para o controlador

            // Exibir a tela como uma janela modal
            Stage stage = new Stage();
            stage.setTitle("Requisitar Consulta");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());
            stage.showAndWait();  // Espera a janela ser fechada

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela de requisitar consulta: " + e.getMessage());
        }
    }

    /**
     * Método de placeholder para a funcionalidade de visualização de relatórios.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onVerRelatorios(ActionEvent event) {
        System.out.println("Cliente visualizou relatórios dos animais.");
    }

    /**
     * Exibe os animais registrados pelo cliente na tabela.
     * Carrega os dados do banco e preenche a tabela com as informações.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onVerAnimaisRegistrados(ActionEvent event) {
        if (clienteId == null) {
            System.out.println("Erro: Cliente não identificado.");
            return;
        }

        try {
            // Buscar animais vinculados ao cliente
            AnimalDaoJDBC animalDao = new AnimalDaoJDBC(DB.getConnection());
            List<Animal> animais = animalDao.findAnimaisByClienteId(clienteId);

            // Preencher a tabela
            ObservableList<Animal> animaisObservableList = FXCollections.observableArrayList(animais);
            tableAnimais.setItems(animaisObservableList);

            // Definir comportamento das colunas
            colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
            colIdade.setCellValueFactory(cellData -> cellData.getValue().idadeProperty().asObject());
            colRaca.setCellValueFactory(cellData -> cellData.getValue().racaProperty());
            colEspecie.setCellValueFactory(cellData -> cellData.getValue().especieProperty());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar os animais: " + e.getMessage());
        }
    }

    /**
     * Abre a tela para registrar um novo animal para o cliente.
     * A tela é carregada em uma janela modal e, após o registro, a tabela de animais
     * é atualizada.
     * 
     * @param event O evento gerado pelo clique no botão.
     */
    @FXML
    public void onRegistrarAnimal(ActionEvent event) {
        try {
            if (clienteId == null) {
                System.out.println("Erro: Cliente não identificado. Defina clienteId antes de abrir a tela.");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AnimalRegistro.fxml"));
            Parent parent = loader.load();

            // Obter o controlador da tela de registro de animal
            AnimalController controller = loader.getController();
            controller.setClienteId(clienteId); 

            // Abrir a tela de registro de animal em uma janela modal
            Stage stage = new Stage();
            stage.setTitle("Registrar Animal");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());
            stage.showAndWait(); 
            
            // Após o registro, você pode atualizar a tabela de animais
            onVerAnimaisRegistrados(event);  

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela de registro de animais: " + e.getMessage());
        }
    }

    /**
     * Realiza o logout do cliente e retorna para a tela principal da aplicação.
     * 
     * @param event O evento gerado pelo clique no botão de sair.
     */
    @FXML
    public void onSairAction(ActionEvent event) {
        try {
            // Carregar o FXML da tela principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            Parent mainViewParent = loader.load();

            // Obter a cena principal da aplicação
            Scene mainScene = Main.getMainScene();

            // Substituir o conteúdo da cena principal com o novo FXML (MainView)
            mainScene.setRoot(mainViewParent);

            // Atualizar a cena e exibir a janela
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);  
            stage.show();  

            stage.sizeToScene();  // Ajusta a janela automaticamente ao conteúdo
            stage.centerOnScreen();  

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar a tela principal", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Define a mensagem de boas-vindas exibida na tela principal.
     * 
     * @param message A mensagem de boas-vindas.
     */
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}
