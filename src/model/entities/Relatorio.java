package model.entities;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representa um relatório de consulta veterinária, incluindo a descrição do atendimento, diagnóstico, recomendações
 * e outras informações importantes sobre a consulta realizada.
 */
public class Relatorio {
    private Integer id;
    private Consulta consulta;  
    private Veterinario veterinario;  
    private String descricao;   
    private String diagnostico;  
    private String recomendacao;  
    private LocalDate dataCriacao;  
    private Veterinario veterinarioResponsavel;

    /**
     * Construtor padrão para criar uma instância de {@link Relatorio}.
     */
    public Relatorio() {}

    /**
     * Construtor para criar uma instância de {@link Relatorio} com os valores fornecidos.
     * 
     * @param id O ID do relatório.
     * @param consulta A consulta associada a este relatório.
     * @param veterinario O veterinário responsável pela consulta.
     * @param descricao A descrição do relatório.
     * @param diagnostico O diagnóstico fornecido pelo veterinário.
     * @param recomendacao As recomendações do veterinário.
     * @param dataCriacao A data de criação do relatório.
     */
    public Relatorio(Integer id, Consulta consulta, Veterinario veterinario, String descricao, 
                     String diagnostico, String recomendacao, LocalDate dataCriacao) {
        this.id = id;
        this.consulta = consulta;
        this.veterinario = veterinario;
        this.descricao = descricao;
        this.diagnostico = diagnostico;
        this.recomendacao = recomendacao;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters

    /**
     * Retorna o veterinário responsável pela criação do relatório.
     * 
     * @return O veterinário responsável.
     */
    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    /**
     * Define o veterinário responsável pela criação do relatório.
     * 
     * @param veterinarioResponsavel O veterinário responsável.
     */
    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    /**
     * Retorna a descrição do relatório como um {@link StringProperty}.
     * 
     * @return A descrição do relatório.
     */
    public StringProperty getRelatorioProperty() {
        return new SimpleStringProperty(descricao);
    }

    /**
     * Retorna o nome do veterinário responsável pelo relatório como um {@link StringProperty}.
     * 
     * @return O nome do veterinário responsável.
     */
    public StringProperty getVeterinarioResponsavelProperty() {
        return new SimpleStringProperty(veterinarioResponsavel.getNome());
    }

    /**
     * Retorna o ID do relatório.
     * 
     * @return O ID do relatório.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do relatório.
     * 
     * @param id O ID do relatório.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna a consulta associada a este relatório.
     * 
     * @return A consulta associada ao relatório.
     */
    public Consulta getConsulta() {
        return consulta;
    }

    /**
     * Define a consulta associada a este relatório.
     * 
     * @param consulta A consulta associada ao relatório.
     */
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    /**
     * Retorna o veterinário responsável pela consulta e, portanto, pelo relatório.
     * 
     * @return O veterinário responsável pela consulta.
     */
    public Veterinario getVeterinario() {
        return veterinario;
    }

    /**
     * Define o veterinário responsável pela consulta.
     * 
     * @param veterinario O veterinário responsável pela consulta.
     */
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    /**
     * Retorna a descrição do relatório.
     * 
     * @return A descrição do relatório.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do relatório.
     * 
     * @param descricao A descrição do relatório.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o diagnóstico fornecido pelo veterinário.
     * 
     * @return O diagnóstico do veterinário.
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * Define o diagnóstico fornecido pelo veterinário.
     * 
     * @param diagnostico O diagnóstico do veterinário.
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * Retorna as recomendações fornecidas pelo veterinário.
     * 
     * @return As recomendações do veterinário.
     */
    public String getRecomendacao() {
        return recomendacao;
    }

    /**
     * Define as recomendações fornecidas pelo veterinário.
     * 
     * @param recomendacao As recomendações do veterinário.
     */
    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }

    /**
     * Retorna a data de criação do relatório.
     * 
     * @return A data de criação do relatório.
     */
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Define a data de criação do relatório.
     * 
     * @param dataCriacao A data de criação do relatório.
     */
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
