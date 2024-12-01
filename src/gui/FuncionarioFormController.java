package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Funcionario;
import model.exceptions.ValidationException;
import model.services.FuncionarioService;

/**
 * Controlador da tela de cadastro e edição de funcionários.
 * 
 * Esta classe gerencia os dados do funcionário, incluindo a validação, exibição e manipulação dos dados
 * no formulário, bem como o controle de ações de salvar e cancelar.
 */
public class FuncionarioFormController implements Initializable {

    private Funcionario entity;
    private FuncionarioService service;
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
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

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
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    /**
     * Configura o objeto de funcionário que será manipulado neste formulário.
     * 
     * @param entity O funcionário a ser editado ou criado.
     */
    public void setFuncionario(Funcionario entity) {
        this.entity = entity;
    }

    /**
     * Configura o serviço utilizado para salvar ou atualizar os dados do funcionário.
     * 
     * @param service O serviço de funcionário.
     */
    public void setFuncionarioService(FuncionarioService service) {
        this.service = service;
    }

    /**
     * Inscreve um ouvinte para ouvir mudanças de dados no formulário.
     * 
     * @param listener O ouvinte a ser inscrito.
     */
    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    /**
     * Ação do botão "Salvar" do formulário.
     * Salva ou atualiza os dados do funcionário, exibindo mensagens de erro, se necessário.
     * 
     * @param event O evento do clique no botão.
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
     * Extrai os dados do formulário e os converte para um objeto `Funcionario`.
     * 
     * @return Um objeto `Funcionario` com os dados extraídos do formulário.
     * @throws ValidationException Se houver erros de validação nos dados.
     */
    private Funcionario getFormData() {
        Funcionario obj = new Funcionario();
        ValidationException exception = new ValidationException("Validation error");

        // Verifica e seta o ID
        obj.setId(Utils.tryParseToInt(txtId.getText()));

        // Valida e seta o campo 'name'
        if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        // Valida e seta o campo 'email'
        if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
            exception.addError("email", "Field can't be empty");
        }
        obj.setEmail(txtEmail.getText());

        // Valida e seta o campo 'birthDate'
        if (dpBirthDate.getValue() == null) {
            exception.addError("birthDate", "Field can't be empty");
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        // Valida e seta o campo 'baseSalary'
        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().isEmpty()) {
            exception.addError("baseSalary", "Field can't be empty");
        } else {
            obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
        }

        // Valida e seta o campo 'password'
        if (txtPassword.getText() == null || txtPassword.getText().trim().isEmpty()) {
            exception.addError("password", "Field can't be empty");
        }
        obj.setPassword(txtPassword.getText());

        // Lança exceção se houver erros
        if (exception.getErrors().size() > 0) throw exception;

        return obj;
    }

    /**
     * Ação do botão "Cancelar" do formulário.
     * Fecha o formulário sem salvar as alterações.
     * 
     * @param event O evento do clique no botão.
     */
    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    /**
     * Inicializa os componentes do formulário.
     * Este método é chamado automaticamente ao carregar a tela.
     * 
     * @param url O URL que foi usado para localizar o arquivo FXML.
     * @param rb O ResourceBundle usado para internacionalização (caso seja necessário).
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    /**
     * Configura as restrições nos campos de texto do formulário.
     */
    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
    }

    /**
     * Atualiza os dados do formulário com os dados do funcionário.
     */
    public void updateFormData() {
        if (entity == null) throw new IllegalStateException("Entity was null");

        txtId.setText(entity.getId() == null ? "" : String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        txtBaseSalary.setText(String.format(Locale.US, "%.2f", entity.getBaseSalary()));

        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }

        // Preenche o campo 'password' (vazio para novos funcionários)
        txtPassword.setText(entity.getPassword() == null ? "" : entity.getPassword());
    }

    /**
     * Exibe as mensagens de erro no formulário, de acordo com os campos que falharam na validação.
     * 
     * @param errors Um mapa contendo os erros de validação, com a chave sendo o nome do campo e o valor sendo a mensagem de erro.
     */
    private void setErrorMessages(Map<String, String> errors) {
        labelErrorName.setText(errors.getOrDefault("name", ""));
        labelErrorEmail.setText(errors.getOrDefault("email", ""));
        labelErrorBirthDate.setText(errors.getOrDefault("birthDate", ""));
        labelErrorBaseSalary.setText(errors.getOrDefault("baseSalary", ""));
    }
}

