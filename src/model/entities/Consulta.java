package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * Representa uma consulta veterinária realizada por um cliente para um animal.
 * A consulta inclui informações sobre o cliente, veterinário, animal, data e hora da consulta, descrição, status e quem criou a consulta.
 */
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

    /**
     * Construtor padrão para criar uma instância de {@link Consulta}.
     */
    public Consulta() {}

    /**
     * Construtor para criar uma instância de {@link Consulta} com os valores fornecidos.
     * 
     * @param id O ID da consulta.
     * @param cliente O cliente que está realizando a consulta.
     * @param veterinario O veterinário responsável pela consulta.
     * @param data A data da consulta.
     * @param hora A hora da consulta.
     * @param descricao A descrição da consulta.
     * @param status O status atual da consulta (e.g., agendada, realizada).
     * @param criadoPor O nome de quem criou a consulta.
     * @param animal O animal que será atendido.
     */
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

    // Getters e Setters

    /**
     * Retorna o ID do cliente associado à consulta.
     * 
     * @return O ID do cliente.
     */
    public Integer getClienteId() {
        return clienteId;
    }

    /**
     * Define o ID do cliente associado à consulta.
     * 
     * @param clienteId O ID do cliente.
     * @throws IllegalArgumentException Se o clienteId for nulo.
     */
    public void setClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("clienteId não pode ser null");
        }
        this.clienteId = clienteId;
    }

    /**
     * Retorna o nome do cliente associado à consulta.
     * 
     * @return O nome do cliente.
     */
    public String getClienteNome() {
        return clienteNome;
    }

    /**
     * Define o nome do cliente associado à consulta.
     * 
     * @param clienteNome O nome do cliente.
     */
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    /**
     * Retorna o nome do animal associado à consulta.
     * 
     * @return O nome do animal.
     */
    public String getAnimalNome() {
        return animalNome;
    }

    /**
     * Define o nome do animal associado à consulta.
     * 
     * @param animalNome O nome do animal.
     */
    public void setAnimalNome(String animalNome) {
        this.animalNome = animalNome;
    }

    /**
     * Retorna a data de solicitação da consulta.
     * 
     * @return A data de solicitação.
     */
    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    /**
     * Define a data de solicitação da consulta.
     * 
     * @param dataSolicitacao A data de solicitação.
     */
    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    /**
     * Retorna o ID da consulta.
     * 
     * @return O ID da consulta.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da consulta.
     * 
     * @param id O ID da consulta.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o cliente associado à consulta.
     * 
     * @return O cliente da consulta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente associado à consulta.
     * 
     * @param cliente O cliente da consulta.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna o veterinário responsável pela consulta.
     * 
     * @return O veterinário responsável.
     */
    public Veterinario getVeterinario() {
        return veterinario;
    }

    /**
     * Define o veterinário responsável pela consulta.
     * 
     * @param veterinario O veterinário responsável.
     */
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    /**
     * Retorna a data da consulta.
     * 
     * @return A data da consulta.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data da consulta.
     * 
     * @param data A data da consulta.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Retorna a hora da consulta.
     * 
     * @return A hora da consulta.
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Define a hora da consulta.
     * 
     * @param hora A hora da consulta.
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    /**
     * Retorna a descrição da consulta.
     * 
     * @return A descrição da consulta.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da consulta.
     * 
     * @param descricao A descrição da consulta.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o status da consulta (e.g., agendada, realizada).
     * 
     * @return O status da consulta.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status da consulta.
     * 
     * @param status O status da consulta.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retorna quem criou a consulta.
     * 
     * @return O nome de quem criou a consulta.
     */
    public String getCriadoPor() {
        return criadoPor;
    }

    /**
     * Define quem criou a consulta.
     * 
     * @param criadoPor O nome de quem criou a consulta.
     */
    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    /**
     * Retorna o animal associado à consulta.
     * 
     * @return O animal da consulta.
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * Define o animal associado à consulta.
     * 
     * @param animal O animal da consulta.
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    /**
     * Retorna uma representação em string do objeto {@link Consulta}.
     * Se o animal estiver associado à consulta, sua informação será exibida.
     * Caso contrário, será exibido "Sem animal associado".
     * 
     * @return Uma string representando a consulta.
     */
    @Override
    public String toString() {
        if (animal != null) {
            return "Consulta ID: " + id + ", Descrição: " + descricao + ", Animal: " + animal.animalInfo();
        } else {
            return "Consulta ID: " + id + ", Descrição: " + descricao + ", Animal: Sem animal associado";
        }
    }
}
