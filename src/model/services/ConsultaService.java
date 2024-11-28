package model.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import model.dao.ConsultaDao;
import model.dao.DaoFactory;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;

public class ConsultaService {
	private ConsultaDao dao;

	// Construtor para injeção do DAO
	public ConsultaService() {
		this.dao = DaoFactory.createConsultaDao(); // Certifique-se de que o DAO está sendo inicializado corretamente.
	}

	public void salvarOuAtualizar(Cliente cliente, Veterinario veterinario, LocalDate data, LocalTime hora,
			String descricao, String criadoPor) {
		// Verifica se os parâmetros são válidos
		if (cliente == null) {
			throw new IllegalArgumentException("Cliente não pode ser nulo.");
		}
		if (veterinario == null) {
			throw new IllegalArgumentException("Veterinário não pode ser nulo.");
		}
		if (data == null) {
			throw new IllegalArgumentException("Data da consulta não pode ser nula.");
		}
		if (hora == null) {
			throw new IllegalArgumentException("Hora da consulta não pode ser nula.");
		}
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new IllegalArgumentException("Descrição não pode ser vazia.");
		}

		// Cria a consulta usando os parâmetros fornecidos
		Consulta consulta = new Consulta(null, cliente, veterinario, data, hora, descricao, "Aprovada", criadoPor);

// Se o ID da consulta for nulo, é uma nova consulta, então insere no banco
		if (consulta.getId() == null) {
			dao.insert(consulta);
		} else {
// Caso contrário, atualiza a consulta existente
			dao.update(consulta);
		}
	}

	// Validação básica para os campos da consulta
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

	// Método para buscar todas as consultas
	public List<Consulta> findAll() {
		return dao.findAll();
	}

	// Método para deletar uma consulta pelo ID
	public void deletar(Integer id) {
		dao.deleteById(id);
	}

	// Método para buscar consulta por ID
	public Consulta buscarPorId(Integer id) {
		return dao.findById(id);
	}

	// Buscar consultas por cliente
	public List<Consulta> buscarPorCliente(Integer clienteId) {
		return dao.findByClienteId(clienteId);
	}

	// Buscar consultas por veterinário
	public List<Consulta> buscarPorVeterinario(Integer veterinarioId) {
		return dao.findByVeterinarioId(veterinarioId);
	}

	// Buscar consultas por status (ex: "pendente", "realizada")
	public List<Consulta> buscarPorStatus(String status) {
		return dao.findByStatus(status);
	}
}
