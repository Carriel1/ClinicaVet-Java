package gui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.Relatorio;
import model.entities.Veterinario;
import model.services.RelatorioService;

/**
 * Controlador responsável por gerenciar a tela de visualização de relatórios.
 * A tela exibe uma lista de relatórios gerados por veterinários, com a possibilidade de mostrar o nome do veterinário responsável.
 */
public class VerRelatoriosController {

    @FXML
    private TableView<Relatorio> tableViewRelatorios;

    @FXML
    private TableColumn<Relatorio, String> colRelatorio;

    @FXML
    private TableColumn<Relatorio, String> colVeterinario;

    private RelatorioService relatorioService;
    
    /**
     * Define o serviço de relatórios a ser utilizado por este controlador.
     * 
     * @param relatorioService O serviço responsável pelo gerenciamento de relatórios.
     */
    public void setRelatorioService(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }
    
    /**
     * Inicializa o controlador, configurando as colunas da tabela e carregando os relatórios.
     * Este método é automaticamente chamado quando o FXML é carregado.
     */
    @FXML
    public void initialize() {
        relatorioService = new RelatorioService();
        
        // Configuração das colunas da tabela
        colRelatorio.setCellValueFactory(cellData -> cellData.getValue().getRelatorioProperty());
        colVeterinario.setCellValueFactory(cellData -> cellData.getValue().getVeterinarioResponsavelProperty());
        
        // Carrega os dados de relatórios
        carregarRelatorios();
    }

    /**
     * Carrega os relatórios de veterinários e os exibe na tabela.
     * Esse método garante que o campo de veterinário responsável não seja nulo, 
     * atribuindo um veterinário fictício caso necessário.
     */
    private void carregarRelatorios() {
        // Obtém a lista de relatórios com veterinários
        List<Relatorio> relatorios = relatorioService.findAllRelatoriosComVeterinario();
        
        // Verifica se o veterinário responsável está preenchido, caso contrário, atribui um veterinário fictício
        for (Relatorio relatorio : relatorios) {
            if (relatorio.getVeterinarioResponsavel() == null) {
                // Atribui um veterinário fictício
                relatorio.setVeterinarioResponsavel(new Veterinario("Veterinário Desconhecido"));
            }
        }

        // Atualiza a tabela com os dados dos relatórios
        tableViewRelatorios.getItems().setAll(relatorios);
    }

}
