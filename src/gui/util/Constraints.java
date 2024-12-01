package gui.util;

import javafx.scene.control.TextField;

/**
 * Classe utilitária para adicionar restrições de entrada em campos de texto.
 * 
 * A classe fornece métodos para restringir a entrada do usuário em campos de texto (`TextField`),
 * permitindo somente números inteiros, números decimais ou controlando o comprimento máximo do texto.
 */
public class Constraints {

    /**
     * Restringe a entrada de texto a números inteiros.
     * 
     * Este método adiciona um ouvinte de mudanças ao campo de texto que impede a inserção de qualquer
     * caractere que não seja um número inteiro.
     * 
     * @param txt O campo de texto (TextField) ao qual a restrição será aplicada.
     */
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\d*")) {
	        	txt.setText(oldValue);
	        }
	    });
	}

    /**
     * Define um comprimento máximo para o texto no campo de texto.
     * 
     * Este método adiciona um ouvinte de mudanças ao campo de texto que impede que o texto exceda o comprimento
     * máximo especificado.
     * 
     * @param txt O campo de texto (TextField) ao qual a restrição será aplicada.
     * @param max O comprimento máximo permitido para o texto.
     */
	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && newValue.length() > max) {
	        	txt.setText(oldValue);
	        }
	    });
	}

    /**
     * Restringe a entrada de texto a números decimais.
     * 
     * Este método adiciona um ouvinte de mudanças ao campo de texto que permite apenas a inserção de números
     * decimais, incluindo números inteiros. O formato aceito é um número seguido opcionalmente de um ponto e 
     * mais dígitos.
     * 
     * @param txt O campo de texto (TextField) ao qual a restrição será aplicada.
     */
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
		    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(oldValue);
                }
		    });
	}
}
