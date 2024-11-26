package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.AnimalDao;
import model.entities.Animal;
import model.entities.Cliente;

public class AnimalDaoJDBC implements AnimalDao {
    private Connection conn;

    public AnimalDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Animal animal) {
        String sql = "INSERT INTO animais (nome, idade, raca, especie, cliente_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getIdade());
            stmt.setString(3, animal.getRaca());
            stmt.setString(4, animal.getEspecie());
            stmt.setInt(5, animal.getCliente().getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        animal.setId(rs.getInt(1));  // Atribui o ID gerado
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Animal obj) {
        try (PreparedStatement st = conn.prepareStatement(
                "UPDATE animais SET nome = ?, idade = ?, raca = ?, especie = ?, cliente_id = ? WHERE id = ?")) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdade());
            st.setString(3, obj.getRaca());
            st.setString(4, obj.getEspecie());
            st.setInt(5, obj.getCliente().getId());
            st.setInt(6, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM animais WHERE id = ?")) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Animal findById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM animais WHERE id = ?")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateAnimal(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Animal> findAll() {
        List<Animal> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM animais")) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(instantiateAnimal(rs));
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Animal> findByClienteId(Integer clienteId) {
        List<Animal> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM animais WHERE cliente_id = ?")) {
            st.setInt(1, clienteId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(instantiateAnimal(rs));
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return list;
    }

    // Renomeie este método para implementar corretamente a interface
    @Override
    public List<Animal> findAnimaisByClienteId(Integer clienteId) {
        return findByClienteId(clienteId);  // Chama o método já implementado
    }

    private Animal instantiateAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setId(rs.getInt("id"));
        animal.setNome(rs.getString("nome"));
        animal.setIdade(rs.getInt("idade"));
        animal.setRaca(rs.getString("raca"));
        animal.setEspecie(rs.getString("especie"));
        Cliente cliente = new Cliente(); // Instancia o cliente associado
        cliente.setId(rs.getInt("cliente_id"));
        animal.setCliente(cliente);
        return animal;
    }

    // Método para excluir todos os animais de um cliente
    public void deleteByClienteId(Integer clienteId) {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM animais WHERE cliente_id = ?")) {
            st.setInt(1, clienteId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
