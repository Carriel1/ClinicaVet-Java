package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Consulta {
    private Integer id;
    private Cliente cliente;
    private Veterinario veterinario;
    private LocalDate data;
    private LocalTime hora;
    private String descricao;
    private String status;
    private String criadoPor;
    private Animal animal;
    private String clienteNome;
    private String animalNome;
    private Date dataSolicitacao;
    private Integer clienteId;


    public Consulta() {}

    // Construtor com parâmetros
    public Consulta(Integer id, Cliente cliente, Veterinario veterinario, LocalDate data, 
                    LocalTime hora, String descricao, String status, String criadoPor, Animal animal) {
        this.id = id;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.status = status;
        this.criadoPor = criadoPor;
        this.animal = animal;
    }

    // Getters e setters
    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("clienteId não pode ser null");
        }
        this.clienteId = clienteId;
    }
    
    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getAnimalNome() {
        return animalNome;
    }

    public void setAnimalNome(String animalNome) {
        this.animalNome = animalNome;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
    
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

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    // Método toString ajustado
    @Override
    public String toString() {
        if (animal != null) {
            return "Consulta ID: " + id + ", Descrição: " + descricao + ", Animal: " + animal.animalInfo();
        } else {
            return "Consulta ID: " + id + ", Descrição: " + descricao + ", Animal: Sem animal associado";
        }
    }
}

