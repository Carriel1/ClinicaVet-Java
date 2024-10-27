package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.FuncionarioDao;
import model.entities.Funcionario;

public class FuncionarioService {

	private FuncionarioDao dao = DaoFactory.createFuncionarioDao();
	
	public List<Funcionario> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Funcionario obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Funcionario obj) {
		dao.deleteById(obj.getId());
	}
}