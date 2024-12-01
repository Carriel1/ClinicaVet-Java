package model.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representa um animal no sistema.
 * Contém informações sobre o animal, como nome, idade, raça, espécie e o cliente ao qual o animal pertence.
 */
public class Animal {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private IntegerProperty idade = new SimpleIntegerProperty();
    private StringProperty raca = new SimpleStringProperty();
    private StringProperty especie = new SimpleStringProperty();
    private Cliente cliente;

    /**
     * Construtor padrão para criar uma instância de {@link Animal}.
     */
    public Animal() {}

    /**
     * Construtor para criar uma instância de {@link Animal} com os valores fornecidos.
     * 
     * @param id O ID do animal.
     * @param nome O nome do animal.
     * @param idade A idade do animal.
     * @param raca A raça do animal.
     * @param especie A espécie do animal.
     * @param cliente O cliente ao qual o animal pertence.
     */
    public Animal(Integer id, String nome, Integer idade, String raca, String especie, Cliente cliente) {
        if (id != null) {
            this.id.set(id);
        } else {
            this.id.set(0);  
        }
        this.nome.set(nome);
        this.idade.set(idade);
        this.raca.set(raca);
        this.especie.set(especie);
        this.cliente = cliente;
    }

    /**
     * Retorna o ID do animal.
     * 
     * @return O ID do animal.
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * Define o ID do animal.
     * 
     * @param id O ID a ser definido.
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Retorna a propriedade {@link IntegerProperty} do ID do animal, que permite a vinculação de dados em JavaFX.
     * 
     * @return A propriedade {@link IntegerProperty} do ID do animal.
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Retorna o nome do animal.
     * 
     * @return O nome do animal.
     */
    public String getNome() {
        return nome.get();
    }

    /**
     * Define o nome do animal.
     * 
     * @param nome O nome a ser definido.
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * Retorna a propriedade {@link StringProperty} do nome do animal, que permite a vinculação de dados em JavaFX.
     * 
     * @return A propriedade {@link StringProperty} do nome do animal.
     */
    public StringProperty nomeProperty() {
        return nome;
    }

    /**
     * Retorna a idade do animal.
     * 
     * @return A idade do animal.
     */
    public Integer getIdade() {
        return idade.get();
    }

    /**
     * Define a idade do animal.
     * 
     * @param idade A idade a ser definida.
     */
    public void setIdade(Integer idade) {
        this.idade.set(idade);
    }

    /**
     * Retorna a propriedade {@link IntegerProperty} da idade do animal, que permite a vinculação de dados em JavaFX.
     * 
     * @return A propriedade {@link IntegerProperty} da idade do animal.
     */
    public IntegerProperty idadeProperty() {
        return idade;
    }

    /**
     * Retorna a raça do animal.
     * 
     * @return A raça do animal.
     */
    public String getRaca() {
        return raca.get();
    }

    /**
     * Define a raça do animal.
     * 
     * @param raca A raça a ser definida.
     */
    public void setRaca(String raca) {
        this.raca.set(raca);
    }

    /**
     * Retorna a propriedade {@link StringProperty} da raça do animal, que permite a vinculação de dados em JavaFX.
     * 
     * @return A propriedade {@link StringProperty} da raça do animal.
     */
    public StringProperty racaProperty() {
        return raca;
    }

    /**
     * Retorna a espécie do animal.
     * 
     * @return A espécie do animal.
     */
    public String getEspecie() {
        return especie.get();
    }

    /**
     * Define a espécie do animal.
     * 
     * @param especie A espécie a ser definida.
     */
    public void setEspecie(String especie) {
        this.especie.set(especie);
    }

    /**
     * Retorna a propriedade {@link StringProperty} da espécie do animal, que permite a vinculação de dados em JavaFX.
     * 
     * @return A propriedade {@link StringProperty} da espécie do animal.
     */
    public StringProperty especieProperty() {
        return especie;
    }

    /**
     * Retorna o cliente ao qual o animal pertence.
     * 
     * @return O cliente associado ao animal.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente ao qual o animal pertence.
     * 
     * @param cliente O cliente a ser associado ao animal.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Define diretamente o ID do cliente ao qual o animal pertence, criando um novo objeto {@link Cliente} caso necessário.
     * 
     * @param clienteId O ID do cliente a ser associado ao animal.
     */
    public void setClienteId(Integer clienteId) {
        if (this.cliente == null) {
            this.cliente = new Cliente();
        }
        this.cliente.setId(clienteId);
    }

    /**
     * Verifica se o animal é novo, ou seja, se ainda não possui um ID atribuído.
     * 
     * @return {@code true} se o animal for novo (ID igual a 0), caso contrário {@code false}.
     */
    public boolean isNew() {
        return this.id.get() == 0;
    }

    /**
     * Retorna uma representação em string do objeto {@link Animal}, incluindo seus principais atributos.
     * 
     * @return Uma string representando o animal.
     */
    @Override
    public String toString() {
        return "Animal [id=" + id.get() + ", nome=" + nome.get() + ", idade=" + idade.get() + 
               ", raca=" + raca.get() + ", especie=" + especie.get() + "]";
    }

    /**
     * Retorna uma string com informações detalhadas sobre o animal, incluindo nome, idade, raça e espécie.
     * 
     * @return Uma string contendo as informações do animal.
     */
    public String animalInfo() {
        return "Nome: " + getNome() + ", Idade: " + getIdade() + " anos, Raça: " + getRaca() + ", Espécie: " + getEspecie();
    }
}
