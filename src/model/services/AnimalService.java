package model.services;

import java.sql.Connection;
import java.util.List;

import db.DbException;
import model.dao.AnimalDao;
import model.dao.DaoFactory;
import model.dao.impl.AnimalDaoJDBC;
import model.entities.Animal;

/**
 * Serviço responsável pela lógica de negócios relacionada a {@link Animal}.
 * Contém métodos para inserir, atualizar, excluir e consultar animais,
 * delegando a interação com o banco de dados para o DAO apropriado.
 */
public class AnimalService {

    private AnimalDao dao;

    /**
     * Construtor que inicializa o serviço com uma conexão de banco de dados fornecida.
     * 
     * @param conn A conexão com o banco de dados.
     */
    public AnimalService(Connection conn) {
        dao = new AnimalDaoJDBC(conn);  
    }
    
    /**
     * Construtor que inicializa o serviço com o DAO de animais padrão.
     */
    public AnimalService() {
        this.dao = DaoFactory.createAnimalDao(); 
    }
    
    /**
     * Insere um novo animal no banco de dados.
     * 
     * @param animal O objeto {@link Animal} a ser inserido.
     * @throws IllegalArgumentException Se o animal ou seu cliente forem nulos.
     * @throws DbException Se ocorrer um erro no banco de dados durante a inserção.
     */
    public void insert(Animal animal) {
        if (animal == null || animal.getCliente() == null) {
            throw new IllegalArgumentException("Animal e Cliente não podem ser nulos.");
        }
        try {
            dao.insert(animal);
        } catch (DbException e) {
            throw new DbException("Erro ao inserir o animal: " + e.getMessage());
        }
    }

    /**
     * Realiza a operação de salvar ou atualizar um animal, dependendo de seu ID.
     * 
     * @param animal O objeto {@link Animal} a ser salvo ou atualizado.
     * @throws DbException Se ocorrer um erro no banco de dados durante a operação.
     */
    public void saveOrUpdate(Animal animal) {
        if (animal.getId() == null) {
            dao.insert(animal); 
        } else {
            dao.update(animal); 
        }
    }
    
    /**
     * Atualiza os dados de um animal existente no banco de dados.
     * 
     * @param animal O objeto {@link Animal} a ser atualizado.
     * @throws IllegalArgumentException Se o animal for nulo.
     * @throws DbException Se ocorrer um erro no banco de dados durante a atualização.
     */
    public void update(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal não pode ser nulo.");
        }
        try {
            dao.update(animal);
        } catch (DbException e) {
            throw new DbException("Erro ao atualizar o animal: " + e.getMessage());
        }
    }

    /**
     * Exclui um animal do banco de dados, dado o seu ID.
     * 
     * @param id O ID do animal a ser excluído.
     * @throws IllegalArgumentException Se o ID for nulo.
     * @throws DbException Se ocorrer um erro no banco de dados durante a exclusão.
     */
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        try {
            dao.deleteById(id);
        } catch (DbException e) {
            throw new DbException("Erro ao deletar o animal: " + e.getMessage());
        }
    }

    /**
     * Encontra um animal no banco de dados pelo seu ID.
     * 
     * @param id O ID do animal a ser encontrado.
     * @return O objeto {@link Animal} encontrado, ou {@code null} se não encontrado.
     * @throws IllegalArgumentException Se o ID for nulo.
     * @throws DbException Se ocorrer um erro no banco de dados ou o animal não for encontrado.
     */
    public Animal findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        Animal animal = dao.findById(id);
        if (animal == null) {
            throw new DbException("Animal não encontrado com o ID: " + id);
        }
        return animal;
    }

    /**
     * Encontra todos os animais no banco de dados.
     * 
     * @return Uma lista com todos os animais encontrados.
     * @throws DbException Se ocorrer um erro no banco de dados durante a busca.
     */
    public List<Animal> findAll() {
        try {
            return dao.findAll();
        } catch (DbException e) {
            throw new DbException("Erro ao buscar todos os animais: " + e.getMessage());
        }
    }

    /**
     * Encontra todos os animais de um cliente específico, dado o seu ID.
     * 
     * @param clienteId O ID do cliente cujos animais serão encontrados.
     * @return Uma lista com os animais do cliente.
     * @throws IllegalArgumentException Se o ID do cliente for nulo.
     * @throws DbException Se ocorrer um erro no banco de dados durante a busca.
     */
    public List<Animal> findByClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo.");
        }
        try {
            return dao.findByClienteId(clienteId);
        } catch (DbException e) {
            throw new DbException("Erro ao buscar animais para o cliente com ID: " + clienteId);
        }
    }

    /**
     * Encontra todos os animais de um cliente, utilizando um método alternativo.
     * 
     * @param clienteId O ID do cliente cujos animais serão encontrados.
     * @return Uma lista com os animais do cliente.
     */
    public List<Animal> findAnimaisByClienteId(Integer clienteId) {
        return findByClienteId(clienteId);
    }
    
    /**
     * Encontra todos os animais de um cliente dado seu ID, utilizando um método específico do DAO.
     * 
     * @param clienteId O ID do cliente cujos animais serão encontrados.
     * @return Uma lista com os animais do cliente.
     */
    public List<Animal> buscarPorClienteId(int clienteId) {
        return dao.buscarPorClienteId(clienteId);
    }
    
    /**
     * Exclui todos os animais de um cliente dado seu ID.
     * 
     * @param clienteId O ID do cliente cujos animais serão excluídos.
     * @throws IllegalArgumentException Se o ID do cliente for nulo.
     * @throws DbException Se ocorrer um erro no banco de dados durante a exclusão.
     */
    public void deleteByClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo.");
        }
        try {
            dao.deleteByClienteId(clienteId);
        } catch (DbException e) {
            throw new DbException("Erro ao deletar animais do cliente com ID: " + clienteId);
        }
    }
}
