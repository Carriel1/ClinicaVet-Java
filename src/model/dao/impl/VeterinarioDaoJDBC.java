package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VeterinarioDao;
import model.entities.Veterinario;

public class VeterinarioDaoJDBC implements VeterinarioDao {

    private Connection conn;

    public VeterinarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Veterinario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Veterinario (nome, email, telefone, senha) VALUES (?, ?, ?, ?)", 
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getTelefone());
            st.setString(4, obj.getSenha());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Veterinario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Veterinario SET nome = ?, email = ?, telefone = ?, senha = ? WHERE id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getTelefone());
            st.setString(4, obj.getSenha());
            st.setInt(5, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Veterinario WHERE id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Veterinario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Veterinario WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateVeterinario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Veterinario findByEmail(String email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Veterinario WHERE email = ?");
            st.setString(1, email);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateVeterinario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Veterinario instantiateVeterinario(ResultSet rs) throws SQLException {
        Veterinario obj = new Veterinario();
        obj.setId(rs.getInt("id"));
        obj.setNome(rs.getString("nome"));
        obj.setEmail(rs.getString("email"));
        obj.setTelefone(rs.getString("telefone"));
        obj.setSenha(rs.getString("senha"));
        return obj;
    }

    @Override
    public List<Veterinario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Veterinario ORDER BY nome");
            rs = st.executeQuery();

            List<Veterinario> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateVeterinario(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
