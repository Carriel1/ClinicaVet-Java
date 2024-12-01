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

/**
 * Controlador responsável pela gestão das operações relacionadas aos animais.
 * Inclui a criação e exibição de detalhes dos animais, além da interação com o banco de dados.
 */
public class AnimalController {

    /**
     * Objeto responsável pela interação com o banco de dados para a entidade Animal.
     */
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

    /**
     * Serviço responsável pela lógica de negócio da entidade Animal.
     */
    private AnimalService animalService;

    /**
     * Construtor da classe. Inicializa o objeto `animalDao` com a conexão do banco de dados.
     */
    public AnimalController() {
        this.animalDao = new AnimalDaoJDBC(DB.getConnection());
    }

    /**
     * Método para injeção de dependência do serviço `AnimalService`.
     * 
     * @param animalService O serviço que irá gerenciar a lógica de negócio da entidade Animal.
     */
    public void setAnimalService(AnimalService animalService) {
        this.animalService = animalService;
    }
    
    /**
     * Método chamado automaticamente pelo JavaFX durante a inicialização da tela.
     * Pode ser usado para configurar valores iniciais ou realizar configurações específicas.
     */
    @FXML
    public void initialize() {
    }

    private Integer clienteId;

    /**
     * Define o identificador do cliente ao qual o animal pertence.
     * 
     * @param clienteId O identificador do cliente.
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Método responsável por salvar o animal após validar os campos do formulário.
     * Exibe alertas de sucesso ou erro durante o processo.
     */
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

    /**
     * Método alternativo para salvar um animal utilizando o serviço `AnimalService`.
     * 
     * @param animal O animal a ser salvo.
     */
    public void salvarAnimal(Animal animal) {
        try {
            animalService.insert(animal);  
        } catch (Exception e) {
            e.printStackTrace();  
            throw new RuntimeException("Erro ao salvar animal", e); 
        }
    }

    /**
     * Limpa os campos do formulário de cadastro de animal.
     */
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        racaField.clear();
        especieField.clear();
    }

    /**
     * Exibe os detalhes de um animal no formulário de cadastro.
     * 
     * @param animal O animal cujos detalhes serão exibidos.
     */
    public void mostrarAnimalDetalhes(Animal animal) {
        nomeField.setText(animal.getNome());
        idadeField.setText(String.valueOf(animal.getIdade()));
        racaField.setText(animal.getRaca());
        especieField.setText(animal.getEspecie());
    }

    /**
     * Exibe um alerta na tela com uma mensagem personalizada.
     * 
     * @param title O título do alerta.
     * @param message A mensagem do alerta.
     * @param type O tipo de alerta (informativo, erro, etc).
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
