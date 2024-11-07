package model.dao;

import java.util.List;
import model.entities.Cliente;

public interface ClienteDao {
    void insert(Cliente obj);
    void update(Cliente obj);
    void deleteById(Integer id);
    Cliente findById(Integer id);
    Cliente findByEmail(String email);
    List<Cliente> findAll();
}
