package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um cliente no sistema.
 * Contém informações sobre o cliente, como nome, e-mail, telefone, endereço, CPF e os animais associados.
 */
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String endereco;
    private String cpf;

    private List<Animal> animais = new ArrayList<>();

    /**
     * Construtor padrão para criar uma instância de {@link Cliente}.
     */
    public Cliente() {
    }

    /**
     * Construtor para criar uma instância de {@link Cliente} com os valores fornecidos.
     * 
     * @param id O ID do cliente.
     * @param nome O nome do cliente.
     * @param email O e-mail do cliente.
     * @param telefone O telefone do cliente.
     * @param senha A senha do cliente.
     * @param endereco O endereço do cliente.
     * @param cpf O CPF do cliente.
     */
    public Cliente(Integer id, String nome, String email, String telefone, String senha, String endereco, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    /**
     * Retorna o ID do cliente.
     * 
     * @return O ID do cliente.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do cliente.
     * 
     * @param id O ID a ser definido.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do cliente.
     * 
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     * 
     * @param nome O nome a ser definido.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o e-mail do cliente.
     * 
     * @return O e-mail do cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do cliente.
     * 
     * @param email O e-mail a ser definido.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o telefone do cliente.
     * 
     * @return O telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do cliente.
     * 
     * @param telefone O telefone a ser definido.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna a senha do cliente.
     * 
     * @return A senha do cliente.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do cliente.
     * 
     * @param senha A senha a ser definida.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna o endereço do cliente.
     * 
     * @return O endereço do cliente.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente.
     * 
     * @param endereco O endereço a ser definido.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o CPF do cliente.
     * 
     * @return O CPF do cliente.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     * 
     * @param cpf O CPF a ser definido.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna a lista de animais associados ao cliente.
     * 
     * @return A lista de animais do cliente.
     */
    public List<Animal> getAnimais() {
        return animais;
    }

    /**
     * Define a lista de animais associados ao cliente.
     * 
     * @param animais A lista de animais a ser associada ao cliente.
     */
    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    /**
     * Adiciona um animal à lista de animais do cliente.
     * 
     * @param animal O animal a ser adicionado à lista.
     */
    public void addAnimal(Animal animal) {
        animais.add(animal);
    }

    /**
     * Remove um animal da lista de animais do cliente.
     * 
     * @param animal O animal a ser removido da lista.
     */
    public void removeAnimal(Animal animal) {
        animais.remove(animal);
    }

    /**
     * Retorna uma representação em string do objeto {@link Cliente}, incluindo seus principais atributos.
     * 
     * @return Uma string representando o cliente.
     */
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + ", endereco=" + endereco + ", cpf=" + cpf + "]";
    }

    /**
     * Calcula o código hash do cliente com base no seu ID.
     * 
     * @return O código hash do cliente.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Verifica se dois objetos são iguais, comparando o ID do cliente.
     * 
     * @param obj O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais (mesmo ID), caso contrário {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        return id != null && id.equals(other.id);
    }
}
