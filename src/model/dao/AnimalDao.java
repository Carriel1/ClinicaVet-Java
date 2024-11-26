package model.dao;

import model.entities.Animal;
import java.util.List;

public interface AnimalDao {
    void insert(Animal obj);
    void update(Animal obj);
    void deleteById(Integer id);
    Animal findById(Integer id);
    List<Animal> findAll();
    List<Animal> findByClienteId(Integer clienteId);  // Método já implementado
    // Caso a interface realmente tenha findAnimaisByClienteId, renomeie o método abaixo:
    List<Animal> findAnimaisByClienteId(Integer clienteId); // Assumindo que esse é o nome correto
}
