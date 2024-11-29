package model.dao;

import model.entities.Consulta;

import java.util.List;

public interface ConsultaDao {

    void insert(Consulta consulta);

    void update(Consulta consulta);

    void deleteById(Integer id);

    Consulta findById(Integer id);

    List<Consulta> findAll();

    List<Consulta> findByClienteId(Integer clienteId);

    List<Consulta> findByVeterinarioId(Integer veterinarioId);

    List<Consulta> findByStatus(String status);
}
