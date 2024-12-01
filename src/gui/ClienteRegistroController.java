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
import model.entities.Cliente;
import model.exceptions.ValidationException;
import model.services.ClienteService;

/**
 * Controlador responsável pela interface de registro de clientes.
 * Ele lida com o formulário de registro, validação de dados, e a interação com o serviço de cliente.
 */
public class ClienteRegistroController implements Initializable {

    private Cliente entity;
    private ClienteService service;
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
    private TextField txtPhone;

    @FXML
    private TextField txtEndereco;  // Novo campo para o endereço

    @FXML
    private TextField txtCpf;  // Novo campo para o CPF

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
    private Label labelErrorPhone;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorEndereco;  // Label de erro para o endereço

    @FXML
    private Label labelErrorCpf;  // Label de erro para o CPF

    /**
     * Define a entidade Cliente para o controlador.
     * 
     * @param entity Cliente a ser associado ao controlador.
     */
    public void setCliente(Cliente entity) {
        this.entity = entity;
    }

    /**
     * Define o serviço Cliente a ser usado pelo controlador.
     * 
     * @param service Serviço de cliente que será utilizado para operações de banco de dados.
     */
    public void setClienteService(ClienteService service) {
        this.service = service;
    }

    /**
     * Inscreve um ouvinte para alterações nos dados.
     * 
     * @param listener O ouvinte que será notificado quando os dados forem alterados.
     */
    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    /**
     * Ação executada ao clicar no botão de salvar.
     * Valida os dados do formulário e chama o serviço para salvar o cliente.
     * 
     * @param event Evento gerado pelo clique no botão de salvar.
     */
    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null || service == null) {
            throw new IllegalStateException("Entity or Service is null");
        }
        try {
            entity = getFormData();
            service.registrarCliente(entity.getNome(), entity.getEmail(), entity.getTelefone(), entity.getSenha(), entity.getEndereco(), entity.getCpf());
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Notifica todos os ouvintes registrados sobre a alteração nos dados.
     */
    private void notifyDataChangeListeners() {
        dataChangeListeners.forEach(DataChangeListener::onDataChanged);
    }

    /**
     * Obtém os dados do formulário e os valida.
     * 
     * @return Um objeto Cliente com os dados preenchidos no formulário.
     * @throws ValidationException Se algum dado não for válido.
     */
    private Cliente getFormData() {
        Cliente obj = new Cliente();
        ValidationException exception = new ValidationException("Validation error");

        // Verifica e seta o ID
        obj.setId(Utils.tryParseToInt(txtId.getText()));

        // Valida e seta o campo 'name'
        if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
            exception.addError("name", "Field can't be empty");
        }
        obj.setNome(txtName.getText());

        // Valida e seta o campo 'email'
        if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
            exception.addError("email", "Field can't be empty");
        }
        obj.setEmail(txtEmail.getText());
        
        // Valida e seta o campo 'phone'
        if (txtPhone.getText() == null || txtPhone.getText().trim().isEmpty()) {
            exception.addError("phone", "Field can't be empty");
        }
        obj.setTelefone(txtPhone.getText());

        // Valida e seta o campo 'password'
        if (txtPassword.getText() == null || txtPassword.getText().trim().isEmpty()) {
            exception.addError("password", "Field can't be empty");
        }
        obj.setSenha(txtPassword.getText());

        // Valida e seta o campo 'endereco'
        if (txtEndereco.getText() == null || txtEndereco.getText().trim().isEmpty()) {
            exception.addError("endereco", "Field can't be empty");
        }
        obj.setEndereco(txtEndereco.getText());

        // Valida e seta o campo 'cpf'
        if (txtCpf.getText() == null || txtCpf.getText().trim().isEmpty()) {
            exception.addError("cpf", "Field can't be empty");
        }
        obj.setCpf(txtCpf.getText());

        // Lança exceção se houver erros
        if (exception.getErrors().size() > 0) throw exception;

        return obj;
    }

    /**
     * Ação executada ao clicar no botão de cancelar.
     * Fecha a janela atual sem salvar as alterações.
     * 
     * @param event Evento gerado pelo clique no botão de cancelar.
     */
    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    /**
     * Inicializa o controlador. Este método é chamado automaticamente
     * após a injeção de dependências.
     * 
     * @param url URL usada para localizar recursos.
     * @param rb Bundle de recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    /**
     * Configura as restrições dos campos de texto.
     * Limita o tamanho máximo e define os campos de texto para aceitar apenas números quando necessário.
     */
    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Constraints.setTextFieldMaxLength(txtPhone, 15);
        Constraints.setTextFieldMaxLength(txtPassword, 20);
        Constraints.setTextFieldMaxLength(txtEndereco, 100); // Limite para o endereço
        Constraints.setTextFieldMaxLength(txtCpf, 11); // Limite de 11 caracteres para CPF
    }

    /**
     * Atualiza o formulário com os dados da entidade Cliente.
     */
    public void updateFormData() {
        if (entity == null) throw new IllegalStateException("Entity was null");

        txtId.setText(entity.getId() == null ? "" : String.valueOf(entity.getId()));
        txtName.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());
        txtPhone.setText(entity.getTelefone());
        txtPassword.setText(entity.getSenha() == null ? "" : entity.getSenha());
        txtEndereco.setText(entity.getEndereco() == null ? "" : entity.getEndereco());
        txtCpf.setText(entity.getCpf() == null ? "" : entity.getCpf());
    }

    /**
     * Define as mensagens de erro para os campos do formulário.
     * 
     * @param errors Mapa de erros com os campos e suas respectivas mensagens.
     */
    private void setErrorMessages(Map<String, String> errors) {
        labelErrorName.setText(errors.getOrDefault("name", ""));
        labelErrorEmail.setText(errors.getOrDefault("email", ""));
        labelErrorPhone.setText(errors.getOrDefault("phone", ""));
        labelErrorBirthDate.setText(errors.getOrDefault("birthDate", ""));
        labelErrorPassword.setText(errors.getOrDefault("password", ""));
        labelErrorEndereco.setText(errors.getOrDefault("endereco", ""));  // Erro de endereço
        labelErrorCpf.setText(errors.getOrDefault("cpf", ""));  // Erro de CPF
    }
}

