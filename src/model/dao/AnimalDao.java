package model.dao;

import java.util.List;
import model.entities.Animal;

/**
 * Interface que define as operações de acesso a dados (DAO) para a entidade Animal.
 * 
 * Esta interface declara os métodos necessários para manipulação de registros de animais
 * no banco de dados, como inserção, atualização, exclusão e consulta de dados.
 */
public interface AnimalDao {

    /**
     * Insere um novo animal no banco de dados.
     * 
     * @param obj O objeto Animal a ser inserido.
     */
    void insert(Animal obj);

    /**
     * Atualiza as informações de um animal no banco de dados.
     * 
     * @param obj O objeto Animal com as informações atualizadas.
     */
    void update(Animal obj);

    /**
     * Exclui um animal do banco de dados a partir do seu ID.
     * 
     * @param id O ID do animal a ser excluído.
     */
    void deleteById(Integer id);

    /**
     * Busca um animal no banco de dados pelo seu ID.
     * 
     * @param id O ID do animal a ser buscado.
     * @return O animal encontrado ou null, caso não seja encontrado.
     */
    Animal findById(Integer id);

    /**
     * Retorna todos os animais cadastrados no banco de dados.
     * 
     * @return Uma lista com todos os animais.
     */
    List<Animal> findAll();

    /**
     * Retorna todos os animais de um cliente específico, identificado pelo seu ID.
     * 
     * @param clienteId O ID do cliente cujos animais serão retornados.
     * @return Uma lista com os animais pertencentes ao cliente.
     */
    List<Animal> findByClienteId(Integer clienteId);

    /**
     * Retorna todos os animais de um cliente específico, identificado pelo seu ID.
     * 
     * @param clienteId O ID do cliente cujos animais serão retornados.
     * @return Uma lista com os animais pertencentes ao cliente.
     */
    List<Animal> findAnimaisByClienteId(Integer clienteId);

    /**
     * Exclui todos os animais de um cliente específico, identificado pelo seu ID.
     * 
     * @param clienteId O ID do cliente cujos animais serão excluídos.
     */
    void deleteByClienteId(Integer clienteId);  

    /**
     * Busca todos os animais de um cliente específico, identificado pelo seu ID.
     * 
     * @param clienteId O ID do cliente cujos animais serão buscados.
     * @return Uma lista com os animais pertencentes ao cliente.
     */
    List<Animal> buscarPorClienteId(int clienteId);
}
