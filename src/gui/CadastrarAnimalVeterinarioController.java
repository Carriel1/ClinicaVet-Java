package gui;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Animal;
import model.entities.Cliente;
import model.services.AnimalService;
import model.services.ClienteService;

/**
 * Controlador responsável pelo cadastro de animais e vinculação ao cliente selecionado.
 * Este controlador é utilizado na tela de cadastro de animais para veterinários.
 */
public class CadastrarAnimalVeterinarioController {

    @FXML
    private ComboBox<Cliente> comboBoxClientes;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField idadeTextField;

    @FXML
    private TextField racaTextField;

    @FXML
    private TextField especieTextField;

    /**
     * Serviço responsável pela lógica de negócio da entidade Animal.
     */
    private AnimalService animalService;

    /**
     * Serviço responsável pela lógica de negócio da entidade Cliente.
     */
    private ClienteService clienteService;

    /**
     * Método para injeção de dependência dos serviços `AnimalService` e `ClienteService`.
     * Este método é chamado para configurar os serviços necessários para o cadastro de animais.
     * 
     * @param animalService Serviço para manipulação dos dados dos animais.
     * @param clienteService Serviço para manipulação dos dados dos clientes.
     */
    public void setServices(AnimalService animalService, ClienteService clienteService) {
        if (animalService == null || clienteService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.animalService = animalService;
        this.clienteService = clienteService;

        carregarClientes();
    }

    /**
     * Carrega a lista de clientes no ComboBox. Esta lista é obtida a partir do serviço `ClienteService`.
     * Caso ocorra algum erro ao carregar os dados, é exibido um alerta.
     */
    private void carregarClientes() {
        try {
            ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteService.findAll());
            comboBoxClientes.setItems(clientes);
        } catch (Exception e) {
            Alerts.showAlert("Erro", null, "Falha ao carregar a lista de clientes.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método chamado quando o botão "Salvar" é pressionado. Realiza a validação dos campos e
     * salva o animal no banco de dados, associando-o ao cliente selecionado.
     * 
     * @throws NumberFormatException Se a idade informada não for um número válido.
     */
    @FXML
    public void onSalvarAnimal() {
        try {
            Cliente clienteSelecionado = comboBoxClientes.getSelectionModel().getSelectedItem();
            if (clienteSelecionado == null || clienteSelecionado.getId() == null) {
                Alerts.showAlert("Erro", null, "Selecione um cliente válido para vincular ao animal.", Alert.AlertType.ERROR);
                return;
            }

            String nome = nomeTextField.getText();
            String idadeText = idadeTextField.getText();
            Integer idade = Integer.parseInt(idadeText); 
            String raca = racaTextField.getText();
            String especie = especieTextField.getText();

            Animal animal = new Animal(null, nome, idade, raca, especie, clienteSelecionado);

            animalService.insert(animal);

            Alerts.showAlert("Sucesso", null, "Animal cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            fecharTela();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro no console
            Alerts.showAlert("Erro", null, "Falha ao cadastrar o animal. Verifique os dados.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Fecha a tela de cadastro após o cadastro bem-sucedido do animal.
     */
    private void fecharTela() {
        Stage stage = (Stage) nomeTextField.getScene().getWindow();
        stage.close();
    }
}

