package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.ConsultaDao;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;

public class ConsultaDaoJDBC implements ConsultaDao {
    private Connection conn;

    public ConsultaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Consulta consulta) {
        String sql = "INSERT INTO Consulta (data, hora, descricao, status, clienteId, veterinarioId, criadoPor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setDate(1, Date.valueOf(consulta.getData()));               // Converting LocalDate to Date
            st.setTime(2, Time.valueOf(consulta.getHora()));               // Converting LocalTime to Time
            st.setString(3, consulta.getDescricao());
            st.setString(4, consulta.getStatus());
            st.setInt(5, consulta.getCliente().getId());
            st.setInt(6, consulta.getVeterinario() != null ? consulta.getVeterinario().getId() : null);
            st.setString(7, consulta.getCriadoPor());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    consulta.setId(rs.getInt(1));
                }
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Consulta consulta) {
        String sql = "UPDATE Consulta SET data = ?, hora = ?, descricao = ?, status = ?, clienteId = ?, veterinarioId = ?, criadoPor = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(consulta.getData()));               // Converting LocalDate to Date
            st.setTime(2, Time.valueOf(consulta.getHora()));               // Converting LocalTime to Time
            st.setString(3, consulta.getDescricao());
            st.setString(4, consulta.getStatus());
            st.setInt(5, consulta.getCliente().getId());
            st.setInt(6, consulta.getVeterinario() != null ? consulta.getVeterinario().getId() : null);
            st.setString(7, consulta.getCriadoPor());
            st.setInt(8, consulta.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Consulta WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Consulta findById(Integer id) {
        String sql = "SELECT * FROM Consulta WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateConsulta(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Consulta> findAll() {
        String sql = "SELECT * FROM Consulta";
        try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            List<Consulta> consultas = new ArrayList<>();
            while (rs.next()) {
                consultas.add(instantiateConsulta(rs));
            }
            return consultas;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Consulta> findByClienteId(Integer clienteId) {
        String sql = "SELECT * FROM Consulta WHERE clienteId = ?";
        return findByForeignKey(sql, clienteId);
    }

    @Override
    public List<Consulta> findByVeterinarioId(Integer veterinarioId) {
        String sql = "SELECT * FROM Consulta WHERE veterinarioId = ?";
        return findByForeignKey(sql, veterinarioId);
    }

    @Override
    public List<Consulta> findByStatus(String status) {
        String sql = "SELECT * FROM Consulta WHERE status = ?";
        return findByForeignKey(sql, status);
    }

    private Consulta instantiateConsulta(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getInt("id"));
        consulta.setData(rs.getDate("data").toLocalDate());           // Converting Date to LocalDate
        consulta.setHora(rs.getTime("hora").toLocalTime());           // Converting Time to LocalTime
        consulta.setDescricao(rs.getString("descricao"));
        consulta.setStatus(rs.getString("status"));
        consulta.setCriadoPor(rs.getString("criadoPor"));

        // Recuperando Cliente e Veterinário do banco
        Cliente cliente = new Cliente(); // Aqui você deve criar a instância de Cliente corretamente
        cliente.setId(rs.getInt("clienteId"));
        consulta.setCliente(cliente);

        Veterinario veterinario = new Veterinario(); // Aqui você deve criar a instância de Veterinário corretamente
        veterinario.setId(rs.getInt("veterinarioId"));
        consulta.setVeterinario(veterinario);

        return consulta;
    }

    private List<Consulta> findByForeignKey(String sql, Object param) {
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setObject(1, param);
            try (ResultSet rs = st.executeQuery()) {
                List<Consulta> consultas = new ArrayList<>();
                while (rs.next()) {
                    consultas.add(instantiateConsulta(rs));
                }
                return consultas;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}

