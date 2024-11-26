package gui;

import java.util.List;

import db.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.dao.impl.AnimalDaoJDBC;
import model.entities.Animal;
import model.entities.Cliente;

public class AnimalController {

    private AnimalDaoJDBC animalDao;

    // Campos da tela para o cadastro/edição de animais
    @FXML
    private TextField nomeField;
    @FXML
    private TextField idadeField;
    @FXML
    private TextField racaField;
    @FXML
    private TextField especieField;
    @FXML
    private TextField clienteIdField;
    @FXML
    private Button salvarButton;
    @FXML
    private VBox listaAnimaisVBox; // VBox para exibir a lista de animais

    public AnimalController() {
        this.animalDao = new AnimalDaoJDBC(DB.getConnection());
    }

    @FXML
    public void initialize() {
        // Inicialize a tela, caso precise carregar algum dado inicial
   //     carregarListaAnimais();
    }

    private Integer clienteId;

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    @FXML
    public void salvarAnimal() {
        try {
        	System.out.println("clienteId ao salvar: " + clienteId);
        	String nome = nomeField.getText();
            Integer idade = Integer.parseInt(idadeField.getText());
            String raca = racaField.getText();
            String especie = especieField.getText();

            // Obtenha o ID do cliente do campo clienteIdField
            if (clienteIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("O ID do cliente não pode estar vazio.");
            }

            Integer clienteId = Integer.parseInt(clienteIdField.getText());
            Cliente cliente = new Cliente();
            cliente.setId(clienteId);

            // Crie o animal e salve no banco de dados
            Animal animal = new Animal(null, nome, idade, raca, especie, cliente);
            animalDao.insert(animal);

            showAlert("Sucesso", "Animal cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos(); // Limpar campos após o salvamento
        } catch (Exception e) {
            showAlert("Erro", "Erro ao salvar animal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        racaField.clear();
        especieField.clear();
    }

/*
    @FXML
    public void carregarListaAnimais() {
        listaAnimaisVBox.getChildren().clear(); // Limpa a lista atual

        if (clienteId == null) {
            showAlert("Aviso", "Cliente não identificado. Não foi possível carregar os animais.", Alert.AlertType.WARNING);
            return;
        }

        List<Animal> animais = animalDao.findAnimaisByClienteId(clienteId);

        for (Animal animal : animais) {
            Button animalButton = new Button(animal.getNome());
            animalButton.setOnAction(event -> mostrarAnimalDetalhes(animal));
            listaAnimaisVBox.getChildren().add(animalButton);
        }
    }
*/

    public void mostrarAnimalDetalhes(Animal animal) {
        // Lógica para mostrar os detalhes do animal (ex: preencher campos para edição)
        nomeField.setText(animal.getNome());
        idadeField.setText(String.valueOf(animal.getIdade()));
        racaField.setText(animal.getRaca());
        especieField.setText(animal.getEspecie());
        clienteIdField.setText(String.valueOf(animal.getCliente().getId()));
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
