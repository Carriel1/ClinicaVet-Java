package model.dao;

import java.util.List;
import model.entities.Cliente;

/**
 * Interface que define as operações de acesso a dados (DAO) para a entidade Cliente.
 * 
 * Esta interface declara os métodos necessários para manipulação de registros de clientes
 * no banco de dados, como inserção, atualização, exclusão e consulta de dados.
 */
public interface ClienteDao {

    /**
     * Retorna todos os clientes cadastrados no banco de dados.
     * 
     * @return Uma lista com todos os clientes.
     */
    List<Cliente> findAll();

    /**
     * Busca um cliente no banco de dados pelo seu ID.
     * 
     * @param id O ID do cliente a ser buscado.
     * @return O cliente encontrado ou null, caso não seja encontrado.
     */
    Cliente findById(Integer id);

    /**
     * Busca um cliente no banco de dados pelo seu nome de usuário.
     * 
     * @param username O nome de usuário do cliente a ser buscado.
     * @return O cliente encontrado ou null, caso não seja encontrado.
     */
    Cliente findByUsername(String username);

    /**
     * Insere um novo cliente no banco de dados.
     * 
     * @param cliente O objeto Cliente a ser inserido.
     */
    void insert(Cliente cliente);

    /**
     * Atualiza as informações de um cliente no banco de dados.
     * 
     * @param cliente O objeto Cliente com as informações atualizadas.
     */
    void update(Cliente cliente);

    /**
     * Exclui um cliente do banco de dados a partir do seu ID.
     * 
     * @param id O ID do cliente a ser excluído.
     */
    void deleteById(Integer id);
}
