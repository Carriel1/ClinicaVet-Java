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
    private VBox listaAnimaisVBox; 

    private AnimalService animalService;

    
    public AnimalController() {
        this.animalDao = new AnimalDaoJDBC(DB.getConnection());
    }
    
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
            String nome = nomeField.getText();
            String idadeText = idadeField.getText();
            String raca = racaField.getText();
            String especie = especieField.getText();

            if (nome.isEmpty() || idadeText.isEmpty() || raca.isEmpty() || especie.isEmpty()) {
                showAlert("Erro", "Todos os campos devem ser preenchidos!", Alert.AlertType.ERROR);
                return;
            }

            Integer idade = Integer.parseInt(idadeText);

            Animal animal = new Animal();
            animal.setNome(nome);
            animal.setIdade(idade);
            animal.setRaca(raca);
            animal.setEspecie(especie);
            animal.setClienteId(clienteId);  

            animalDao.insert(animal);

            showAlert("Sucesso", "Animal cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos(); 
        } catch (NumberFormatException e) {
            showAlert("Erro", "Idade inválida. Por favor, insira um número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erro", "Erro ao salvar animal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    // Método para salvar o animal
    public void salvarAnimal(Animal animal) {
        try {
            animalService.insert(animal);  
        } catch (Exception e) {
            e.printStackTrace();  
            throw new RuntimeException("Erro ao salvar animal", e); 
        }
    }
    
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        racaField.clear();
        especieField.clear();
    }

    public void mostrarAnimalDetalhes(Animal animal) {
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

