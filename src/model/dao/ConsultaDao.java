package model.dao;

import model.entities.Consulta;
import java.util.List;

/**
 * Interface que define as operações de acesso a dados (DAO) para a entidade Consulta.
 * 
 * Esta interface declara os métodos necessários para manipulação de registros de consultas
 * no banco de dados, como inserção, atualização, exclusão e consulta de dados.
 */
public interface ConsultaDao {

    /**
     * Insere uma nova consulta no banco de dados.
     * 
     * @param consulta O objeto Consulta a ser inserido.
     */
    void insert(Consulta consulta);

    /**
     * Atualiza as informações de uma consulta existente no banco de dados.
     * 
     * @param consulta O objeto Consulta com as informações atualizadas.
     */
    void update(Consulta consulta);

    /**
     * Exclui uma consulta do banco de dados a partir do seu ID.
     * 
     * @param id O ID da consulta a ser excluída.
     */
    void deleteById(Integer id);

    /**
     * Busca uma consulta no banco de dados pelo seu ID.
     * 
     * @param id O ID da consulta a ser buscada.
     * @return A consulta encontrada ou null, caso não seja encontrada.
     */
    Consulta findById(Integer id);

    /**
     * Atualiza o status de uma consulta no banco de dados.
     * 
     * @param consulta O objeto Consulta cujo status será atualizado.
     * @param status O novo status da consulta.
     */
    void updateStatus(Consulta consulta, String status);

    /**
     * Retorna todas as consultas registradas no banco de dados.
     * 
     * @return Uma lista com todas as consultas.
     */
    List<Consulta> findAll();

    /**
     * Retorna todas as consultas pendentes no banco de dados.
     * 
     * @return Uma lista com as consultas pendentes.
     */
    List<Consulta> findAllPendentes();

    /**
     * Retorna todas as consultas requisitadas no banco de dados.
     * 
     * @return Uma lista com as consultas requisitadas.
     */
    List<Consulta> findAllRequisitadas();

    /**
     * Retorna todas as consultas de um cliente específico, a partir do seu ID.
     * 
     * @param clienteId O ID do cliente cujas consultas serão buscadas.
     * @return Uma lista com as consultas do cliente.
     */
    List<Consulta> findByClienteId(Integer clienteId);

    /**
     * Retorna todas as consultas de um veterinário específico, a partir do seu ID.
     * 
     * @param veterinarioId O ID do veterinário cujas consultas serão buscadas.
     * @return Uma lista com as consultas do veterinário.
     */
    List<Consulta> findByVeterinarioId(Integer veterinarioId);

    /**
     * Retorna todas as consultas com um status específico.
     * 
     * @param status O status das consultas a serem buscadas.
     * @return Uma lista com as consultas com o status informado.
     */
    List<Consulta> findByStatus(String status);
}
