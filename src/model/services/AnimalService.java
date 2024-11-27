package model.services;

import java.sql.Connection;
import java.util.List;

import db.DbException;
import model.dao.AnimalDao;
import model.dao.DaoFactory;
import model.dao.impl.AnimalDaoJDBC;
import model.entities.Animal;

public class AnimalService {

    private AnimalDao dao;

    public AnimalService(Connection conn) {
        dao = new AnimalDaoJDBC(conn);  // Inicializa o DAO com a conexão fornecida
    }
    
    public AnimalService() {
        this.dao = DaoFactory.createAnimalDao(); // Cria o DAO para operações de banco
    }
    
    // Inserir um novo animal
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

    
    public void saveOrUpdate(Animal animal) {
        if (animal.getId() == null) {
            dao.insert(animal); // Insere se o ID for nulo
        } else {
            dao.update(animal); // Atualiza se já tiver um ID
        }
    }
    
    // Atualizar um animal existente
    public void update(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal não pode ser nulo.");
        }
        try {
            dao.update(animal);  // Chama o método de atualização do DAO
        } catch (DbException e) {
            throw new DbException("Erro ao atualizar o animal: " + e.getMessage());
        }
    }

    // Deletar um animal pelo ID
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        try {
            dao.deleteById(id);  // Chama o método de exclusão do DAO
        } catch (DbException e) {
            throw new DbException("Erro ao deletar o animal: " + e.getMessage());
        }
    }

    // Encontrar um animal pelo ID
    public Animal findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        Animal animal = dao.findById(id);  // Chama o método de busca por ID do DAO
        if (animal == null) {
            throw new DbException("Animal não encontrado com o ID: " + id);
        }
        return animal;
    }

    // Encontrar todos os animais
    public List<Animal> findAll() {
        try {
            return dao.findAll();  // Chama o método de busca de todos os animais
        } catch (DbException e) {
            throw new DbException("Erro ao buscar todos os animais: " + e.getMessage());
        }
    }

    // Encontrar todos os animais de um cliente
    public List<Animal> findByClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo.");
        }
        try {
            return dao.findByClienteId(clienteId);  // Chama o método de busca de animais por cliente
        } catch (DbException e) {
            throw new DbException("Erro ao buscar animais para o cliente com ID: " + clienteId);
        }
    }

    // Encontrar todos os animais de um cliente (mesmo método de findByClienteId, só renomeado)
    public List<Animal> findAnimaisByClienteId(Integer clienteId) {
        return findByClienteId(clienteId);  // Chama o método já implementado
    }

    // Excluir todos os animais de um cliente
    public void deleteByClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo.");
        }
        try {
            dao.deleteByClienteId(clienteId);  // Chama o método de exclusão de animais por cliente
        } catch (DbException e) {
            throw new DbException("Erro ao deletar animais do cliente com ID: " + clienteId);
        }
    }
}
