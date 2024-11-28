package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {

    private Integer id;
    private Cliente cliente;
    private Veterinario veterinario;
    private LocalDate data;
    private LocalTime hora;
    private String descricao;
    private String status;
    private String criadoPor; // Novo campo

    public Consulta () {
    	
    }
    
    // Construtor com todos os parâmetros, incluindo o novo campo 'criadoPor'
    public Consulta(Integer id, Cliente cliente, Veterinario veterinario, LocalDate data, LocalTime hora, String descricao, String status, String criadoPor) {
        this.id = id;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.status = status;
        this.criadoPor = criadoPor; // Inicializando o campo 'criadoPor'
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }
}

