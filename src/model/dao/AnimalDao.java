package model.dao;

import java.util.List;

import model.entities.Animal;

public interface AnimalDao {
    void insert(Animal obj);
    void update(Animal obj);
    void deleteById(Integer id);
    Animal findById(Integer id);
    List<Animal> findAll();
    List<Animal> findByClienteId(Integer clienteId);
    List<Animal> findAnimaisByClienteId(Integer clienteId);
    void deleteByClienteId(Integer clienteId);  
    List<Animal> buscarPorClienteId(int clienteId); 
}
