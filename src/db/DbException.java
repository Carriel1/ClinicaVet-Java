package db;

/**
 * Classe customizada para representar exceções específicas de banco de dados.
 * Esta classe estende a classe `RuntimeException` e fornece construtores para diferentes tipos de erros.
 * Pode ser utilizada para lançar exceções relacionadas a falhas de conexão, SQL ou outras operações de banco de dados.
 */
public class DbException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor simples que recebe uma mensagem de erro.
     * 
     * @param msg A mensagem de erro a ser associada à exceção.
     */
    public DbException(String msg) {
        super(msg);
    }

    /**
     * Construtor que recebe uma mensagem de erro e a causa original da exceção.
     * 
     * @param msg A mensagem de erro a ser associada à exceção.
     * @param cause A causa original da exceção (outra exceção que gerou essa).
     */
    public DbException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Construtor que recebe uma mensagem de erro e um código de erro específico.
     * A mensagem será concatenada com o código de erro.
     * 
     * @param msg A mensagem de erro a ser associada à exceção.
     * @param errorCode O código de erro associado à exceção.
     */
    public DbException(String msg, int errorCode) {
        super(msg + " - Error Code: " + errorCode);
    }
}
