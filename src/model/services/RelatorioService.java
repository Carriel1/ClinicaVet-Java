package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.RelatorioDao;
import model.dao.VeterinarioDao;
import model.entities.Relatorio;
import model.entities.Veterinario;

public class RelatorioService {
    private RelatorioDao relatorioDao = DaoFactory.createRelatorioDao();
    private VeterinarioDao veterinarioDao = DaoFactory.createVeterinarioDao(); // Inicialização do VeterinarioDao
    
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

    public List<Relatorio> findAllRelatoriosComVeterinario() {
        List<Relatorio> relatorios = relatorioDao.findAll(); // Supondo que você tenha um DAO

        // Asegure-se de que cada relatorio tenha seu veterinarioResponsavel preenchido
        for (Relatorio relatorio : relatorios) {
            if (relatorio.getVeterinarioResponsavel() == null) {
                // Atribuir o veterinário responsável aqui, se necessário
                Veterinario veterinario = veterinarioDao.findById(relatorio.getVeterinario().getId()); // Usando veterinarioDao
                relatorio.setVeterinarioResponsavel(veterinario);
            }
        }

        return relatorios;
    }
}

