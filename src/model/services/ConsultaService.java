package model.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ConsultaDao;
import model.dao.DaoFactory;
import model.entities.Consulta;

public class ConsultaService {

    private ConsultaDao dao;

    public ConsultaService() {
        this.dao = DaoFactory.createConsultaDao(); // Certifique-se de que o DAO está sendo inicializado corretamente.
    }

    // Método para salvar ou atualizar a consulta
    public void salvarOuAtualizar(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula.");
        }

        validarConsulta(consulta);

        if (consulta.getId() == null || consulta.getId() == 0) {
            dao.insert(consulta);  // Inserir nova consulta no banco de dados
        } else {
            dao.update(consulta);  // Atualizar consulta existente
        }
    }

    public void marcarConsultaComoRealizada(Consulta consulta) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DB.getConnection();  // Abre a conexão
            if (conn.isClosed()) {
                throw new DbException("A conexão está fechada.");
            }
            
            String sql = "UPDATE consulta SET status = 'realizada' WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, consulta.getId());
            
            int rowsAffected = st.executeUpdate();  // Executa a atualização
            if (rowsAffected == 0) {
                throw new DbException("Consulta não encontrada ou já realizada.");
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar consulta: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            // A conexão não é fechada aqui, ela será fechada em outro ponto do sistema
        }
    }



    // Valida os campos obrigatórios da consulta
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

    // Método para deletar consulta por ID
    public void deletar(Integer id) {
        dao.deleteById(id);
    }

    // Método para buscar consulta por ID
    public Consulta buscarPorId(Integer id) {
        return dao.findById(id);
    }

    // Método para buscar consultas por cliente
    public List<Consulta> buscarPorCliente(Integer clienteId) {
        return dao.findByClienteId(clienteId);
    }

    // Método para buscar consultas por veterinário
    public List<Consulta> buscarPorVeterinario(Integer veterinarioId) {
        return dao.findByVeterinarioId(veterinarioId);
    }

    // Método para buscar consultas por status
    public List<Consulta> buscarPorStatus(String status) {
        return dao.findByStatus(status);
    }

    // Método para encontrar consultas pendentes
    public List<Consulta> findConsultasPendentes() {
        // Recupera todas as consultas com o status "Pendente"
        List<Consulta> consultasPendentes = dao.findByStatus("Pendente");

        for (Consulta consulta : consultasPendentes) {
            if (consulta.getAnimal() == null) {
                // Se o animal for null, podemos logar ou definir como "Sem animal"
                System.out.println("Consulta sem animal: " + consulta.getId());
            }
            if (consulta.getVeterinario() == null) {
                // Se o veterinário não foi atribuído, podemos logar ou tratar
                System.out.println("Consulta sem veterinário: " + consulta.getId());
            }
        }
        return consultasPendentes;
    }
    
    public void cancelarConsulta(Consulta consulta) {
        if (consulta == null || consulta.getId() == null) {
            throw new IllegalArgumentException("Consulta inválida. Verifique o ID.");
        }

        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DB.getConnection();  // Abre a conexão
            if (conn.isClosed()) {
                throw new DbException("A conexão está fechada.");
            }
            
            // Atualiza o status da consulta para 'Cancelada'
            String sql = "UPDATE consulta SET status = 'Cancelada' WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, consulta.getId());
            
            int rowsAffected = st.executeUpdate();  // Executa a atualização
            if (rowsAffected == 0) {
                throw new DbException("Consulta não encontrada ou já cancelada.");
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao cancelar consulta: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            // A conexão não é fechada aqui, ela será fechada em outro ponto do sistema
        }
    }

}
