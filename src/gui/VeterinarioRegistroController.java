package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Veterinario;
import model.exceptions.ValidationException;
import model.services.VeterinarioService;

/**
 * Controlador responsável pelo registro e edição de veterinários.
 * 
 * Esta classe gerencia a tela de cadastro e edição de veterinários, validando os dados de entrada,
 * exibindo mensagens de erro quando necessário e chamando o serviço para salvar ou atualizar o veterinário.
 */
public class VeterinarioRegistroController implements Initializable {

    private Veterinario entity;
    private VeterinarioService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtCpf;  

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorPassword;

    @FXML
    private Label labelErrorTelefone;

    @FXML
    private Label labelErrorCpf;  

    /**
     * Define a entidade veterinário a ser registrada ou editada.
     * 
     * @param entity A entidade Veterinário que será registrada ou editada.
     */
    public void setVeterinario(Veterinario entity) {
        this.entity = entity;
    }

    /**
     * Define o serviço responsável pela persistência dos dados de veterinário.
     * 
     * @param service O serviço de veterinário.
     */
    public void setVeterinarioService(VeterinarioService service) {
        this.service = service;
    }

    /**
     * Inscreve um ouvinte para mudanças nos dados.
     * 
     * @param listener O ouvinte que será notificado quando os dados mudarem.
     */
    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    /**
     * Ação do botão de salvar. Valida os dados do formulário, salva ou atualiza o veterinário,
     * e notifica os ouvintes sobre a mudança.
     * 
     * @param event O evento de clique no botão de salvar.
     */
    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null || service == null) {
            throw new IllegalStateException("Entity or Service is null");
        }
        try {
            entity = getFormData(); 
            service.saveOrUpdate(entity);  
            notifyDataChangeListeners(); 
            Utils.currentStage(event).close(); 
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors()); 
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Notifica todos os ouvintes registrados sobre a mudança nos dados.
     */
    private void notifyDataChangeListeners() {
        dataChangeListeners.forEach(DataChangeListener::onDataChanged);
    }

    /**
     * Recupera os dados do formulário e os valida antes de criar um objeto Veterinário.
     * 
     * @return O objeto Veterinário com os dados do formulário.
     * @throws ValidationException Caso haja erro de validação nos campos do formulário.
     */
    private Veterinario getFormData() {
        Veterinario obj = new Veterinario();
        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
            exception.addError("name", "Field can't be empty");
        }
        obj.setNome(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
            exception.addError("email", "Field can't be empty");
        }
        obj.setEmail(txtEmail.getText());

        if (txtTelefone.getText() == null || txtTelefone.getText().trim().isEmpty()) {
            exception.addError("telefone", "Field can't be empty");
        }
        obj.setTelefone(txtTelefone.getText());

        if (txtPassword.getText() == null || txtPassword.getText().trim().isEmpty()) {
            exception.addError("password", "Field can't be empty");
        }
        obj.setSenha(txtPassword.getText());

        if (txtCpf.getText() == null || txtCpf.getText().trim().isEmpty()) {
            exception.addError("cpf", "Field can't be empty");
        }
        obj.setCpf(txtCpf.getText());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return obj;
    }

    /**
     * Exibe as mensagens de erro ao lado dos campos correspondentes.
     * 
     * @param errors O mapa contendo os erros de validação dos campos.
     */
    private void setErrorMessages(Map<String, String> errors) {
        labelErrorName.setText(errors.get("name"));
        labelErrorEmail.setText(errors.get("email"));
        labelErrorPassword.setText(errors.get("password"));
        labelErrorTelefone.setText(errors.get("telefone"));
        labelErrorCpf.setText(errors.get("cpf"));
    }

    /**
     * Ação do botão de cancelar. Fecha a tela de registro/edição.
     * 
     * @param event O evento de clique no botão de cancelar.
     */
    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * 
     * @param url O URL para carregar o FXML (não utilizado aqui).
     * @param rb O recurso de bundle (não utilizado aqui).
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    /**
     * Inicializa as restrições de entrada para os campos do formulário.
     */
    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 100);
        Constraints.setTextFieldMaxLength(txtEmail, 50);
        Constraints.setTextFieldMaxLength(txtTelefone, 15);
        Constraints.setTextFieldMaxLength(txtPassword, 50);
        Constraints.setTextFieldMaxLength(txtCpf, 11);
    }

    /**
     * Atualiza os dados do formulário com os dados da entidade Veterinário.
     */
    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity is null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());
        txtTelefone.setText(entity.getTelefone());
        txtPassword.setText(entity.getSenha());
        txtCpf.setText(entity.getCpf());
    }
}
