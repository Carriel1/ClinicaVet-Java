package db;

public class DbException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // Construtor simples
    public DbException(String msg) {
        super(msg);
    }

    // Construtor com mensagem e causa
    public DbException(String msg, Throwable cause) {
        super(msg, cause);
    }

    // Construtor com código de erro, se necessário
    public DbException(String msg, int errorCode) {
        super(msg + " - Error Code: " + errorCode);
    }
}
