package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.RelatorioDao;
import model.dao.VeterinarioDao;
import model.entities.Relatorio;
import model.entities.Veterinario;

/**
 * Serviço responsável pela lógica de negócios relacionada aos relatórios de consultas.
 * Permite a criação, atualização, exclusão e consulta de relatórios, além de associar veterinários aos relatórios.
 * 
 * Utiliza {@link RelatorioDao} para interagir com o banco de dados de relatórios
 * e {@link VeterinarioDao} para buscar dados sobre veterinários.
 */
public class RelatorioService {
    
    private RelatorioDao relatorioDao = DaoFactory.createRelatorioDao();
    private VeterinarioDao veterinarioDao = DaoFactory.createVeterinarioDao(); // Inicialização do VeterinarioDao
    
    /**
     * Insere um novo relatório no banco de dados.
     * 
     * @param relatorio O relatório a ser inserido.
     */
    public void insert(Relatorio relatorio) {
        relatorioDao.insert(relatorio);
    }

    /**
     * Atualiza um relatório existente no banco de dados.
     * 
     * @param relatorio O relatório a ser atualizado.
     */
    public void update(Relatorio relatorio) {
        relatorioDao.update(relatorio);
    }

    /**
     * Deleta um relatório pelo seu ID.
     * 
     * @param id O ID do relatório a ser deletado.
     */
    public void deleteById(Integer id) {
        relatorioDao.deleteById(id);
    }

    /**
     * Busca um relatório pelo seu ID.
     * 
     * @param id O ID do relatório.
     * @return O relatório encontrado, ou {@code null} se não encontrado.
     */
    public Relatorio findById(Integer id) {
        return relatorioDao.findById(id);
    }

    /**
     * Busca todos os relatórios no banco de dados.
     * 
     * @return Uma lista de todos os relatórios.
     */
    public List<Relatorio> findAll() {
        return relatorioDao.findAll();
    }

    /**
     * Busca todos os relatórios, associando cada um com o veterinário responsável.
     * 
     * @return Uma lista de relatórios com veterinários responsáveis carregados.
     */
    public List<Relatorio> findAllRelatoriosComVeterinario() {
        List<Relatorio> relatorios = relatorioDao.findAll();  // Busca todos os relatórios

        // Para cada relatório, se não houver veterinário responsável, ele é associado
        for (Relatorio relatorio : relatorios) {
            if (relatorio.getVeterinarioResponsavel() == null) {
                Veterinario veterinario = veterinarioDao.findById(relatorio.getVeterinario().getId()); // Usa veterinarioDao
                relatorio.setVeterinarioResponsavel(veterinario);
            }
        }

        return relatorios;
    }
}
