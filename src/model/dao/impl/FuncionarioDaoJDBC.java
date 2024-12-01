package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.FuncionarioDao;
import model.entities.Funcionario;

/**
 * Implementação da interface {@link FuncionarioDao} para operações CRUD de funcionários no banco de dados.
 * Esta classe utiliza JDBC para realizar as operações de inserção, atualização, exclusão, e busca de funcionários.
 */
public class FuncionarioDaoJDBC implements FuncionarioDao {

    private Connection conn;

    /**
     * Construtor padrão da classe {@link FuncionarioDaoJDBC}.
     * Cria uma instância sem conexão (será necessário configurar a conexão posteriormente).
     */
    public FuncionarioDaoJDBC() {}

    /**
     * Construtor da classe {@link FuncionarioDaoJDBC} com uma conexão já existente.
     * 
     * @param conn A conexão JDBC a ser utilizada para realizar as operações no banco de dados.
     */
    public FuncionarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    /**
     * Verifica se a conexão com o banco de dados está aberta e, caso não esteja, reabre a conexão.
     * 
     * @throws RuntimeException Se ocorrer um erro ao verificar ou reabrir a conexão.
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
     * Insere um novo funcionário no banco de dados.
     * 
     * @param obj O objeto {@link Funcionario} a ser inserido.
     * @throws DbException Se ocorrer um erro ao realizar a inserção no banco de dados.
     */
    @Override
    public void insert(Funcionario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Funcionario "
                    + "(name, email, birthDate, baseSalary, password) "
                    + "VALUES (?, ?, ?, ?, ?)", 
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setString(5, obj.getPassword());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
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

    /**
     * Atualiza as informações de um funcionário no banco de dados.
     * 
     * @param obj O objeto {@link Funcionario} com os dados atualizados.
     * @throws DbException Se ocorrer um erro ao realizar a atualização no banco de dados.
     */
    @Override
    public void update(Funcionario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Funcionario "
                    + "SET name = ?, email = ?, birthDate = ?, baseSalary = ?, password = ? "
                    + "WHERE id = ?");

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setString(5, obj.getPassword());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Exclui um funcionário do banco de dados com base no ID.
     * 
     * @param id O ID do funcionário a ser excluído.
     * @throws DbException Se ocorrer um erro ao realizar a exclusão no banco de dados.
     */
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Funcionario WHERE id = ?");

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /**
     * Busca um funcionário no banco de dados com base no seu ID.
     * 
     * @param id O ID do funcionário a ser buscado.
     * @return O funcionário encontrado, ou {@code null} se não houver funcionário com o ID especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
    @Override
    public Funcionario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Funcionario WHERE id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateFuncionario(rs);
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
     * Busca um funcionário no banco de dados com base no seu endereço de email.
     * 
     * @param username O email do funcionário a ser buscado.
     * @return O funcionário encontrado, ou {@code null} se não houver funcionário com o email especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
    @Override
    public Funcionario findByUsername(String username) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Funcionario WHERE email = ?");
            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateFuncionario(rs);
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
     * Método auxiliar para instanciar um objeto {@link Funcionario} a partir de um {@link ResultSet}.
     * 
     * @param rs O {@link ResultSet} contendo os dados do funcionário.
     * @return O objeto {@link Funcionario} instanciado com os dados do {@link ResultSet}.
     * @throws SQLException Se ocorrer um erro ao extrair os dados do {@link ResultSet}.
     */
    private Funcionario instantiateFuncionario(ResultSet rs) throws SQLException {
        Funcionario obj = new Funcionario();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setBaseSalary(rs.getDouble("baseSalary"));
        obj.setBirthDate(rs.getDate("birthDate"));
        obj.setPassword(rs.getString("password"));
        return obj;
    }

    /**
     * Busca todos os funcionários no banco de dados.
     * 
     * @return Uma lista de {@link Funcionario} contendo todos os funcionários cadastrados no banco de dados.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
    @Override
    public List<Funcionario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Funcionario ORDER BY name");
            rs = st.executeQuery();

            List<Funcionario> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateFuncionario(rs));
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
