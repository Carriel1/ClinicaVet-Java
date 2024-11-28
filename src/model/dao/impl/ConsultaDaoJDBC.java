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

import db.DB;
import db.DbException;
import model.dao.ConsultaDao;
import model.entities.Animal;
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
        String sql = """
            INSERT INTO Consulta (data, hora, descricao, status, clienteId, veterinarioId, criadoPor, animal_id) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Configurando os parâmetros da consulta
            st.setDate(1, Date.valueOf(consulta.getData()));  // Convertendo LocalDate para Date
            st.setTime(2, Time.valueOf(consulta.getHora()));  // Convertendo LocalTime para Time
            st.setString(3, consulta.getDescricao());
            st.setString(4, consulta.getStatus());
            st.setInt(5, consulta.getCliente().getId()); // Garantindo que cliente tenha ID válido
            st.setInt(6, consulta.getVeterinario() != null ? consulta.getVeterinario().getId() : null); // Pode ser null
            st.setString(7, consulta.getCriadoPor());
            st.setInt(8, consulta.getAnimal().getId()); // Animal sempre será necessário devido à restrição NOT NULL

            // Executando a inserção
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                // Recuperando a chave gerada
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    consulta.setId(rs.getInt(1)); // Atribuindo o ID gerado à consulta
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
    public void update(Consulta consulta) {
        String sql = "UPDATE Consulta SET data = ?, hora = ?, descricao = ?, status = ?, clienteId = ?, veterinarioId = ?, criadoPor = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(consulta.getData()));  // Converting LocalDate to Date
            st.setTime(2, Time.valueOf(consulta.getHora()));  // Converting LocalTime to Time
            st.setString(3, consulta.getDescricao());
            st.setString(4, consulta.getStatus());
            st.setInt(5, consulta.getCliente().getId()); // Usando o método getId() do Cliente
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

    public List<Consulta> findByStatus(String status) {
        String sql = """
            SELECT 
                c.id AS consulta_id, c.data, c.hora, c.descricao, c.status, c.criadoPor, 
                cl.id AS cliente_id, cl.nome AS cliente_nome, 
                a.id AS animal_id, a.nome AS animal_nome
            FROM consulta c 
            JOIN cliente cl ON c.clienteid = cl.id 
            JOIN animais a ON c.animal_id = a.id 
            WHERE c.status = ?
        """;

        List<Consulta> consultas = new ArrayList<>();
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Cliente
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));

                // Animal
                Animal animal = new Animal();
                animal.setId(rs.getInt("animal_id"));
                animal.setNome(rs.getString("animal_nome"));

                // Consulta
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("consulta_id"));
                consulta.setData(rs.getDate("data").toLocalDate());
                consulta.setHora(rs.getTime("hora").toLocalTime());
                consulta.setDescricao(rs.getString("descricao"));
                consulta.setStatus(rs.getString("status"));
                consulta.setCriadoPor(rs.getString("criadoPor"));
                consulta.setCliente(cliente);
                consulta.setAnimal(animal);

                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultas;
    }


    public List<Consulta> findConsultasPendentesByVeterinarioId(Integer veterinarioId) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE veterinarioId = ? AND status = 'Pendente'"; // Exemplo de SQL
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, veterinarioId);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setVeterinario(new Veterinario(
                	    rs.getInt("veterinarioId"), // Preencher o veterinário com os dados do banco
                	    rs.getString("nome_veterinario"),
                	    rs.getString("cpf_veterinario"),
                	    rs.getString("email_veterinario"),
                	    rs.getString("telefone_veterinario"),
                	    rs.getString("senha_veterinario")
                	));

                	consulta.setCliente(new Cliente(
                	    rs.getInt("clienteId"), // Preencher o cliente com os dados do banco
                	    rs.getString("nome_cliente"),
                	    rs.getString("email_cliente"),
                	    rs.getString("telefone_cliente"),
                	    rs.getString("senha_cliente"),
                	    rs.getString("endereco_cliente"),
                	    rs.getString("cpf_cliente")
                	));

                consulta.setData(rs.getDate("data").toLocalDate());
                consulta.setHora(rs.getTime("hora").toLocalTime());
                consulta.setDescricao(rs.getString("descricao"));
                // Aqui você pode carregar o animal, caso seja necessário.
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException("Erro ao carregar as consultas pendentes.");
        }
        return consultas;
    }


    private Consulta instantiateConsulta(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getInt("id"));
        consulta.setData(rs.getDate("data").toLocalDate());  // Converting Date to LocalDate
        consulta.setHora(rs.getTime("hora").toLocalTime());  // Converting Time to LocalTime
        consulta.setDescricao(rs.getString("descricao"));
        consulta.setStatus(rs.getString("status"));
        consulta.setCriadoPor(rs.getString("criadoPor"));

        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("clienteId"));
        consulta.setCliente(cliente);

        Veterinario veterinario = new Veterinario();
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
