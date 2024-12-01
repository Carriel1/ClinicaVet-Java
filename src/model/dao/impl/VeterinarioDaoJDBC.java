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

/**
 * Implementação da interface {@link VeterinarioDao} para operações CRUD de veterinários no banco de dados.
 * Utiliza JDBC para realizar inserções, atualizações, exclusões e consultas.
 */
public class VeterinarioDaoJDBC implements VeterinarioDao {

    private Connection conn;

    /**
     * Construtor padrão da classe {@link VeterinarioDaoJDBC}.
     * Cria uma instância sem conexão (será necessário configurar a conexão posteriormente).
     */
    public VeterinarioDaoJDBC () {}

    /**
     * Construtor da classe {@link VeterinarioDaoJDBC} com uma conexão já existente.
     * 
     * @param conn A conexão JDBC a ser utilizada para realizar as operações no banco de dados.
     */
    public VeterinarioDaoJDBC(Connection conn) {
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
     * Insere um novo veterinário no banco de dados.
     * 
     * @param obj O objeto {@link Veterinario} a ser inserido.
     * @throws DbException Se ocorrer um erro ao realizar a inserção no banco de dados.
     */
    @Override
    public void insert(Veterinario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Veterinario (nome, cpf, email, telefone, senha) VALUES (?, ?, ?, ?, ?)", 
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCpf());
            st.setString(3, obj.getEmail());
            st.setString(4, obj.getTelefone());
            st.setString(5, obj.getSenha());

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

    /**
     * Atualiza os dados de um veterinário no banco de dados.
     * 
     * @param obj O objeto {@link Veterinario} com os dados atualizados.
     * @throws DbException Se ocorrer um erro ao realizar a atualização no banco de dados.
     */
    @Override
    public void update(Veterinario obj) {
        String sql = "UPDATE veterinario SET nome = ?, email = ?, telefone = ?, senha = ?, cpf = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getTelefone());
            st.setString(4, obj.getSenha());
            st.setString(5, obj.getCpf());
            st.setInt(6, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Exclui um veterinário do banco de dados com base no seu ID.
     * 
     * @param id O ID do veterinário a ser excluído.
     * @throws DbException Se ocorrer um erro ao realizar a exclusão no banco de dados.
     */
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

    /**
     * Busca um veterinário no banco de dados com base no seu ID.
     * 
     * @param id O ID do veterinário a ser buscado.
     * @return O objeto {@link Veterinario} encontrado ou {@code null} se não houver veterinário com o ID especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
    @Override
    public Veterinario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM veterinario WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setId(rs.getInt("id"));
                veterinario.setNome(rs.getString("nome"));
                veterinario.setTelefone(rs.getString("telefone"));
                return veterinario;
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
     * Busca um veterinário no banco de dados com base no seu e-mail.
     * 
     * @param email O e-mail do veterinário a ser buscado.
     * @return O objeto {@link Veterinario} encontrado ou {@code null} se não houver veterinário com o e-mail especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
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

    /**
     * Busca um veterinário no banco de dados com base no seu CPF.
     * 
     * @param cpf O CPF do veterinário a ser buscado.
     * @return O objeto {@link Veterinario} encontrado ou {@code null} se não houver veterinário com o CPF especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
    @Override
    public Veterinario findByCpf(String cpf) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Veterinario WHERE cpf = ?");
            st.setString(1, cpf);
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

    /**
     * Busca um veterinário no banco de dados com base no seu nome (username).
     * 
     * @param username O nome do veterinário a ser buscado (representando o username).
     * @return O objeto {@link Veterinario} encontrado ou {@code null} se não houver veterinário com o nome especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
    @Override
    public Veterinario findByUsername(String username) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Veterinario WHERE nome = ?"); // ou qualquer outro campo que represente o username
            st.setString(1, username);
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

    /**
     * Instancia um objeto {@link Veterinario} a partir de um {@link ResultSet}.
     * 
     * @param rs O {@link ResultSet} contendo os dados do veterinário.
     * @return O objeto {@link Veterinario} instanciado com os dados do {@link ResultSet}.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do {@link ResultSet}.
     */
    private Veterinario instantiateVeterinario(ResultSet rs) throws SQLException {
        Veterinario obj = new Veterinario();
        obj.setId(rs.getInt("id"));
        obj.setNome(rs.getString("nome"));
        obj.setCpf(rs.getString("cpf"));
        obj.setEmail(rs.getString("email"));
        obj.setTelefone(rs.getString("telefone"));
        obj.setSenha(rs.getString("senha"));
        return obj;
    }

    /**
     * Retorna uma lista de todos os veterinários registrados no banco de dados.
     * 
     * @return Uma lista de objetos {@link Veterinario} com todos os veterinários no banco de dados.
     * @throws DbException Se ocorrer um erro ao realizar a consulta no banco de dados.
     */
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
