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

public class Utils {

    // Método para obter o Stage atual a partir de um evento
    public static Stage currentStage(ActionEvent event) {
        if (event == null || event.getSource() == null) {
            throw new IllegalArgumentException("O evento ou a origem do evento está nulo.");
        }
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    // Método para carregar uma nova cena a partir de um arquivo FXML
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

    
    // Método para obter o controlador da cena carregada
    public static Object getController(Scene scene) {
        if (scene != null && scene.getRoot() != null) {
            return scene.getRoot().getProperties().get("controller");
        }
        return null;
    }

    // Método para tentar converter String para Integer, retornando null em caso de erro
    public static Integer tryParseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Método para tentar converter String para Double, retornando null em caso de erro
    public static Double tryParseToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Método para formatar colunas de data em tabelas, recebendo o formato como parâmetro
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

    // Método para formatar colunas de valores Double em tabelas, especificando casas decimais
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

    // Método para formatar o DatePicker, permitindo definir o formato de data exibido e aceito
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

    // Método adicional para converter Date para String, com suporte ao formato desejado
    public static String formatDateToString(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    // Método adicional para converter String para Date, com suporte a formatos específicos
    public static Date parseStringToDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
