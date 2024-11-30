package model.entities;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Relatorio {
    private Integer id;
    private Consulta consulta;  
    private Veterinario veterinario;  
    private String descricao;   
    private String diagnostico;  
    private String recomendacao;  
    private LocalDate dataCriacao;  
    private Veterinario veterinarioResponsavel;

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
    
    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public StringProperty getRelatorioProperty() {
        return new SimpleStringProperty(descricao);
    }

    public StringProperty getVeterinarioResponsavelProperty() {
        return new SimpleStringProperty(veterinarioResponsavel.getNome());
    }
    
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
