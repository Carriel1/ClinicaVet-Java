package model.dao;

import model.entities.Relatorio;

import java.util.List;

/**
 * Interface que define as operações básicas de acesso a dados para a entidade Relatorio.
 * Contém os métodos necessários para realizar as operações de CRUD (Criar, Ler, Atualizar, Excluir)
 * no banco de dados relacionadas a relatórios de consultas realizadas pelos veterinários.
 * 
 * A interface é implementada por classes responsáveis pela execução real dessas operações no banco de dados.
 */
public interface RelatorioDao {

    /**
     * Insere um novo relatório no banco de dados.
     * 
     * @param relatorio O objeto Relatorio a ser inserido.
     */
    void insert(Relatorio relatorio);

    /**
     * Atualiza os dados de um relatório no banco de dados.
     * 
     * @param relatorio O objeto Relatorio com os dados a serem atualizados.
     */
    void update(Relatorio relatorio);

    /**
     * Exclui um relatório do banco de dados pelo seu ID.
     * 
     * @param id O ID do relatório a ser excluído.
     */
    void deleteById(Integer id);

    /**
     * Retorna um relatório do banco de dados pelo seu ID.
     * 
     * @param id O ID do relatório a ser encontrado.
     * @return O objeto Relatorio com os dados correspondentes ao ID informado.
     */
    Relatorio findById(Integer id);

    /**
     * Retorna uma lista de todos os relatórios do banco de dados.
     * 
     * @return Uma lista de objetos Relatorio.
     */
    List<Relatorio> findAll();

    /**
     * Retorna uma lista de relatórios relacionados a uma consulta específica.
     * 
     * @param consultaId O ID da consulta relacionada aos relatórios a serem encontrados.
     * @return Uma lista de objetos Relatorio associados ao ID de consulta informado.
     */
    List<Relatorio> findByConsultaId(Integer consultaId);

    /**
     * Retorna uma lista de relatórios relacionados a um veterinário específico.
     * 
     * @param veterinarioId O ID do veterinário relacionado aos relatórios a serem encontrados.
     * @return Uma lista de objetos Relatorio associados ao ID de veterinário informado.
     */
    List<Relatorio> findByVeterinarioId(Integer veterinarioId);

    /**
     * Retorna uma lista de todos os relatórios que possuem informações sobre veterinários.
     * 
     * @return Uma lista de objetos Relatorio com dados relacionados a veterinários.
     */
    List<Relatorio> findAllComVeterinario();
}
