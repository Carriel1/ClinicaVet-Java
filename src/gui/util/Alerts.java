package gui.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Classe utilitária para exibir alertas e caixas de confirmação.
 * 
 * Esta classe fornece métodos para mostrar alertas de diferentes tipos 
 * (erro, informação, confirmação, etc.) e para exibir caixas de diálogo de 
 * confirmação que aguardam uma resposta do usuário.
 */
public class Alerts {

    /**
     * Exibe um alerta simples.
     * 
     * @param title O título da janela de alerta.
     * @param header O texto do cabeçalho do alerta.
     * @param content O conteúdo do alerta (mensagem principal).
     * @param type O tipo de alerta (por exemplo, erro, informação).
     */
	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
    /**
     * Exibe uma caixa de diálogo de confirmação com botões para o usuário escolher.
     * 
     * @param title O título da janela de confirmação.
     * @param content O conteúdo da caixa de diálogo (mensagem de confirmação).
     * @return Um objeto {@link Optional<ButtonType>} contendo a resposta do usuário.
     *         Se o usuário clicar em um botão (por exemplo, OK ou Cancelar), o valor será retornado.
     */
	public static Optional<ButtonType> showConfirmation(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		return alert.showAndWait();
	}
}
