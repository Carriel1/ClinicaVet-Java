package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClienteDao;
import model.entities.Cliente;
import model.entities.Animal;

/**
 * Implementação da interface ClienteDao utilizando JDBC para realizar operações no banco de dados.
 * Esta classe fornece métodos para inserir, atualizar, excluir e buscar clientes, além de manipular
 * os animais associados a cada cliente.
 */
public class ClienteDaoJDBC implements ClienteDao {

    private Connection conn;

    /**
     * Construtor que recebe a conexão com o banco de dados.
     * 
     * @param conn A conexão com o banco de dados.
     */
    public ClienteDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Construtor padrão.
     */
    public ClienteDaoJDBC () {
    	
    }
    
    /**
     * Verifica se a conexão com o banco de dados está aberta e, caso não esteja, reabre a conexão.
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
     * Insere um cliente no banco de dados, incluindo os animais associados a ele.
     * 
     * @param obj O cliente a ser inserido.
     */
    @Override
    public void insert(Cliente obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO cliente "
                    + "(nome, email, telefone, senha, endereco, cpf) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", 
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getTelefone());
            st.setString(4, obj.getSenha());
            st.setString(5, obj.getEndereco());
            st.setString(6, obj.getCpf());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);

                // Após inserir o cliente, insere seus animais
                for (Animal animal : obj.getAnimais()) {
                    animal.setClienteId(obj.getId()); // Setar o cliente id no animal
                    new AnimalDaoJDBC(conn).insert(animal); // Inserir animal
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Atualiza as informações de um cliente no banco de dados, incluindo seus animais.
     * 
     * @param obj O cliente a ser atualizado.
     */
    @Override
    public void update(Cliente obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE cliente "
                    + "SET nome = ?, email = ?, telefone = ?, senha = ?, endereco = ?, cpf = ? "
                    + "WHERE id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getTelefone());
            st.setString(4, obj.getSenha());
            st.setString(5, obj.getEndereco());
            st.setString(6, obj.getCpf());
            st.setInt(7, obj.getId());

            st.executeUpdate();

            // Atualizar os animais do cliente
            for (Animal animal : obj.getAnimais()) {
                animal.setClienteId(obj.getId()); // Setar o cliente id no animal
                new AnimalDaoJDBC(conn).update(animal); // Atualizar animal
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Exclui um cliente do banco de dados, removendo também seus animais associados.
     * 
     * @param id O id do cliente a ser excluído.
     */
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");

            st.setInt(1, id);
            st.executeUpdate();

            // Remover os animais do cliente
            new AnimalDaoJDBC(conn).deleteByClienteId(id);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Busca um cliente pelo seu id no banco de dados.
     * 
     * @param id O id do cliente a ser buscado.
     * @return O cliente encontrado ou null caso não exista.
     */
    @Override
    public Cliente findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM cliente WHERE id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Cliente cliente = instantiateCliente(rs);

                // Carregar os animais associados ao cliente
                List<Animal> animais = new AnimalDaoJDBC(conn).findByClienteId(id);
                cliente.setAnimais(animais);

                return cliente;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /**
     * Busca um cliente pelo seu nome (usuário) no banco de dados.
     * 
     * @param username O nome do cliente a ser buscado.
     * @return O cliente encontrado ou null caso não exista.
     */
    @Override
    public Cliente findByUsername(String username) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM cliente WHERE nome = ?");

            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next()) {
                Cliente cliente = instantiateCliente(rs);

                // Carregar os animais associados ao cliente
                List<Animal> animais = new AnimalDaoJDBC(conn).findByClienteId(cliente.getId());
                cliente.setAnimais(animais);

                return cliente;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /**
     * Cria um objeto Cliente a partir de um ResultSet.
     * 
     * @param rs O ResultSet contendo os dados do cliente.
     * @return O cliente instanciado.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Cliente instantiateCliente(ResultSet rs) throws SQLException {
        Cliente obj = new Cliente();
        obj.setId(rs.getInt("id"));
        obj.setNome(rs.getString("nome"));
        obj.setEmail(rs.getString("email"));
        obj.setTelefone(rs.getString("telefone"));
        obj.setSenha(rs.getString("senha"));
        obj.setEndereco(rs.getString("endereco"));
        obj.setCpf(rs.getString("cpf"));
        return obj;
    }

    /**
     * Retorna uma lista de todos os clientes cadastrados no banco de dados.
     * 
     * @return Uma lista de todos os clientes.
     */
    @Override
    public List<Cliente> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM cliente ORDER BY nome");
            rs = st.executeQuery();

            List<Cliente> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateCliente(rs));
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
