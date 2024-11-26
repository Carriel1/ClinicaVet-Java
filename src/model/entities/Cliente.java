package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String endereco; // Atributo para endereço
    private String cpf; // Atributo para CPF

    private List<Animal> animais = new ArrayList<>(); // Lista de animais registrados

    public Cliente() {
    }

    // Construtor com parâmetros, incluindo o CPF
    public Cliente(Integer id, String nome, String email, String telefone, String senha, String endereco, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() { 
        return senha;
    }

    public void setSenha(String senha) { 
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() { // Getter para o CPF
        return cpf;
    }

    public void setCpf(String cpf) { // Setter para o CPF
        this.cpf = cpf;
    }

    // Métodos para acessar e modificar a lista de animais
    public List<Animal> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }

    public void addAnimal(Animal animal) {
        animais.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animais.remove(animal);
    }

    // Método toString - Ajustado para não incluir dados sensíveis como a senha
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + ", endereco=" + endereco + ", cpf=" + cpf + "]";
    }

    // Métodos de comparação (equals e hashCode) para uso em coleções e validações
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

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
