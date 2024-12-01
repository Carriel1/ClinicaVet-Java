package model.dao;

import java.util.List;
import model.entities.Funcionario;

/**
 * Interface que define as operações básicas de acesso a dados para a entidade Funcionario.
 * Contém os métodos necessários para realizar as operações de CRUD (Criar, Ler, Atualizar, Excluir)
 * no banco de dados relacionadas a funcionários.
 * 
 * A interface é implementada por classes responsáveis pela execução real dessas operações no banco de dados.
 */
public interface FuncionarioDao {

    /**
     * Insere um novo funcionário no banco de dados.
     * 
     * @param obj O objeto Funcionario a ser inserido.
     */
    void insert(Funcionario obj);

    /**
     * Atualiza os dados de um funcionário no banco de dados.
     * 
     * @param obj O objeto Funcionario com os dados a serem atualizados.
     */
    void update(Funcionario obj);

    /**
     * Exclui um funcionário do banco de dados pelo seu ID.
     * 
     * @param id O ID do funcionário a ser excluído.
     */
    void deleteById(Integer id);

    /**
     * Retorna um funcionário do banco de dados pelo seu ID.
     * 
     * @param id O ID do funcionário a ser encontrado.
     * @return O objeto Funcionario com os dados correspondentes ao ID informado.
     */
    Funcionario findById(Integer id);

    /**
     * Retorna uma lista de todos os funcionários do banco de dados.
     * 
     * @return Uma lista de objetos Funcionario.
     */
    List<Funcionario> findAll();

    /**
     * Retorna um funcionário do banco de dados pelo seu nome de usuário.
     * 
     * @param username O nome de usuário do funcionário.
     * @return O objeto Funcionario correspondente ao nome de usuário informado.
     */
    Funcionario findByUsername(String username);
}
