package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.AnimalDao;
import model.entities.Animal;
import model.entities.Cliente;

/**
 * Implementação da interface AnimalDao usando JDBC para interagir com o banco de dados.
 */
public class AnimalDaoJDBC implements AnimalDao {
    private Connection conn;

    /**
     * Construtor para inicializar a conexão com o banco de dados.
     * 
     * @param conn A conexão com o banco de dados.
     */
    public AnimalDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Construtor vazio.
     */
    public AnimalDaoJDBC () {
    	
    }
    
    /**
     * Verifica se a conexão com o banco de dados está aberta e, se não, reabre.
     */
    public void verificarConexao() {
        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("Conexão fechada ou nula, reabrindo...");
                conn = DB.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar ou reabrir a conexão", e);
        }
    }

    /**
     * Insere um novo animal no banco de dados.
     * 
     * @param animal O objeto Animal a ser inserido.
     */
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
                        animal.setId(rs.getInt(1));  
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza os dados de um animal no banco de dados.
     * 
     * @param obj O objeto Animal a ser atualizado.
     */
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
    
    /**
     * Deleta um animal do banco de dados, baseado no seu ID.
     * 
     * @param id O ID do animal a ser deletado.
     */
    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM animais WHERE id = ?")) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    
    /**
     * Busca um animal no banco de dados pelo seu ID.
     * 
     * @param id O ID do animal a ser buscado.
     * @return O objeto Animal, ou null se não encontrado.
     */
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
    
    /**
     * Busca todos os animais no banco de dados.
     * 
     * @return Uma lista de todos os animais.
     */
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

    /**
     * Busca os animais de um cliente específico, pelo ID do cliente.
     * 
     * @param clienteId O ID do cliente.
     * @return Uma lista de animais associados ao cliente.
     */
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

    
    @Override
    public List<Animal> findAnimaisByClienteId(Integer clienteId) {
        return findByClienteId(clienteId);  
    }
    
    /**
     * Método auxiliar para instanciar um objeto Animal a partir de um ResultSet.
     * 
     * @param rs O ResultSet contendo os dados do animal.
     * @return O objeto Animal instanciado.
     * @throws SQLException Se ocorrer um erro ao acessar os dados no ResultSet.
     */
    private Animal instantiateAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setId(rs.getInt("id"));
        animal.setNome(rs.getString("nome"));
        animal.setIdade(rs.getInt("idade"));
        animal.setRaca(rs.getString("raca"));
        animal.setEspecie(rs.getString("especie"));
        Cliente cliente = new Cliente(); 
        cliente.setId(rs.getInt("cliente_id"));
        animal.setCliente(cliente);
        return animal;
    }
    
    /**
     * Busca os animais de um cliente específico, pelo ID do cliente.
     * 
     * @param clienteId O ID do cliente.
     * @return Uma lista de animais associados ao cliente.
     */
    @Override
    public List<Animal> buscarPorClienteId(int clienteId) {
        List<Animal> lista = new ArrayList<>();
        String sql = "SELECT * FROM animais WHERE cliente_id = ?";
        
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, clienteId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Animal animal = new Animal();
                    animal.setId(rs.getInt("id"));
                    animal.setNome(rs.getString("nome"));
                    animal.setEspecie(rs.getString("especie"));
                    animal.setRaca(rs.getString("raca"));
                    lista.add(animal);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar animais: " + e.getMessage());
        }

        return lista;
    }
    
    /**
     * Deleta todos os animais de um cliente específico, pelo ID do cliente.
     * 
     * @param clienteId O ID do cliente.
     */
    @Override
    public void deleteByClienteId(Integer clienteId) {
        String sql = "DELETE FROM animais WHERE cliente_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, clienteId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Erro ao excluir os animais do cliente: " + e.getMessage());
        }
    }
}
