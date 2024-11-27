package model.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Animal {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private IntegerProperty idade = new SimpleIntegerProperty();
    private StringProperty raca = new SimpleStringProperty();
    private StringProperty especie = new SimpleStringProperty();
    private Cliente cliente;

    public Animal() {}

    public Animal(Integer id, String nome, Integer idade, String raca, String especie, Cliente cliente) {
        if (id != null) {
            this.id.set(id);
        } else {
            this.id.set(0);  // Ou outra lógica para definir um valor padrão
        }
        this.nome.set(nome);
        this.idade.set(idade);
        this.raca.set(raca);
        this.especie.set(especie);
        this.cliente = cliente;
    }


    // Propriedades observáveis para as colunas da tabela
    public Integer getId() {
        return id.get();
    }
    
    public void setId(Integer id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public Integer getIdade() {
        return idade.get();
    }

    public void setIdade(Integer idade) {
        this.idade.set(idade);
    }

    public IntegerProperty idadeProperty() {
        return idade;
    }

    public String getRaca() {
        return raca.get();
    }

    public void setRaca(String raca) {
        this.raca.set(raca);
    }

    public StringProperty racaProperty() {
        return raca;
    }

    public String getEspecie() {
        return especie.get();
    }

    public void setEspecie(String especie) {
        this.especie.set(especie);
    }

    public StringProperty especieProperty() {
        return especie;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Método para definir o cliente_id diretamente
    public void setClienteId(Integer clienteId) {
        if (this.cliente == null) {
            this.cliente = new Cliente();
        }
        this.cliente.setId(clienteId);
    }

    public boolean isNew() {
        return this.id.get() == 0;  // Verifica se o ID é zero
    }




    @Override
    public String toString() {
        return "Animal [id=" + id.get() + ", nome=" + nome.get() + ", idade=" + idade.get() + 
               ", raca=" + raca.get() + ", especie=" + especie.get() + "]";
    }
}

