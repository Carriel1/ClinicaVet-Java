package model.entities;

import java.io.Serializable;

public class Veterinario implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;

    // Construtor padrão
    public Veterinario() {
    }

    // Construtor com parâmetros
    public Veterinario(Integer id, String nome, String cpf, String email, String telefone, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
    
    // Novo construtor apenas com o nome
    public Veterinario(String nome) {
        this.nome = nome;
        this.id = null;
        this.cpf = null;
        this.email = null;
        this.telefone = null;
        this.senha = null;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    @Override
    public String toString() {
        return "Veterinario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", telefone=" + telefone + "]";
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
        Veterinario other = (Veterinario) obj;
        return id != null && id.equals(other.id);
    }
}
