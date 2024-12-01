package model.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Exceção personalizada para lidar com erros de validação de dados.
 * Essa classe estende {@link RuntimeException} e permite armazenar um conjunto de erros associados a campos específicos.
 * 
 * A principal vantagem dessa exceção é que ela pode agrupar múltiplos erros de validação em um único objeto,
 * permitindo que a lógica de validação continue mesmo após encontrar erros em vários campos.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Mapa que armazena os erros de validação, com o nome do campo como chave e a mensagem de erro como valor.
     */
    private Map<String, String> errors = new HashMap<>();
    
    /**
     * Construtor da exceção que recebe uma mensagem geral.
     * 
     * @param msg A mensagem que descreve o erro.
     */
    public ValidationException(String msg) {
        super(msg);
    }

    /**
     * Retorna o mapa de erros, onde a chave é o nome do campo e o valor é a mensagem de erro associada.
     * 
     * @return O mapa contendo os erros de validação.
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Adiciona um erro ao mapa de erros de validação. 
     * O erro é associado a um campo específico, identificado pelo nome do campo e pela mensagem de erro.
     * 
     * @param fieldName O nome do campo que falhou na validação.
     * @param errorMessage A mensagem de erro associada ao campo.
     */
    public void addError(String fieldName, String errorMessage) {
        errors.put(fieldName, errorMessage);
    }
}
