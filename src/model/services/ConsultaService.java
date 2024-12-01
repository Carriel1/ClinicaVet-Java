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

/**
 * Serviço responsável pela lógica de negócios relacionada às consultas.
 * Permite a gestão de consultas, incluindo criação, atualização, 
 * busca, cancelamento e alteração de status.
 * 
 * Utiliza o {@link ConsultaDao} para interagir com o banco de dados.
 */
public class ConsultaService {

    private ConsultaDao dao;

    /**
     * Construtor que inicializa o serviço com o DAO de consultas.
     * O DAO é obtido através de uma fábrica de DAOs.
     */
    public ConsultaService() {
        this.dao = DaoFactory.createConsultaDao(); 
    }

    /**
     * Busca todas as consultas com o status "Pendente".
     * 
     * @return Lista de consultas pendentes.
     */
    public List<Consulta> buscarConsultasPendentes() {
        return dao.findAllPendentes();
    }

    /**
     * Aceita uma consulta, atualizando seu status para "Pendente".
     * 
     * @param consulta A consulta a ser aceita.
     */
    public void aceitarConsulta(Consulta consulta) {
        dao.updateStatus(consulta, "Pendente");  
    }

    /**
     * Busca todas as consultas requisitadas (status "Requisitada").
     * 
     * @return Lista de consultas requisitadas.
     */
    public List<Consulta> buscarConsultasRequisitadas() {
        return dao.findAllRequisitadas();  
    }

    /**
     * Nega uma consulta, alterando seu status para "Negada".
     * 
     * @param consulta A consulta a ser negada.
     */
    public void negarConsulta(Consulta consulta) {
        dao.updateStatus(consulta, "negada");  
    }

    /**
     * Salva ou atualiza uma consulta.
     * Se a consulta não tiver ID, ela é inserida. Caso contrário, ela é atualizada.
     * 
     * @param consulta A consulta a ser salva ou atualizada.
     * @throws IllegalArgumentException Se a consulta for inválida.
     */
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

    /**
     * Marca uma consulta como realizada, alterando seu status para "Realizada".
     * 
     * @param consulta A consulta a ser marcada como realizada.
     * @throws DbException Se ocorrer um erro ao atualizar o status no banco de dados.
     */
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
        }
    }
    
    /**
     * Valida os campos de uma consulta antes de ser salva ou atualizada.
     * 
     * @param consulta A consulta a ser validada.
     * @throws IllegalArgumentException Se algum campo obrigatório estiver inválido.
     */
    public void validarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula.");
        }
        if (consulta.getCliente() == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (consulta.getAnimal() == null) {
            throw new IllegalArgumentException("Animal não pode ser nulo.");
        }
        if ("Funcionário".equals(consulta.getCriadoPor()) && consulta.getVeterinario() == null) {
            throw new IllegalArgumentException("Veterinário não pode ser nulo quando criado por um funcionário.");
        }
        if (consulta.getDescricao() == null || consulta.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia.");
        }
        if (consulta.getData() == null) {
            throw new IllegalArgumentException("Data da consulta não pode ser nula.");
        }
        if (consulta.getHora() == null) {
            throw new IllegalArgumentException("Hora da consulta não pode ser nula.");
        }
    }

    /**
     * Busca todas as consultas no banco de dados.
     * 
     * @return Lista de todas as consultas.
     */
    public List<Consulta> findAll() {
        return dao.findAll();
    }

    /**
     * Deleta uma consulta pelo ID.
     * 
     * @param id O ID da consulta a ser deletada.
     */
    public void deletar(Integer id) {
        dao.deleteById(id);
    }

    /**
     * Busca uma consulta pelo ID.
     * 
     * @param id O ID da consulta a ser buscada.
     * @return A consulta encontrada ou null se não existir.
     */
    public Consulta buscarPorId(Integer id) {
        return dao.findById(id);
    }

    /**
     * Busca todas as consultas de um cliente específico.
     * 
     * @param clienteId O ID do cliente.
     * @return Lista de consultas do cliente.
     */
    public List<Consulta> buscarPorCliente(Integer clienteId) {
        return dao.findByClienteId(clienteId);
    }

    /**
     * Busca todas as consultas de um veterinário específico.
     * 
     * @param veterinarioId O ID do veterinário.
     * @return Lista de consultas do veterinário.
     */
    public List<Consulta> buscarPorVeterinario(Integer veterinarioId) {
        return dao.findByVeterinarioId(veterinarioId);
    }

    /**
     * Busca todas as consultas com um status específico.
     * 
     * @param status O status da consulta.
     * @return Lista de consultas com o status especificado.
     */
    public List<Consulta> buscarPorStatus(String status) {
        return dao.findByStatus(status);
    }

    /**
     * Busca todas as consultas com o status "Pendente" e valida a integridade dos dados.
     * Caso algum campo importante (como animal ou veterinário) esteja ausente, o método realiza o log.
     * 
     * @return Lista de consultas pendentes.
     */
    public List<Consulta> findConsultasPendentes() {
        List<Consulta> consultasPendentes = dao.findByStatus("Pendente");

        for (Consulta consulta : consultasPendentes) {
            if (consulta.getAnimal() == null) {
                System.out.println("Consulta sem animal: " + consulta.getId());
            }
            if (consulta.getVeterinario() == null) {
                System.out.println("Consulta sem veterinário: " + consulta.getId());
            }
        }
        return consultasPendentes;
    }

    /**
     * Cancela uma consulta, alterando seu status para "Cancelada".
     * 
     * @param consulta A consulta a ser cancelada.
     * @throws DbException Se ocorrer um erro ao atualizar o status no banco de dados.
     */
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
        }
    }
}
