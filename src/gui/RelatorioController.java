package gui;

import java.sql.SQLException;

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

public class RelatorioController {

    private Consulta consulta;

    @FXML
    private TextArea txtRelatorio;

    public RelatorioController() {
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
        carregarRelatorio();
    }

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

    @FXML
    public void onSalvar() {
        try {
            String conteudoRelatorio = txtRelatorio.getText();

            if (conteudoRelatorio.isBlank()) {
                Alerts.showAlert("Erro", null, "O relatório está vazio. Preencha-o antes de salvar.", Alert.AlertType.ERROR);
                return;
            }

            // Verificar se o veterinário não é null
            if (consulta.getVeterinario() == null) {
                Alerts.showAlert("Erro", null, "Não há veterinário atribuído à consulta.", Alert.AlertType.ERROR);
                return;
            }

            // Criar objeto Relatorio
            Relatorio relatorio = new Relatorio();
            relatorio.setConsulta(consulta);
            relatorio.setVeterinario(consulta.getVeterinario());
            relatorio.setDescricao(conteudoRelatorio);
            relatorio.setDiagnostico("Diagnóstico placeholder"); 
            relatorio.setRecomendacao("Recomendação placeholder"); 
            relatorio.setDataCriacao(java.time.LocalDate.now());

            // Salvar no banco de dados
            RelatorioDao relatorioDao = new RelatorioDaoJDBC(DB.getConnection());
            relatorioDao.insert(relatorio);

            Alerts.showAlert("Sucesso", null, "Relatório salvo com sucesso!", Alert.AlertType.INFORMATION);

            fecharTela();
        }  catch (DbException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha inesperada ao salvar o relatório.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void onVoltar() {
        fecharTela();
    }

    private void fecharTela() {
        Stage stage = (Stage) txtRelatorio.getScene().getWindow();
        stage.close();
    }
}

