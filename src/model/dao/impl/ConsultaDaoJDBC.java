package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ConsultaDao;
import model.dao.VeterinarioDao;
import model.entities.Animal;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Veterinario;

public class ConsultaDaoJDBC implements ConsultaDao {
    private Connection conn;

    public ConsultaDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    
    public ConsultaDaoJDBC () {
    	
    }
    
    // Método para verificar e reabrir a conexão, se necessário
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
    public void insert(Consulta consulta) {
    	if (consulta.getCliente() == null || consulta.getCliente().getId() == null) {
    	    throw new IllegalArgumentException("Cliente inválido ou sem ID configurado na consulta.");
    	}

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
            if (consulta.getVeterinario() != null) {
                st.setInt(6, consulta.getVeterinario().getId());
            } else {
                st.setNull(6, java.sql.Types.INTEGER);  // Utiliza o tipo correto de dados para null
            }
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
        String sql = "UPDATE consulta SET descricao = ?, hora = ?, data = ? WHERE id = ?";

        try {
            // Verifica se a conexão está fechada e reabre, se necessário
            if (conn == null || conn.isClosed()) {
                conn = getConnection(); // Método para obter conexão
            }

            try (PreparedStatement st = conn.prepareStatement(sql)) {
                // Define os parâmetros na ordem correta
                st.setString(1, consulta.getDescricao());             // descricao
                st.setTime(2, Time.valueOf(consulta.getHora()));      // hora
                st.setDate(3, Date.valueOf(consulta.getData()));      // data
                st.setInt(4, consulta.getId());                       // id

                // Executa a atualização
                st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar consulta: " + e.getMessage());
        }
    }


    private Connection getConnection() throws SQLException {
        // Verifica se a conexão já foi estabelecida, caso contrário, cria uma nova conexão.
        if (conn == null || conn.isClosed()) {
            conn = DB.getConnection();  // Supondo que o método DB.getConnection() seja utilizado para obter a conexão
        }
        return conn;
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM consulta WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                
                // Verificação de null para a data
                LocalDate data = rs.getDate("data") != null ? rs.getDate("data").toLocalDate() : null;
                consulta.setData(data);

                // Atribuindo Veterinário à consulta
                VeterinarioDao veterinarioDao = new VeterinarioDaoJDBC(conn);
                Veterinario veterinario = veterinarioDao.findById(rs.getInt("veterinario_id"));

                // Verificando se o veterinário é null antes de atribuir
                if (veterinario != null) {
                    consulta.setVeterinario(veterinario);
                } else {
                    consulta.setVeterinario(null);  // Ou lançar uma exceção, dependendo da lógica do sistema
                }

                return consulta;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar consulta com id " + id + ": " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }



    public List<Consulta> findAll() {
        verificarConexao();  // Verifica e reabre a conexão se necessário
        try {
            // Atualizando a consulta para buscar todos os dados necessários
            String sql = "SELECT c.id, c.descricao, c.data, c.hora, c.status, c.criadoPor, " + 
                         "a.id AS animal_id, a.nome AS animal_nome, a.idade AS animal_idade, a.raca AS animal_raca, a.especie AS animal_especie, " +
                         "cl.id AS clienteid, cl.nome AS cliente_nome, cl.email AS cliente_email, cl.telefone AS cliente_telefone, cl.senha AS cliente_senha, cl.endereco AS cliente_endereco, cl.cpf AS cliente_cpf, " +
                         "v.id AS veterinarioid, v.nome AS veterinario_nome, v.cpf AS veterinario_cpf, v.email AS veterinario_email, v.telefone AS veterinario_telefone, v.senha AS veterinario_senha " + 
                         "FROM consulta c " +
                         "LEFT JOIN animais a ON c.animal_id = a.id " +
                         "LEFT JOIN cliente cl ON c.clienteid = cl.id " +
                         "LEFT JOIN veterinario v ON c.veterinarioid = v.id";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Consulta> consultas = new ArrayList<>();
            
            while (rs.next()) {
                // Criando Cliente
                Cliente cliente = new Cliente(
                    rs.getInt("clienteid"), 
                    rs.getString("cliente_nome"),
                    rs.getString("cliente_email"),
                    rs.getString("cliente_telefone"),
                    rs.getString("cliente_senha"),
                    rs.getString("cliente_endereco"),
                    rs.getString("cliente_cpf")
                );
                
                // Criando Veterinário
                Veterinario veterinario = new Veterinario(
                    rs.getInt("veterinarioid"), 
                    rs.getString("veterinario_nome"),
                    rs.getString("veterinario_cpf"),
                    rs.getString("veterinario_email"),
                    rs.getString("veterinario_telefone"),
                    rs.getString("veterinario_senha")
                );
                
                // Criando Animal, se existir
                Animal animal = null;
                int animalId = rs.getInt("animal_id");
                if (animalId != 0) {
                    animal = new Animal(
                        animalId, 
                        rs.getString("animal_nome"),
                        rs.getInt("animal_idade"),
                        rs.getString("animal_raca"),
                        rs.getString("animal_especie"),
                        cliente  // Associando o cliente ao animal
                    );
                }
                
                // Criando a data e hora
                LocalDate data = rs.getDate("data").toLocalDate();
                LocalTime hora = rs.getTime("hora").toLocalTime();
                
                // Criando a consulta
                Consulta consulta = new Consulta(
                    rs.getInt("id"), 
                    cliente, 
                    veterinario, 
                    data, 
                    hora, 
                    rs.getString("descricao"), 
                    rs.getString("status"), 
                    rs.getString("criadoPor"), 
                    animal
                );
                
                // Adicionando a consulta à lista
                consultas.add(consulta);
            }
            return consultas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar o banco de dados", e);
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
                a.id AS animal_id, a.nome AS animal_nome,
                v.id AS veterinario_id, v.nome AS veterinario_nome
            FROM consulta c 
            JOIN cliente cl ON c.clienteid = cl.id 
            JOIN animais a ON c.animal_id = a.id 
            LEFT JOIN veterinario v ON c.veterinarioid = v.id  -- Adiciona a junção com veterinários
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

                // Veterinário
                Veterinario veterinario = null;
                int veterinarioId = rs.getInt("veterinario_id");
                if (veterinarioId > 0) {
                    veterinario = new Veterinario();
                    veterinario.setId(veterinarioId);
                    veterinario.setNome(rs.getString("veterinario_nome"));
                }

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
                consulta.setVeterinario(veterinario); // Atribuindo o veterinário, se presente

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

    @Override
    public List<Consulta> findAllPendentes() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE status = 'pendente'";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
           //     consulta.setClienteNome(rs.getString("cliente_nome"));
              //  consulta.setAnimalNome(rs.getString("animal_nome"));
                consulta.setDataSolicitacao(rs.getDate("data"));
                consulta.setStatus(rs.getString("status"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultas;
    }

    @Override
    public void updateStatus(Consulta consulta, String status) {
        String sql = "UPDATE consulta SET status = ? WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, status);  // 'aceita' ou 'negada'
            pst.setInt(2, consulta.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
    @Override
    public List<Consulta> findAllRequisitadas() {
        List<Consulta> consultas = new ArrayList<>();
        // SQL com JOIN para trazer o nome do cliente e do animal
        String sql = "SELECT c.id, a.nome AS animal_nome, cl.nome AS cliente_nome, c.status, c.data " +
                     "FROM consulta c " +
                     "JOIN animais a ON c.animal_id = a.id " +  // Relacionando consulta com animal
                     "JOIN cliente cl ON c.clienteid = cl.id " +  // Relacionando consulta com cliente
                     "WHERE c.status = 'Requisitada'";  // Alterando o status para "Requisitada"

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setAnimalNome(rs.getString("animal_nome"));  // Nome do animal
                consulta.setClienteNome(rs.getString("cliente_nome"));  // Nome do cliente
                consulta.setDataSolicitacao(rs.getDate("data"));
                consulta.setStatus(rs.getString("status"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultas;
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
