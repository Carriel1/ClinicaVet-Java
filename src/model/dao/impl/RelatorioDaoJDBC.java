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

public class RelatorioDaoJDBC implements RelatorioDao {
    private Connection conn;
    
    public RelatorioDaoJDBC () {
    	
    }
    
    public RelatorioDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
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
    
    @Override
    public void insert(Relatorio relatorio) {
        // Verificar se o veterinário está presente antes de tentar inseri-lo no banco
        if (relatorio.getVeterinario() == null) {
            throw new DbException("Veterinário não atribuído ao relatório.");
        }

        String sql = """
            INSERT INTO Relatorio (consulta_id, veterinario_id, descricao, diagnostico, recomendacao, dataCriacao) 
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, relatorio.getConsulta().getId());
            st.setInt(2, relatorio.getVeterinario().getId()); // Agora, você pode acessar o veterinário sem problemas
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

        public List<Relatorio> findAllComVeterinario() {
            List<Relatorio> relatorios = new ArrayList<>();

            // Exemplo de código para consultar os relatórios e incluir os veterinários
            String sql = "SELECT r.*, v.nome AS veterinario_nome FROM relatorio r "
                       + "JOIN veterinario v ON r.veterinario_id = v.id";

            try (Connection conn = DB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Relatorio relatorio = new Relatorio();
                    relatorio.setId(rs.getInt("id"));
                    relatorio.setDescricao(rs.getString("descricao"));
                    // Adicione outros campos do relatório conforme necessário

                    Veterinario veterinario = new Veterinario();
                    veterinario.setNome(rs.getString("veterinario_nome"));
                    // Preencha outros dados do veterinário

                    relatorio.setVeterinario(veterinario);  // Supondo que Relatorio tenha um veterinario
                    relatorios.add(relatorio);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return relatorios;
        }
}
