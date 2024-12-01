package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe responsável pela conexão com o banco de dados.
 * Esta classe gerencia a abertura e o fechamento da conexão, além de fornecer métodos auxiliares
 * para o gerenciamento de objetos relacionados ao banco de dados, como `Statement` e `ResultSet`.
 */
public class DB {

    /**
     * Conexão com o banco de dados.
     */
	private static Connection conn = null;
	
    /**
     * Obtém a conexão com o banco de dados. Caso não exista uma conexão aberta, cria uma nova.
     * 
     * @return A conexão com o banco de dados.
     * @throws DbException Se ocorrer um erro ao abrir a conexão.
     */
	public static Connection getConnection() {
	    try {
	        if (conn == null || conn.isClosed()) {  // Verifica se a conexão está fechada
	            Properties props = loadProperties();
	            String url = props.getProperty("dburl");
	            conn = DriverManager.getConnection(url, props);
	        }
	    } catch (SQLException e) {
	        throw new DbException("Erro ao abrir conexão: " + e.getMessage());
	    }
	    return conn;
	}
	
    /**
     * Fecha a conexão com o banco de dados, caso esteja aberta.
     * 
     * @throws DbException Se ocorrer um erro ao fechar a conexão.
     */
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
    /**
     * Carrega as propriedades de configuração do banco de dados a partir de um arquivo `db.properties`.
     * 
     * @return As propriedades carregadas do arquivo de configuração.
     * @throws DbException Se ocorrer um erro ao carregar as propriedades.
     */
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
    /**
     * Fecha o objeto `Statement`, se estiver aberto.
     * 
     * @param st O `Statement` a ser fechado.
     * @throws DbException Se ocorrer um erro ao fechar o `Statement`.
     */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

    /**
     * Fecha o objeto `ResultSet`, se estiver aberto.
     * 
     * @param rs O `ResultSet` a ser fechado.
     * @throws DbException Se ocorrer um erro ao fechar o `ResultSet`.
     */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
