package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Veterinario implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;

    // Construtor vazio
    public Veterinario() {
    }

    // Construtor com todos os atributos
    public Veterinario(Integer id, String nome, String email, String telefone, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
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

    // toString para facilitar a visualização
    @Override
    public String toString() {
        return "Veterinario [id=" + id + ", nome=" + nome + ", email=" + email + 
               ", telefone=" + telefone + ", senha=" + senha + "]";
    }

    // equals e hashCode para comparações
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Veterinario other = (Veterinario) obj;
        return Objects.equals(id, other.id);
    }
}
