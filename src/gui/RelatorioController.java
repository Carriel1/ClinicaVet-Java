package gui;

import db.DB;
import db.DbException;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.dao.RelatorioDao;
import model.dao.impl.RelatorioDaoJDBC;
import model.entities.Consulta;
import model.entities.Relatorio;

/**
 * Controlador para a tela de Relatório de Consultas.
 * Permite visualizar e salvar relatórios de uma consulta específica.
 */
public class RelatorioController {

    private Consulta consulta;  // Consulta associada ao relatório

    @FXML
    private TextArea txtRelatorio;  // Campo de texto para inserir o relatório

    /**
     * Construtor da classe.
     */
    public RelatorioController() {
    }

    /**
     * Define a consulta associada ao relatório e carrega as informações da consulta.
     * 
     * @param consulta A consulta associada ao relatório.
     */
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
        carregarRelatorio();
    }

    /**
     * Carrega as informações da consulta e preenche o campo de texto com os dados do relatório.
     */
    private void carregarRelatorio() {
        if (consulta != null) {
            // Preencher o relatório com informações da consulta
            String relatorio = "Relatório da Consulta\n"
                    + "Cliente: " + consulta.getCliente().getNome() + "\n"
                    + "Animal: " + (consulta.getAnimal() != null ? consulta.getAnimal().getNome() : "Não informado") + "\n"
                    + "Data: " + consulta.getData().toString() + "\n"
                    + "Descrição: " + consulta.getDescricao() + "\n";
            txtRelatorio.setText(relatorio);
        }
    }

    /**
     * Método chamado ao clicar no botão "Salvar".
     * Valida o conteúdo do relatório e salva as informações no banco de dados.
     */
    @FXML
    public void onSalvar() {
        try {
            String conteudoRelatorio = txtRelatorio.getText();

            // Verifica se o relatório está vazio
            if (conteudoRelatorio.isBlank()) {
                Alerts.showAlert("Erro", null, "O relatório está vazio. Preencha-o antes de salvar.", Alert.AlertType.ERROR);
                return;
            }

            // Verifica se o veterinário está atribuído à consulta
            if (consulta.getVeterinario() == null) {
                Alerts.showAlert("Erro", null, "Não há veterinário atribuído à consulta.", Alert.AlertType.ERROR);
                return;
            }

            // Cria um novo objeto Relatório
            Relatorio relatorio = new Relatorio();
            relatorio.setConsulta(consulta);
            relatorio.setVeterinario(consulta.getVeterinario());
            relatorio.setDescricao(conteudoRelatorio);
            relatorio.setDiagnostico("Diagnóstico placeholder"); 
            relatorio.setRecomendacao("Recomendação placeholder"); 
            relatorio.setDataCriacao(java.time.LocalDate.now());

            // Salva o relatório no banco de dados
            RelatorioDao relatorioDao = new RelatorioDaoJDBC(DB.getConnection());
            relatorioDao.insert(relatorio);

            Alerts.showAlert("Sucesso", null, "Relatório salvo com sucesso!", Alert.AlertType.INFORMATION);

            fecharTela();  // Fecha a tela após o salvamento
        }  catch (DbException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha inesperada ao salvar o relatório.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método chamado ao clicar no botão "Voltar".
     * Fecha a tela de relatório e retorna à tela anterior.
     */
    @FXML
    public void onVoltar() {
        fecharTela();
    }

    /**
     * Fecha a tela atual de relatório.
     */
    private void fecharTela() {
        Stage stage = (Stage) txtRelatorio.getScene().getWindow();
        stage.close();
    }
}
