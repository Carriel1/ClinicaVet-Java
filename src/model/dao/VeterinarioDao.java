package model.dao;

import java.util.List;

import model.entities.Veterinario;

public interface VeterinarioDao {
    void insert(Veterinario obj);
    void update(Veterinario obj);
    void deleteById(Integer id);
    Veterinario findById(Integer id);
    Veterinario findByEmail(String email);
    Veterinario findByCpf(String cpf);
    Veterinario findByUsername(String username); 
    List<Veterinario> findAll();
}
