package model.services;

import model.dao.DaoFactory;
import model.dao.RelatorioDao;
import model.entities.Relatorio;

import java.util.List;

public class RelatorioService {
    private RelatorioDao relatorioDao = DaoFactory.createRelatorioDao();

    public void insert(Relatorio relatorio) {
        relatorioDao.insert(relatorio);
    }

    public void update(Relatorio relatorio) {
        relatorioDao.update(relatorio);
    }

    public void deleteById(Integer id) {
        relatorioDao.deleteById(id);
    }

    public Relatorio findById(Integer id) {
        return relatorioDao.findById(id);
    }

    public List<Relatorio> findAll() {
        return relatorioDao.findAll();
    }
}
