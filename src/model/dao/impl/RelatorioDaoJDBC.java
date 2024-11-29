package model.dao.impl;

import model.dao.RelatorioDao;
import model.entities.Relatorio;
import model.entities.Consulta;
import model.entities.Veterinario;
import db.DB;
import db.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDaoJDBC implements RelatorioDao {
    private Connection conn;

    public RelatorioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Relatorio relatorio) {
        String sql = """
            INSERT INTO Relatorio (consulta_id, veterinario_id, descricao, diagnostico, recomendacao, dataCriacao) 
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, relatorio.getConsulta().getId());
            st.setInt(2, relatorio.getVeterinario().getId());
            st.setString(3, relatorio.getDescricao());
            st.setString(4, relatorio.getDiagnostico());
            st.setString(5, relatorio.getRecomendacao());
            st.setDate(6, Date.valueOf(relatorio.getDataCriacao()));

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    relatorio.setId(rs.getInt(1));  // Atribui o ID gerado
                }
                rs.close();
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Relatorio relatorio) {
        String sql = """
            UPDATE Relatorio 
            SET descricao = ?, diagnostico = ?, recomendacao = ? 
            WHERE id = ?
        """;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, relatorio.getDescricao());
            st.setString(2, relatorio.getDiagnostico());
            st.setString(3, relatorio.getRecomendacao());
            st.setInt(4, relatorio.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Relatorio WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Relatorio findById(Integer id) {
        String sql = "SELECT * FROM Relatorio WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateRelatorio(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Relatorio> findAll() {
        String sql = "SELECT * FROM Relatorio";
        try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            List<Relatorio> relatorios = new ArrayList<>();
            while (rs.next()) {
                relatorios.add(instantiateRelatorio(rs));
            }
            return relatorios;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Relatorio> findByConsultaId(Integer consultaId) {
        String sql = "SELECT * FROM Relatorio WHERE consulta_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, consultaId);
            try (ResultSet rs = st.executeQuery()) {
                List<Relatorio> relatorios = new ArrayList<>();
                while (rs.next()) {
                    relatorios.add(instantiateRelatorio(rs));
                }
                return relatorios;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Relatorio> findByVeterinarioId(Integer veterinarioId) {
        String sql = "SELECT * FROM Relatorio WHERE veterinario_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, veterinarioId);
            try (ResultSet rs = st.executeQuery()) {
                List<Relatorio> relatorios = new ArrayList<>();
                while (rs.next()) {
                    relatorios.add(instantiateRelatorio(rs));
                }
                return relatorios;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Relatorio instantiateRelatorio(ResultSet rs) throws SQLException {
        Relatorio relatorio = new Relatorio();
        relatorio.setId(rs.getInt("id"));
        
        Consulta consulta = new Consulta();
        consulta.setId(rs.getInt("consulta_id"));
        relatorio.setConsulta(consulta);
        
        Veterinario veterinario = new Veterinario();
        veterinario.setId(rs.getInt("veterinario_id"));
        relatorio.setVeterinario(veterinario);
        
        relatorio.setDescricao(rs.getString("descricao"));
        relatorio.setDiagnostico(rs.getString("diagnostico"));
        relatorio.setRecomendacao(rs.getString("recomendacao"));
        relatorio.setDataCriacao(rs.getDate("dataCriacao").toLocalDate());
        
        return relatorio;
    }
}
