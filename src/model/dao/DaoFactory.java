package model.dao;

import db.DB;
import model.dao.impl.AnimalDaoJDBC;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.ConsultaDaoJDBC;
import model.dao.impl.FuncionarioDaoJDBC;
import model.dao.impl.RelatorioDaoJDBC;
import model.dao.impl.VeterinarioDaoJDBC;  

/**
 * Fábrica de DAOs (Data Access Objects) que fornece métodos estáticos para criar instâncias 
 * de objetos responsáveis pelo acesso a dados de diferentes entidades, como Funcionario, Cliente, 
 * Veterinário, Animal, Consulta e Relatório.
 * 
 * A fábrica é usada para centralizar a criação das instâncias de DAOs, garantindo a consistência
 * e evitando a criação de múltiplas instâncias de conexões com o banco de dados.
 */
public class DaoFactory {

    /**
     * Cria e retorna uma instância de FuncionarioDao, responsável pelas operações de acesso
     * a dados da entidade Funcionario.
     * 
     * @return Uma instância de FuncionarioDao.
     */
    public static FuncionarioDao createFuncionarioDao() {
        return new FuncionarioDaoJDBC(DB.getConnection());
    }

    /**
     * Cria e retorna uma instância de ClienteDao, responsável pelas operações de acesso
     * a dados da entidade Cliente.
     * 
     * @return Uma instância de ClienteDao.
     */
    public static ClienteDao createClienteDao() {
        return new ClienteDaoJDBC(DB.getConnection());
    }
    
    /**
     * Cria e retorna uma instância de VeterinarioDao, responsável pelas operações de acesso
     * a dados da entidade Veterinario.
     * 
     * @return Uma instância de VeterinarioDao.
     */
    public static VeterinarioDao createVeterinarioDao() {
        return new VeterinarioDaoJDBC(DB.getConnection());
    }

    /**
     * Cria e retorna uma instância de AnimalDao, responsável pelas operações de acesso
     * a dados da entidade Animal.
     * 
     * @return Uma instância de AnimalDao.
     */
    public static AnimalDao createAnimalDao() {
        return new AnimalDaoJDBC(DB.getConnection());
    }

    /**
     * Cria e retorna uma instância de ConsultaDao, responsável pelas operações de acesso
     * a dados da entidade Consulta.
     * 
     * @return Uma instância de ConsultaDao.
     */
    public static ConsultaDao createConsultaDao() {
        return new ConsultaDaoJDBC(DB.getConnection()); 
    }
    
    /**
     * Cria e retorna uma instância de RelatorioDao, responsável pelas operações de acesso
     * a dados da entidade Relatorio.
     * 
     * @return Uma instância de RelatorioDao.
     */
    public static RelatorioDao createRelatorioDao() {
        return new RelatorioDaoJDBC(DB.getConnection()); 
    }
}
