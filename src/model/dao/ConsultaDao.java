package model.dao;

import model.entities.Consulta;

import java.util.List;

public interface ConsultaDao {

    void insert(Consulta consulta);

    void update(Consulta consulta);

    void deleteById(Integer id);

    Consulta findById(Integer id);

    void updateStatus(Consulta consulta, String status);

    List<Consulta> findAll();

    List<Consulta> findAllPendentes();
    
    List<Consulta> findAllRequisitadas();
    
    List<Consulta> findByClienteId(Integer clienteId);

    List<Consulta> findByVeterinarioId(Integer veterinarioId);

    List<Consulta> findByStatus(String status);
}
