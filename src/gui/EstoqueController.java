package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador da tela de gerenciamento de estoque de medicamentos da clínica veterinária.
 * Este controlador permite adicionar ou remover medicamentos do estoque e exibir as quantidades atuais
 * na interface gráfica. A interface inclui medicamentos como Meloxicam, Tramadol, Propofol, entre outros.
 */
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

    /**
     * Atualiza o estoque de um medicamento específico, adicionando ou removendo unidades.
     *
     * @param label O Label que exibe a quantidade atual do medicamento.
     * @param quantidade A quantidade a ser adicionada ou removida. Um valor positivo adiciona, um valor negativo remove.
     */
    private void atualizarEstoque(Label label, int quantidade) {
        int novoEstoque = Integer.parseInt(label.getText()) + quantidade;
        label.setText(String.valueOf(novoEstoque));
    }

    // Métodos de adicionar/remover para cada remédio

    /**
     * Adiciona 1 unidade de Meloxicam ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarMeloxicam(ActionEvent event) {
        atualizarEstoque(meloxicamLabel, 1);
    }

    /**
     * Remove 1 unidade de Meloxicam do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerMeloxicam(ActionEvent event) {
        atualizarEstoque(meloxicamLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Tramadol ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarTramadol(ActionEvent event) {
        atualizarEstoque(tramadolLabel, 1);
    }

    /**
     * Remove 1 unidade de Tramadol do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerTramadol(ActionEvent event) {
        atualizarEstoque(tramadolLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Propofol ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarPropofol(ActionEvent event) {
        atualizarEstoque(propofolLabel, 1);
    }

    /**
     * Remove 1 unidade de Propofol do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerPropofol(ActionEvent event) {
        atualizarEstoque(propofolLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Isoflurano ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarIsoflurano(ActionEvent event) {
        atualizarEstoque(isofluranoLabel, 1);
    }

    /**
     * Remove 1 unidade de Isoflurano do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerIsoflurano(ActionEvent event) {
        atualizarEstoque(isofluranoLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Amoxicilina ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarAmoxicilina(ActionEvent event) {
        atualizarEstoque(amoxicilinaLabel, 1);
    }

    /**
     * Remove 1 unidade de Amoxicilina do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerAmoxicilina(ActionEvent event) {
        atualizarEstoque(amoxicilinaLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Cefalexina ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarCefalexina(ActionEvent event) {
        atualizarEstoque(cefalexinaLabel, 1);
    }

    /**
     * Remove 1 unidade de Cefalexina do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerCefalexina(ActionEvent event) {
        atualizarEstoque(cefalexinaLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Prednisona ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarPrednisona(ActionEvent event) {
        atualizarEstoque(prednisonaLabel, 1);
    }

    /**
     * Remove 1 unidade de Prednisona do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerPrednisona(ActionEvent event) {
        atualizarEstoque(prednisonaLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Metoclopramida ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarMetoclopramida(ActionEvent event) {
        atualizarEstoque(metoclopramidaLabel, 1);
    }

    /**
     * Remove 1 unidade de Metoclopramida do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerMetoclopramida(ActionEvent event) {
        atualizarEstoque(metoclopramidaLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Ondansetrona ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarOndansetrona(ActionEvent event) {
        atualizarEstoque(ondansetronaLabel, 1);
    }

    /**
     * Remove 1 unidade de Ondansetrona do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerOndansetrona(ActionEvent event) {
        atualizarEstoque(ondansetronaLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Ringer ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarRinger(ActionEvent event) {
        atualizarEstoque(ringerLabel, 1);
    }

    /**
     * Remove 1 unidade de Ringer do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerRinger(ActionEvent event) {
        atualizarEstoque(ringerLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Soro Fisiológico ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarSoroFisiologico(ActionEvent event) {
        atualizarEstoque(soroFisiologicoLabel, 1);
    }

    /**
     * Remove 1 unidade de Soro Fisiológico do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerSoroFisiologico(ActionEvent event) {
        atualizarEstoque(soroFisiologicoLabel, -1);
    }

    /**
     * Adiciona 1 unidade de Ivermectina ao estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void adicionarIvermectina(ActionEvent event) {
        atualizarEstoque(ivermectinaLabel, 1);
    }
    
    /**
     * Remove 1 unidade de Ivermectina do estoque.
     *
     * @param event O evento gerado pela ação de clicar no botão.
     */
    @FXML
    public void removerIvermectina(ActionEvent event) {
        atualizarEstoque(ivermectinaLabel, -1);
    }

    /**
     * Adiciona uma unidade de Pipetas ao estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para adicionar Pipetas.
     */
    @FXML
    public void adicionarPipetas(ActionEvent event) {
        atualizarEstoque(pipetasLabel, 1);
    }

    /**
     * Remove uma unidade de Pipetas do estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para remover Pipetas.
     */
    @FXML
    public void removerPipetas(ActionEvent event) {
        atualizarEstoque(pipetasLabel, -1);
    }

    /**
     * Adiciona uma unidade de Aminofilina ao estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para adicionar Aminofilina.
     */
    @FXML
    public void adicionarAminofilina(ActionEvent event) {
        atualizarEstoque(aminofilinaLabel, 1);
    }

    /**
     * Remove uma unidade de Aminofilina do estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para remover Aminofilina.
     */
    @FXML
    public void removerAminofilina(ActionEvent event) {
        atualizarEstoque(aminofilinaLabel, -1);
    }

    /**
     * Adiciona uma unidade de Vitamina K1 ao estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para adicionar Vitamina K1.
     */
    @FXML
    public void adicionarVitaminaK1(ActionEvent event) {
        atualizarEstoque(vitaminaK1Label, 1);
    }

    /**
     * Remove uma unidade de Vitamina K1 do estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para remover Vitamina K1.
     */
    @FXML
    public void removerVitaminaK1(ActionEvent event) {
        atualizarEstoque(vitaminaK1Label, -1);
    }

    /**
     * Adiciona uma unidade de Ester Fibrina ao estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para adicionar Ester Fibrina.
     */
    @FXML
    public void adicionarEsterFibrina(ActionEvent event) {
        atualizarEstoque(esterFibrinaLabel, 1);
    }

    /**
     * Remove uma unidade de Ester Fibrina do estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para remover Ester Fibrina.
     */
    @FXML
    public void removerEsterFibrina(ActionEvent event) {
        atualizarEstoque(esterFibrinaLabel, -1);
    }

    /**
     * Adiciona uma unidade de Diazepam ao estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para adicionar Diazepam.
     */
    @FXML
    public void adicionarDiazepam(ActionEvent event) {
        atualizarEstoque(diazepamLabel, 1);
    }

    /**
     * Remove uma unidade de Diazepam do estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para remover Diazepam.
     */
    @FXML
    public void removerDiazepam(ActionEvent event) {
        atualizarEstoque(diazepamLabel, -1);
    }

    /**
     * Adiciona uma unidade de Fenobarbital ao estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para adicionar Fenobarbital.
     */
    @FXML
    public void adicionarFenobarbital(ActionEvent event) {
        atualizarEstoque(fenobarbitalLabel, 1);
    }

    /**
     * Remove uma unidade de Fenobarbital do estoque e atualiza a exibição da quantidade no rótulo correspondente.
     *
     * @param event O evento gerado pelo clique do botão para remover Fenobarbital.
     */
    @FXML
    public void removerFenobarbital(ActionEvent event) {
        atualizarEstoque(fenobarbitalLabel, -1);
    }

    /**
     * Método para controle de estoque.
     * Este método é invocado ao clicar no botão de controle de estoque.
     *
     * @param event O evento gerado pelo clique do botão.
     */
    @FXML
    public void onControleEstoque(ActionEvent event) {
        System.out.println("Controle de Estoque.");
    }
}