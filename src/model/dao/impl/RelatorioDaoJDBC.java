package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.RelatorioDao;
import model.dao.VeterinarioDao;
import model.entities.Consulta;
import model.entities.Relatorio;
import model.entities.Veterinario;

/**
 * Implementação da interface {@link RelatorioDao} para operações CRUD de relatórios no banco de dados.
 * Esta classe utiliza JDBC para realizar as operações de inserção, atualização, exclusão, e busca de relatórios.
 */
public class RelatorioDaoJDBC implements RelatorioDao {

    private Connection conn;

    /**
     * Construtor padrão da classe {@link RelatorioDaoJDBC}.
     * Cria uma instância sem conexão (será necessário configurar a conexão posteriormente).
     */
    public RelatorioDaoJDBC() {}

    /**
     * Construtor da classe {@link RelatorioDaoJDBC} com uma conexão já existente.
     * 
     * @param conn A conexão JDBC a ser utilizada para realizar as operações no banco de dados.
     */
    public RelatorioDaoJDBC(Connection conn) {
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
     * Insere um novo relatório no banco de dados.
     * 
     * @param relatorio O objeto {@link Relatorio} a ser inserido.
     * @throws DbException Se ocorrer um erro ao realizar a inserção no banco de dados ou se o veterinário não for atribuído.
     */
    @Override
    public void insert(Relatorio relatorio) {
        if (relatorio.getVeterinario() == null) {
            throw new DbException("Veterinário não atribuído ao relatório.");
        }

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

    /**
     * Atualiza um relatório existente no banco de dados.
     * 
     * @param relatorio O objeto {@link Relatorio} com os dados atualizados.
     * @throws DbException Se ocorrer um erro ao realizar a atualização no banco de dados.
     */
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

    /**
     * Exclui um relatório do banco de dados com base no ID.
     * 
     * @param id O ID do relatório a ser excluído.
     * @throws DbException Se ocorrer um erro ao realizar a exclusão no banco de dados.
     */
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

    /**
     * Busca um relatório no banco de dados com base no seu ID.
     * 
     * @param id O ID do relatório a ser buscado.
     * @return O relatório encontrado, ou {@code null} se não houver relatório com o ID especificado.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
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

    /**
     * Retorna todos os relatórios cadastrados no banco de dados.
     * 
     * @return Uma lista de {@link Relatorio} contendo todos os relatórios.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
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

    /**
     * Retorna todos os relatórios de uma consulta específica.
     * 
     * @param consultaId O ID da consulta associada aos relatórios.
     * @return Uma lista de {@link Relatorio} associados à consulta.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
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

    /**
     * Retorna todos os relatórios de um veterinário específico.
     * 
     * @param veterinarioId O ID do veterinário associado aos relatórios.
     * @return Uma lista de {@link Relatorio} associados ao veterinário.
     * @throws DbException Se ocorrer um erro ao realizar a busca no banco de dados.
     */
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

    /**
     * Instancia um objeto {@link Relatorio} a partir de um {@link ResultSet}.
     * 
     * @param rs O {@link ResultSet} contendo os dados do relatório.
     * @return O objeto {@link Relatorio} instanciado com os dados do {@link ResultSet}.
     * @throws SQLException Se ocorrer um erro ao extrair os dados do {@link ResultSet}.
     */
    private Relatorio instantiateRelatorio(ResultSet rs) throws SQLException {
        Relatorio relatorio = new Relatorio();
        relatorio.setId(rs.getInt("id"));
        
        Consulta consulta = new Consulta();
        consulta.setId(rs.getInt("consulta_id"));
        relatorio.setConsulta(consulta);
        
        // Carrega o Veterinário completo ao invés de apenas setar o ID
        VeterinarioDao veterinarioDao = new VeterinarioDaoJDBC(conn);
        Veterinario veterinario = veterinarioDao.findById(rs.getInt("veterinario_id"));
        relatorio.setVeterinario(veterinario);
        
        relatorio.setDescricao(rs.getString("descricao"));
        relatorio.setDiagnostico(rs.getString("diagnostico"));
        relatorio.setRecomendacao(rs.getString("recomendacao"));
        relatorio.setDataCriacao(rs.getDate("dataCriacao").toLocalDate());
        
        return relatorio;
    }

    /**
     * Retorna todos os relatórios com os nomes dos veterinários associados.
     * 
     * @return Uma lista de {@link Relatorio} contendo os relatórios e os nomes dos veterinários associados.
     */
    public List<Relatorio> findAllComVeterinario() {
        List<Relatorio> relatorios = new ArrayList<>();

        String sql = "SELECT r.*, v.nome AS veterinario_nome FROM relatorio r "
                   + "JOIN veterinario v ON r.veterinario_id = v.id";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setId(rs.getInt("id"));
                relatorio.setDescricao(rs.getString("descricao"));

                Veterinario veterinario = new Veterinario();
                veterinario.setNome(rs.getString("veterinario_nome"));

                relatorio.setVeterinario(veterinario);  
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorios;
    }
}
