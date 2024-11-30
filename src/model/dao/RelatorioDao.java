package model.dao;

import model.entities.Relatorio;

import java.util.List;

public interface RelatorioDao {
    void insert(Relatorio relatorio);
    void update(Relatorio relatorio);
    void deleteById(Integer id);
    Relatorio findById(Integer id);
    List<Relatorio> findAll();
    List<Relatorio> findByConsultaId(Integer consultaId);
    List<Relatorio> findByVeterinarioId(Integer veterinarioId);
    List<Relatorio> findAllComVeterinario();
}
