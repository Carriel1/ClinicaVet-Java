package model.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import model.dao.ConsultaDao;
import model.dao.DaoFactory;
import model.entities.Animal;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;

public class ConsultaService {

	private ConsultaDao dao;

	public ConsultaService() {
		this.dao = DaoFactory.createConsultaDao(); // Certifique-se de que o DAO está sendo inicializado corretamente.
	}

	public void salvarOuAtualizar(Consulta consulta) {

	    if (consulta == null) {
	        throw new IllegalArgumentException("Consulta não pode ser nula.");
	    }

	    validarConsulta(consulta);

	    if (consulta.getId() == null || consulta.getId() == 0) {
	        dao.insert(consulta);  
	    } else {
	        dao.update(consulta);  
	    }
	}


	public void marcarConsultaComoRealizada(Consulta consulta) {
		if (consulta == null) {
			throw new IllegalArgumentException("Consulta não pode ser nula.");
		}
		consulta.setStatus("Realizada");
		dao.update(consulta); // Atualiza o status no banco de dados
	}

	private void validarConsulta(Consulta consulta) {
		if (consulta.getData() == null) {
			throw new IllegalArgumentException("Data da consulta não pode ser nula.");
		}

		if (consulta.getHora() == null) {
			throw new IllegalArgumentException("Hora da consulta não pode ser nula.");
		}
		if (consulta.getVeterinario() == null) {
			throw new IllegalArgumentException("Veterinário não pode ser nulo.");
		}
		if (consulta.getCliente() == null) {
			throw new IllegalArgumentException("Cliente não pode ser nulo.");
		}
		if (consulta.getDescricao() == null || consulta.getDescricao().trim().isEmpty()) {
			throw new IllegalArgumentException("Descrição da consulta não pode ser vazia.");
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

	public List<Consulta> buscarPorCliente(Integer clienteId) {
		return dao.findByClienteId(clienteId);
	}

	public List<Consulta> buscarPorVeterinario(Integer veterinarioId) {
		return dao.findByVeterinarioId(veterinarioId);
	}

	public List<Consulta> buscarPorStatus(String status) {
		return dao.findByStatus(status);
	}

	public List<Consulta> findConsultasPendentes() {
		// Recupera todas as consultas com o status "Pendente"
		List<Consulta> consultasPendentes = dao.findByStatus("Pendente");

		for (Consulta consulta : consultasPendentes) {
			if (consulta.getAnimal() == null) {
				// Se o animal for null, podemos logar ou definir como "Sem animal"
				System.out.println("Consulta sem animal: " + consulta.getId());
			}
		}
		return consultasPendentes;
	}
}
