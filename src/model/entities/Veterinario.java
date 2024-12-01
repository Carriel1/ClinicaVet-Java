package model.entities;

import java.io.Serializable;

/**
 * Representa um veterinário no sistema. Contém informações pessoais como nome, CPF, email, telefone e senha.
 * Além disso, esta classe é serializável para permitir o armazenamento e transferência de dados.
 */
public class Veterinario implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;

    /**
     * Construtor padrão para criar uma instância de {@link Veterinario}.
     */
    public Veterinario() {
    }

    /**
     * Construtor para criar uma instância de {@link Veterinario} com os valores fornecidos.
     * 
     * @param id O ID do veterinário.
     * @param nome O nome do veterinário.
     * @param cpf O CPF do veterinário.
     * @param email O email do veterinário.
     * @param telefone O telefone do veterinário.
     * @param senha A senha do veterinário.
     */
    public Veterinario(Integer id, String nome, String cpf, String email, String telefone, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    /**
     * Construtor para criar uma instância de {@link Veterinario} com apenas o nome fornecido.
     * Este construtor é útil quando o nome do veterinário é conhecido, mas as outras informações ainda não.
     * 
     * @param nome O nome do veterinário.
     */
    public Veterinario(String nome) {
        this.nome = nome;
        this.id = null;
        this.cpf = null;
        this.email = null;
        this.telefone = null;
        this.senha = null;
    }

    // Getters e Setters

    /**
     * Retorna o ID do veterinário.
     * 
     * @return O ID do veterinário.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do veterinário.
     * 
     * @param id O ID do veterinário.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do veterinário.
     * 
     * @return O nome do veterinário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do veterinário.
     * 
     * @param nome O nome do veterinário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF do veterinário.
     * 
     * @return O CPF do veterinário.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do veterinário.
     * 
     * @param cpf O CPF do veterinário.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o email do veterinário.
     * 
     * @return O email do veterinário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do veterinário.
     * 
     * @param email O email do veterinário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o telefone do veterinário.
     * 
     * @return O telefone do veterinário.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do veterinário.
     * 
     * @param telefone O telefone do veterinário.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna a senha do veterinário.
     * 
     * @return A senha do veterinário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do veterinário.
     * 
     * @param senha A senha do veterinário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna uma representação em string do veterinário, incluindo seu ID, nome, CPF, email e telefone.
     * 
     * @return Uma string representando o veterinário.
     */
    @Override
    public String toString() {
        return "Veterinario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", telefone=" + telefone + "]";
    }

    /**
     * Gera um código hash único para o veterinário com base no seu ID. Usado para comparações rápidas de objetos.
     * 
     * @return O código hash do veterinário.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Compara este veterinário com outro objeto. Dois veterinários são considerados iguais se tiverem o mesmo ID.
     * 
     * @param obj O objeto a ser comparado.
     * @return {@code true} se os veterinários forem iguais (mesmo ID), caso contrário {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Veterinario other = (Veterinario) obj;
        return id != null && id.equals(other.id);
    }
}
