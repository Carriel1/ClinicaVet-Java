package gui;

import db.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.dao.impl.AnimalDaoJDBC;
import model.entities.Animal;
import model.services.AnimalService;

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
    private Button salvarButton;
    @FXML
    private VBox listaAnimaisVBox; // VBox para exibir a lista de animais

    private AnimalService animalService;

    
    public AnimalController() {
        this.animalDao = new AnimalDaoJDBC(DB.getConnection());
    }
    
    // Injeção do serviço
    public void setAnimalService(AnimalService animalService) {
        this.animalService = animalService;
    }
    
    @FXML
    public void initialize() {
    }

    private Integer clienteId;

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    @FXML
    public void salvarAnimal() {
        try {
            // Verifique se todos os campos foram preenchidos
            String nome = nomeField.getText();
            String idadeText = idadeField.getText();
            String raca = racaField.getText();
            String especie = especieField.getText();

            if (nome.isEmpty() || idadeText.isEmpty() || raca.isEmpty() || especie.isEmpty()) {
                showAlert("Erro", "Todos os campos devem ser preenchidos!", Alert.AlertType.ERROR);
                return;
            }

            Integer idade = Integer.parseInt(idadeText);

            // Crie o animal e salve no banco de dados
            Animal animal = new Animal();
            animal.setNome(nome);
            animal.setIdade(idade);
            animal.setRaca(raca);
            animal.setEspecie(especie);
            animal.setClienteId(clienteId);  // Passa o clienteId diretamente

            animalDao.insert(animal);

            showAlert("Sucesso", "Animal cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos(); // Limpar campos após o salvamento
        } catch (NumberFormatException e) {
            showAlert("Erro", "Idade inválida. Por favor, insira um número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erro", "Erro ao salvar animal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    // Método para salvar o animal
    public void salvarAnimal(Animal animal) {
        try {
            animalService.insert(animal);  // Chama o serviço para inserir o animal
        } catch (Exception e) {
            e.printStackTrace();  // Captura qualquer erro
            throw new RuntimeException("Erro ao salvar animal", e);  // Lança exceção caso ocorra um erro
        }
    }
    
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        racaField.clear();
        especieField.clear();
    }

    public void mostrarAnimalDetalhes(Animal animal) {
        // Lógica para mostrar os detalhes do animal (ex: preencher campos para edição)
        nomeField.setText(animal.getNome());
        idadeField.setText(String.valueOf(animal.getIdade()));
        racaField.setText(animal.getRaca());
        especieField.setText(animal.getEspecie());
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

