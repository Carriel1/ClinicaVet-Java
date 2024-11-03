package model.dao;

import db.DB;

import model.dao.impl.FuncionarioDaoJDBC;

public class DaoFactory {

	public static FuncionarioDao createFuncionarioDao() {
		return new FuncionarioDaoJDBC(DB.getConnection());
	}
	
}
