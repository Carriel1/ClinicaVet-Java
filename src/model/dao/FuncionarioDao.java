package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Funcionario;

public interface FuncionarioDao {

	void insert(Funcionario obj);
	void update(Funcionario obj);
	void deleteById(Integer id);
	Funcionario findById(Integer id);
	List<Funcionario> findAll();
	List<Funcionario> findByDepartment(Department department);
}