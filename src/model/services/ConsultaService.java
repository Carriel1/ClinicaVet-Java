package model.services;

import java.util.List;

import model.dao.ConsultaDao;
import model.entities.Consulta;

public class ConsultaService {
    private ConsultaDao dao;

    public ConsultaService(ConsultaDao dao) {
        this.dao = dao;
    }

    public void salvarOuAtualizar(Consulta consulta) {
        if (consulta.getId() == null) {
            dao.insert(consulta);
        } else {
            dao.update(consulta);
        }
    }
    
    public List<Consulta> findAll() {
        return dao.findAll();
    }
    
    public void deletar(Integer id) {
        dao.deleteById(id);
    }

    public Consulta buscarPorId(Integer id) {
        return dao.findById(id);
    }

    public List<Consulta> buscarTodos() {
        return dao.findAll();
    }

    public List<Consulta> buscarPorCliente(Integer clienteId) {
        return dao.findByClienteId(clienteId);
    }

    public List<Consulta> buscarPorVeterinario(Integer veterinarioId) {
        return dao.findByVeterinarioId(veterinarioId);
    }

    public List<Consulta> buscarPorStatus(String status) {
        return dao.findByStatus(status);
    }
}
