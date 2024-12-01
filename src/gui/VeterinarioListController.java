package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Veterinario;
import model.services.VeterinarioService;

/**
 * Controlador responsável por gerenciar a visualização, edição e remoção de veterinários na interface.
 * Ele permite a interação com uma lista de veterinários e a realização de operações CRUD (Criar, Ler, Atualizar e Deletar).
 */
public class VeterinarioListController implements Initializable, DataChangeListener {

    private VeterinarioService service;

    @FXML
    private TableView<Veterinario> tableViewVeterinario;

    @FXML
    private TableColumn<Veterinario, Integer> tableColumnId;

    @FXML
    private TableColumn<Veterinario, String> tableColumnName;

    @FXML
    private TableColumn<Veterinario, String> tableColumnEmail;

    @FXML
    private TableColumn<Veterinario, String> tableColumnTelefone;

    @FXML
    private TableColumn<Veterinario, Veterinario> tableColumnEDIT;

    @FXML
    private TableColumn<Veterinario, Veterinario> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Veterinario> obsList;

    /**
     * Manipula o evento de clique no botão "Novo" para abrir o formulário de cadastro de um novo veterinário.
     * 
     * @param event O evento do clique no botão "Novo".
     */
    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Veterinario obj = new Veterinario();
        createDialogForm(obj, "/gui/VeterinarioRegistro.fxml", parentStage);
    }

    /**
     * Define o serviço de veterinários a ser utilizado por este controlador.
     * 
     * @param service O serviço responsável pela gestão dos veterinários.
     */
    public void setVeterinarioService(VeterinarioService service) {
        this.service = service;
    }

    /**
     * Inicializa o controlador e seus componentes, configurando a tabela de veterinários.
     * Este método é automaticamente chamado quando o FXML é carregado.
     * 
     * @param url A URL da localização do recurso FXML.
     * @param rb O bundle de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    /**
     * Inicializa os componentes da tabela, definindo como as colunas devem ser preenchidas.
     */
    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewVeterinario.prefHeightProperty().bind(stage.heightProperty());
    }

    /**
     * Atualiza a tabela de veterinários com os dados mais recentes.
     */
    public void updateTableView() {
        if (service == null) throw new IllegalStateException("Service was null");

        List<Veterinario> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewVeterinario.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    /**
     * Cria um formulário de diálogo para cadastrar ou editar um veterinário.
     * 
     * @param obj O veterinário a ser cadastrado ou editado.
     * @param absoluteName O nome do arquivo FXML do formulário.
     * @param parentStage A janela pai do diálogo.
     */
    private void createDialogForm(Veterinario obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            VeterinarioRegistroController controller = loader.getController();
            controller.setVeterinario(obj);
            controller.setVeterinarioService(new VeterinarioService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Veterinario data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Inicializa os botões de edição na tabela.
     * Cada linha terá um botão "Editar" que abre o formulário de edição do veterinário correspondente.
     */
    private void initEditButtons() {
        tableColumnEDIT.setCellFactory(param -> new TableCell<Veterinario, Veterinario>() {
            private final Button button = new Button("Edit");

            @Override
            protected void updateItem(Veterinario obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> createDialogForm(obj, "/gui/VeterinarioRegistro.fxml", Utils.currentStage(event)));
            }
        });
    }

    /**
     * Inicializa os botões de remoção na tabela.
     * Cada linha terá um botão "Remover" que exclui o veterinário correspondente.
     */
    private void initRemoveButtons() {
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Veterinario, Veterinario>() {
            private final Button button = new Button("Remove");

            @Override
            protected void updateItem(Veterinario obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    /**
     * Remove um veterinário da lista após confirmação do usuário.
     * 
     * @param obj O veterinário a ser removido.
     */
    private void removeEntity(Veterinario obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure you want to delete?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (service == null) throw new IllegalStateException("Service was null");

            try {
                service.remove(obj);
                updateTableView();
            } catch (DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
            }
        }
    }

    /**
     * Atualiza a tabela quando os dados são alterados, refletindo as mudanças realizadas.
     */
    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
