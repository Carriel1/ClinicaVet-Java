package model.dao;

import java.util.List;
import model.entities.Veterinario;

public interface VeterinarioDao {
    void insert(Veterinario obj);
    void update(Veterinario obj);
    void deleteById(Integer id);
    Veterinario findById(Integer id);
    Veterinario findByEmail(String email);
    List<Veterinario> findAll();
}
