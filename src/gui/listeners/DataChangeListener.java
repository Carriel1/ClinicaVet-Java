package gui.listeners;

/**
 * Interface que define um ouvinte para mudanças de dados.
 * 
 * Esta interface deve ser implementada por classes que desejam ser notificadas
 * quando os dados de um objeto forem alterados, como em um formulário ou em um 
 * modelo de dados.
 */
public interface DataChangeListener {
    
    /**
     * Método chamado quando ocorre uma mudança nos dados.
     * 
     * Este método deve ser implementado para realizar as ações necessárias
     * quando os dados forem alterados, como atualizar a interface do usuário
     * ou realizar outras operações baseadas na mudança de dados.
     */
    void onDataChanged();
}
