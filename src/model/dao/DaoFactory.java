package model.dao;

import db.DB;
import model.dao.impl.AnimalDaoJDBC;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.FuncionarioDaoJDBC;
import model.dao.impl.VeterinarioDaoJDBC;

public class DaoFactory {

    public static FuncionarioDao createFuncionarioDao() {
        return new FuncionarioDaoJDBC(DB.getConnection());
    }

    public static ClienteDao createClienteDao() {
        return new ClienteDaoJDBC(DB.getConnection());
    }
    
    public static VeterinarioDao createVeterinarioDao() {
        return new VeterinarioDaoJDBC(DB.getConnection());
    }
    
    
    public static AnimalDao createAnimalDao() {
        return new AnimalDaoJDBC(DB.getConnection());
    }
}
