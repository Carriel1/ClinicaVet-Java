package model.entities;

import java.time.LocalDate;

public class Relatorio {
    private Integer id;
    private Consulta consulta;  // Consulta relacionada ao relatório
    private Veterinario veterinario;  // Veterinário que preenche o relatório
    private String descricao;  // Descrição do que foi realizado no atendimento
    private String diagnostico;  // Diagnóstico do animal
    private String recomendacao;  // Recomendação de tratamento ou cuidados
    private LocalDate dataCriacao;  // Data de criação do relatório

    // Construtores
    public Relatorio() {}

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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}