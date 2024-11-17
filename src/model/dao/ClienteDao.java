package model.dao;

import java.util.List;
import model.entities.Cliente;

public interface ClienteDao {
    List<Cliente> findAll();
    Cliente findById(Integer id);
    Cliente findByUsername(String username);
    void insert(Cliente cliente);
    void update(Cliente cliente);
    void deleteById(Integer id);
}
