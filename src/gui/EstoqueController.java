package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EstoqueController {

    // Remédios e suas quantidades
    private int meloxicam = 10;
    private int tramadol = 8;
    private int propofol = 5;
    private int isoflurano = 6;
    private int amoxicilinaComClavulanato = 15;
    private int cefalexina = 12;
    private int prednisona = 10;
    private int metoclopramida = 14;
    private int ondansetrona = 13;
    private int ringer = 20;
    private int soroFisiologico = 18;
    private int ivermectina = 25;
    private int pipetasTratamentoTopico = 30;
    private int aminofilina = 7;
    private int vitaminaK1 = 9;
    private int esterFibrina = 8;
    private int diazepam = 12;
    private int fenobarbital = 10;

    // Labels que exibirão as quantidades dos remédios
    @FXML private Label meloxicamLabel;
    @FXML private Label tramadolLabel;
    @FXML private Label propofolLabel;
    @FXML private Label isofluranoLabel;
    @FXML private Label amoxicilinaLabel;
    @FXML private Label cefalexinaLabel;
    @FXML private Label prednisonaLabel;
    @FXML private Label metoclopramidaLabel;
    @FXML private Label ondansetronaLabel;
    @FXML private Label ringerLabel;
    @FXML private Label soroFisiologicoLabel;
    @FXML private Label ivermectinaLabel;
    @FXML private Label pipetasLabel;
    @FXML private Label aminofilinaLabel;
    @FXML private Label vitaminaK1Label;
    @FXML private Label esterFibrinaLabel;
    @FXML private Label diazepamLabel;
    @FXML private Label fenobarbitalLabel;

    // Método para adicionar/remover do estoque
    private void atualizarEstoque(Label label, int quantidade) {
        int novoEstoque = Integer.parseInt(label.getText()) + quantidade;
        label.setText(String.valueOf(novoEstoque));
    }

    // Métodos de adicionar/remover para cada remédio
    @FXML
    public void adicionarMeloxicam(ActionEvent event) {
        atualizarEstoque(meloxicamLabel, 1);
    }

    @FXML
    public void removerMeloxicam(ActionEvent event) {
        atualizarEstoque(meloxicamLabel, -1);
    }

    @FXML
    public void adicionarTramadol(ActionEvent event) {
        atualizarEstoque(tramadolLabel, 1);
    }

    @FXML
    public void removerTramadol(ActionEvent event) {
        atualizarEstoque(tramadolLabel, -1);
    }

    @FXML
    public void adicionarPropofol(ActionEvent event) {
        atualizarEstoque(propofolLabel, 1);
    }

    @FXML
    public void removerPropofol(ActionEvent event) {
        atualizarEstoque(propofolLabel, -1);
    }

    @FXML
    public void adicionarIsoflurano(ActionEvent event) {
        atualizarEstoque(isofluranoLabel, 1);
    }

    @FXML
    public void removerIsoflurano(ActionEvent event) {
        atualizarEstoque(isofluranoLabel, -1);
    }

    @FXML
    public void adicionarAmoxicilina(ActionEvent event) {
        atualizarEstoque(amoxicilinaLabel, 1);
    }

    @FXML
    public void removerAmoxicilina(ActionEvent event) {
        atualizarEstoque(amoxicilinaLabel, -1);
    }

    @FXML
    public void adicionarCefalexina(ActionEvent event) {
        atualizarEstoque(cefalexinaLabel, 1);
    }

    @FXML
    public void removerCefalexina(ActionEvent event) {
        atualizarEstoque(cefalexinaLabel, -1);
    }

    @FXML
    public void adicionarPrednisona(ActionEvent event) {
        atualizarEstoque(prednisonaLabel, 1);
    }

    @FXML
    public void removerPrednisona(ActionEvent event) {
        atualizarEstoque(prednisonaLabel, -1);
    }

    @FXML
    public void adicionarMetoclopramida(ActionEvent event) {
        atualizarEstoque(metoclopramidaLabel, 1);
    }

    @FXML
    public void removerMetoclopramida(ActionEvent event) {
        atualizarEstoque(metoclopramidaLabel, -1);
    }

    @FXML
    public void adicionarOndansetrona(ActionEvent event) {
        atualizarEstoque(ondansetronaLabel, 1);
    }

    @FXML
    public void removerOndansetrona(ActionEvent event) {
        atualizarEstoque(ondansetronaLabel, -1);
    }

    @FXML
    public void adicionarRinger(ActionEvent event) {
        atualizarEstoque(ringerLabel, 1);
    }

    @FXML
    public void removerRinger(ActionEvent event) {
        atualizarEstoque(ringerLabel, -1);
    }

    @FXML
    public void adicionarSoroFisiologico(ActionEvent event) {
        atualizarEstoque(soroFisiologicoLabel, 1);
    }

    @FXML
    public void removerSoroFisiologico(ActionEvent event) {
        atualizarEstoque(soroFisiologicoLabel, -1);
    }

    @FXML
    public void adicionarIvermectina(ActionEvent event) {
        atualizarEstoque(ivermectinaLabel, 1);
    }

    @FXML
    public void removerIvermectina(ActionEvent event) {
        atualizarEstoque(ivermectinaLabel, -1);
    }

    @FXML
    public void adicionarPipetas(ActionEvent event) {
        atualizarEstoque(pipetasLabel, 1);
    }

    @FXML
    public void removerPipetas(ActionEvent event) {
        atualizarEstoque(pipetasLabel, -1);
    }

    @FXML
    public void adicionarAminofilina(ActionEvent event) {
        atualizarEstoque(aminofilinaLabel, 1);
    }

    @FXML
    public void removerAminofilina(ActionEvent event) {
        atualizarEstoque(aminofilinaLabel, -1);
    }

    @FXML
    public void adicionarVitaminaK1(ActionEvent event) {
        atualizarEstoque(vitaminaK1Label, 1);
    }

    @FXML
    public void removerVitaminaK1(ActionEvent event) {
        atualizarEstoque(vitaminaK1Label, -1);
    }

    @FXML
    public void adicionarEsterFibrina(ActionEvent event) {
        atualizarEstoque(esterFibrinaLabel, 1);
    }

    @FXML
    public void removerEsterFibrina(ActionEvent event) {
        atualizarEstoque(esterFibrinaLabel, -1);
    }

    @FXML
    public void adicionarDiazepam(ActionEvent event) {
        atualizarEstoque(diazepamLabel, 1);
    }

    @FXML
    public void removerDiazepam(ActionEvent event) {
        atualizarEstoque(diazepamLabel, -1);
    }

    @FXML
    public void adicionarFenobarbital(ActionEvent event) {
        atualizarEstoque(fenobarbitalLabel, 1);
    }

    @FXML
    public void removerFenobarbital(ActionEvent event) {
        atualizarEstoque(fenobarbitalLabel, -1);
    }

    // Método para controle de estoque
    @FXML
    public void onControleEstoque(ActionEvent event) {
        System.out.println("Controle de Estoque.");
    }
}
