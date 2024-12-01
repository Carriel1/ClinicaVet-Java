package db;

/**
 * Exceção customizada para erros de integridade no banco de dados.
 * Esta classe estende `RuntimeException` e é utilizada para representar
 * falhas relacionadas à violação de restrições de integridade no banco de dados,
 * como chave estrangeira ou chave primária.
 */
public class DbIntegrityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe uma mensagem de erro.
     * 
     * @param msg A mensagem de erro a ser associada à exceção.
     */
    public DbIntegrityException(String msg) {
        super(msg);
    }
}
