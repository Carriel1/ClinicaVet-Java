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

    private AnimalService animalService;
    private ClienteService clienteService;

    // Método para injeção dos serviços (sem o AnimalController)
    public void setServices(AnimalService animalService, ClienteService clienteService) {
        if (animalService == null || clienteService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.animalService = animalService;
        this.clienteService = clienteService;

        carregarClientes();
    }

    // Carregar todos os clientes no ComboBox
    private void carregarClientes() {
        try {
            ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteService.findAll());
            comboBoxClientes.setItems(clientes);
        } catch (Exception e) {
            Alerts.showAlert("Erro", null, "Falha ao carregar a lista de clientes.", Alert.AlertType.ERROR);
        }
    }

    // Método para salvar o animal e associá-lo ao cliente selecionado
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

            // Criando o animal com os dados inseridos
            Animal animal = new Animal(null, nome, idade, raca, especie, clienteSelecionado);

            // Chama o serviço de inserção
            animalService.insert(animal);

            Alerts.showAlert("Sucesso", null, "Animal cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            fecharTela();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro no console
            Alerts.showAlert("Erro", null, "Falha ao cadastrar o animal. Verifique os dados.", Alert.AlertType.ERROR);
        }
    }

    // Método para fechar a tela de cadastro
    private void fecharTela() {
        Stage stage = (Stage) nomeTextField.getScene().getWindow();
        stage.close();
    }
}
