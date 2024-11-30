package gui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.Relatorio;
import model.entities.Veterinario;
import model.services.RelatorioService;

public class VerRelatoriosController {

    @FXML
    private TableView<Relatorio> tableViewRelatorios;

    @FXML
    private TableColumn<Relatorio, String> colRelatorio;

    @FXML
    private TableColumn<Relatorio, String> colVeterinario;

    private RelatorioService relatorioService;
    
    // Este método deve ser chamado para injetar o serviço
    public void setRelatorioService(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }
    
    @FXML
    public void initialize() {
    	relatorioService = new RelatorioService();
    	
        colRelatorio.setCellValueFactory(cellData -> cellData.getValue().getRelatorioProperty());
        colVeterinario.setCellValueFactory(cellData -> cellData.getValue().getVeterinarioResponsavelProperty());
        
        // Carrega os dados
        carregarRelatorios();
    }

    private void carregarRelatorios() {
        // Obter a lista de relatórios com veterinários
        List<Relatorio> relatorios = relatorioService.findAllRelatoriosComVeterinario();
        
        // Garantir que o campo veterinarioResponsavel está preenchido
        for (Relatorio relatorio : relatorios) {
            if (relatorio.getVeterinarioResponsavel() == null) {
                // Atribuir um veterinário fictício ou um valor padrão, se necessário
                relatorio.setVeterinarioResponsavel(new Veterinario("Veterinário Desconhecido"));
            }
        }

        // Atualiza a tabela com os dados
        tableViewRelatorios.getItems().setAll(relatorios);
    }

}
