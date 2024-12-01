package gui;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import application.Main;
import db.DB;
import gui.util.Alerts;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.impl.AnimalDaoJDBC;
import model.entities.Animal;

public class TelaPrincipalClienteController {

    @FXML
    private Label lblWelcomeMessage;

    @FXML
    private Button btnRequisitarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnVerAnimaisRegistrados;

    @FXML
    private Button btnRegistrarAnimal;

    @FXML
    private Button btnSair;

    @FXML
    private TableView<Animal> tableAnimais;

    @FXML
    private TableColumn<Animal, String> colNome;

    @FXML
    private TableColumn<Animal, Integer> colIdade;

    @FXML
    private TableColumn<Animal, String> colRaca;

    @FXML
    private TableColumn<Animal, String> colEspecie;

    private Integer clienteId; // ID do cliente logado

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    @FXML
    public void onRequisitarConsulta(ActionEvent event) {
        try {
            // Carregar a tela de requisitar consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/RequisitarConsulta.fxml"));
            Parent parent = loader.load();

            // Obter o controlador da tela de requisitar consulta
            RequisitarConsultaController controller = loader.getController();
            controller.setClienteId(clienteId); // Passar o ID do cliente para o controlador

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



    @FXML
    public void onVerRelatorios(ActionEvent event) {
        System.out.println("Cliente visualizou relatórios dos animais.");
    }

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

    @FXML
    public void onRegistrarAnimal(ActionEvent event) {
        try {
            if (clienteId == null) {
                System.out.println("Erro: Cliente não identificado. Defina clienteId antes de abrir a tela.");
                return;
            }
            
            
            // Carregar o FXML da tela de registro de animal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AnimalRegistro.fxml"));
            Parent parent = loader.load();

            // Obter o controlador da tela de registro de animal
            AnimalController controller = loader.getController();
            controller.setClienteId(clienteId);  // Passar o ID do cliente para o controlador

            // Abrir a tela de registro de animal em uma janela modal
            Stage stage = new Stage();
            stage.setTitle("Registrar Animal");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());
            stage.showAndWait();  // Espera a janela ser fechada
            
            // Após o registro, você pode atualizar a tabela de animais
            onVerAnimaisRegistrados(event);  // Atualiza a lista de animais registrados

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela de registro de animais: " + e.getMessage());
        }
    }



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
            stage.setScene(mainScene);  // Atualiza a cena
            stage.show();  // Exibe a cena novamente

            // Ajustar o tamanho da janela após a troca de cenas
            stage.sizeToScene();  // Ajusta a janela automaticamente ao conteúdo
            stage.centerOnScreen();  // Centraliza a janela na tela

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar a tela principal", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}
