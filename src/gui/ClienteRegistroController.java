package gui;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.services.ClienteService;

public class ClienteRegistroController {

    private ClienteService clienteService;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCpf;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Ação para o botão Salvar
    @FXML
    public void onBtSalvarAction(ActionEvent event) {
        if (clienteService == null) {
            Alerts.showAlert("Erro", "Serviço não inicializado", "O serviço de cliente está nulo.", AlertType.ERROR);
            return;
        }

        try {
            String nome = txtNome.getText();
            String endereco = txtEndereco.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();
            String cpf = txtCpf.getText();

            // Verificação simples de preenchimento dos campos obrigatórios
            if (nome.isEmpty() || cpf.isEmpty()) {
                Alerts.showAlert("Erro no Registro", null, "Nome e CPF são obrigatórios.", AlertType.ERROR);
                return;
            }

            boolean registrado = clienteService.registrarCliente(nome, endereco, telefone, email);

            if (registrado) {
                Alerts.showAlert("Sucesso", null, "Cliente registrado com sucesso!", AlertType.INFORMATION);
                limparCampos();
            } else {
                Alerts.showAlert("Erro", "Erro ao Registrar", "Não foi possível registrar o cliente.", AlertType.ERROR);
            }
        } catch (Exception e) {
            Alerts.showAlert("Erro no Registro", null, "Ocorreu um erro ao tentar registrar o cliente.", AlertType.ERROR);
        }
    }

    // Ação para o botão Cancelar
    @FXML
    public void onBtCancelarAction(ActionEvent event) {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    // Método para limpar os campos após registro
    private void limparCampos() {
        txtNome.clear();
        txtEndereco.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtCpf.clear();
    }
}
