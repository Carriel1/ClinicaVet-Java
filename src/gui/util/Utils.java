package gui.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária contendo métodos diversos para facilitar o uso de funcionalidades
 * comuns no aplicativo, como carregamento de cenas, formatação de colunas de tabelas,
 * conversões de tipos e manipulação de datas.
 */
public class Utils {

    /**
     * Obtém o Stage (janela) atual a partir de um evento.
     * 
     * @param event O evento gerado pelo usuário (geralmente um clique).
     * @return O Stage atual que disparou o evento.
     * @throws IllegalArgumentException Caso o evento ou sua origem seja nula.
     */
    public static Stage currentStage(ActionEvent event) {
        if (event == null || event.getSource() == null) {
            throw new IllegalArgumentException("O evento ou a origem do evento está nulo.");
        }
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    /**
     * Carrega uma nova cena a partir de um arquivo FXML.
     * 
     * @param fxmlPath O caminho do arquivo FXML.
     * @return A cena carregada a partir do arquivo FXML.
     */
    public static Scene loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlPath));
            Parent root = loader.load(); 
            return new Scene(root); 
        } catch (IOException e) {
            e.printStackTrace(); 
            return null; 
        }
    }

    /**
     * Obtém o controlador da cena carregada.
     * 
     * @param scene A cena da qual se deseja obter o controlador.
     * @return O controlador da cena carregada.
     */
    public static Object getController(Scene scene) {
        if (scene != null && scene.getRoot() != null) {
            return scene.getRoot().getProperties().get("controller");
        }
        return null;
    }

    /**
     * Tenta converter uma String para Integer, retornando null em caso de erro.
     * 
     * @param str A string a ser convertida.
     * @return O valor convertido como Integer ou null caso ocorra um erro.
     */
    public static Integer tryParseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Tenta converter uma String para Double, retornando null em caso de erro.
     * 
     * @param str A string a ser convertida.
     * @return O valor convertido como Double ou null caso ocorra um erro.
     */
    public static Double tryParseToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Formata uma coluna de data em uma tabela, permitindo o formato especificado.
     * 
     * @param <T> O tipo da tabela.
     * @param tableColumn A coluna da tabela a ser formatada.
     * @param format O formato da data (por exemplo, "dd/MM/yyyy").
     */
    public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Date> cell = new TableCell<T, Date>() {
                private SimpleDateFormat sdf = new SimpleDateFormat(format);

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : sdf.format(item));
                }
            };
            return cell;
        });
    }

    /**
     * Formata uma coluna de valores Double em uma tabela, com o número de casas decimais especificado.
     * 
     * @param <T> O tipo da tabela.
     * @param tableColumn A coluna da tabela a ser formatada.
     * @param decimalPlaces O número de casas decimais.
     */
    public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Double> cell = new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Locale.setDefault(Locale.US);
                        setText(String.format("%." + decimalPlaces + "f", item));
                    }
                }
            };
            return cell;
        });
    }

    /**
     * Formata um DatePicker, permitindo definir o formato de data exibido e aceito.
     * 
     * @param datePicker O DatePicker a ser formatado.
     * @param format O formato desejado para a data (por exemplo, "dd/MM/yyyy").
     */
    public static void formatDatePicker(DatePicker datePicker, String format) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
        datePicker.setPromptText(format.toLowerCase());

        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
    }

    /**
     * Converte uma data (Date) para uma string no formato especificado.
     * 
     * @param date A data a ser convertida.
     * @param format O formato desejado (por exemplo, "dd/MM/yyyy").
     * @return A data formatada como String.
     */
    public static String formatDateToString(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Converte uma string no formato especificado para uma data (Date).
     * 
     * @param dateStr A string representando uma data.
     * @param format O formato da data na string.
     * @return A data convertida ou null se ocorrer um erro de parsing.
     */
    public static Date parseStringToDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
