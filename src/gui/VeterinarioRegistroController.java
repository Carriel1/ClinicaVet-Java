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

    public void setVeterinario(Veterinario entity) {
        this.entity = entity;
    }

    public void setVeterinarioService(VeterinarioService service) {
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

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
            Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR); // Mostra alertas de erros de banco de dados
        }
    }


    private void notifyDataChangeListeners() {
        dataChangeListeners.forEach(DataChangeListener::onDataChanged);
    }

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

    private void setErrorMessages(Map<String, String> errors) {
        labelErrorName.setText(errors.get("name"));
        labelErrorEmail.setText(errors.get("email"));
        labelErrorPassword.setText(errors.get("password"));
        labelErrorTelefone.setText(errors.get("telefone"));
        labelErrorCpf.setText(errors.get("cpf"));
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 100);
        Constraints.setTextFieldMaxLength(txtEmail, 50);
        Constraints.setTextFieldMaxLength(txtTelefone, 15);
        Constraints.setTextFieldMaxLength(txtPassword, 50);
        Constraints.setTextFieldMaxLength(txtCpf, 11);
    }

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

