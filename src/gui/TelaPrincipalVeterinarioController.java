package gui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import db.DB;
import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.entities.Relatorio;
import model.entities.Veterinario;
import model.services.AnimalService;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

public class TelaPrincipalVeterinarioController {

    @FXML
    private Button btnCadastrarAnimal;

    @FXML
    private Button btnRealizarConsulta;

    @FXML
    private Button btnVerRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Label lblWelcomeMessage; // Este Label é para exibir a mensagem de boas-vindas

    private AnimalService animalService;
    private ClienteService clienteService;
    private ConsultaService consultaService;
    private VeterinarioService veterinarioService;
    
    public TelaPrincipalVeterinarioController(ClienteService clienteService, AnimalService animalService, ConsultaService consultaService, VeterinarioService veterinarioService) {
        this.clienteService = clienteService;
        this.consultaService = consultaService;
        this.animalService = animalService;
        this.veterinarioService = veterinarioService;
    }
    
    public TelaPrincipalVeterinarioController () {
    	
    }
    
    // Método para injeção dos serviços
    public void setServices(AnimalService animalService, ClienteService clienteService, ConsultaService consultaService, VeterinarioService veterinarioService) {
        if (animalService == null || clienteService == null || consultaService == null || veterinarioService == null) {
            throw new IllegalStateException("Os serviços não foram configurados corretamente.");
        }
        this.animalService = animalService;
        this.clienteService = clienteService;
        this.consultaService = consultaService; 
        this.veterinarioService = veterinarioService;
    }

    // Método para o evento do botão "Realizar Consulta"
    @FXML
    public void onRealizarConsulta(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de consultas pendentes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultasPendentes.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de consultas pendentes
            ConsultasPendentesController consultasController = loader.getController();
                        
            // Passa os serviços para o controlador de ConsultasPendentes
            consultasController.setServices(clienteService, animalService, consultaService);  // Passa também o ConsultaService

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de consultas pendentes.", Alert.AlertType.ERROR);
        }
    }

    // Método para o evento do botão "Ver Relatórios"
    @FXML
    public void onVerRelatorios(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de relatórios
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VerRelatorios.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de relatórios
            VerRelatoriosController controller = loader.getController();
            
            // Exibe a nova tela de relatórios
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de relatórios.", Alert.AlertType.ERROR);
        }
    }


    // Método para o evento do botão "Cadastrar Animal"
    @FXML
    public void onCadastrarAnimal(ActionEvent event) {
        try {
            if (animalService == null || clienteService == null) {
                throw new IllegalStateException("Os serviços não foram configurados.");
            }

            // Carrega o FXML para a tela de cadastro de animal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CadastrarAnimalVeterinario.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de cadastro de animal
            CadastrarAnimalVeterinarioController controller = loader.getController();

            // Passa os serviços para o controlador de CadastrarAnimalVeterinario
            controller.setServices(animalService, clienteService);

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de cadastro de animal.", Alert.AlertType.ERROR);
        }
    }
    
    public class RelatorioService {

        public static List<Relatorio> findAllRelatoriosComVeterinario() {
            // Exemplo de consulta no banco para pegar todos os relatórios e seus veterinários responsáveis
            List<Relatorio> relatorios = new ArrayList<>();
            
            String query = "SELECT r.*, v.nome FROM relatorio r INNER JOIN veterinario v ON r.veterinario_id = v.id";
            
            try (Connection conn = DB.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Relatorio relatorio = new Relatorio();
                    relatorio.setDescricao(rs.getString("descricao"));
                    Veterinario veterinario = new Veterinario();
                    veterinario.setNome(rs.getString("nome"));
                    relatorio.setVeterinarioResponsavel(veterinario);
                    relatorios.add(relatorio);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            return relatorios;
        }
    }


    @FXML
    public void onSair(ActionEvent event) {
        try {
            // Carregar o FXML da tela principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            Parent mainViewParent = loader.load();

            // Obter a cena principal da aplicação
            Scene mainScene = Main.getMainScene();

            // Substituir o conteúdo da cena principal com o novo FXML (MainView)
            mainScene.setRoot(mainViewParent);

            // Atualizar a cena e exibir a janela
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);  // Atualiza a cena
            stage.show();  // Exibe a cena novamente

            // Ajustar o tamanho da janela após a troca de cenas
            stage.sizeToScene();  // Ajusta a janela automaticamente ao conteúdo
            stage.centerOnScreen();  // Centraliza a janela na tela

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar a tela principal", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para definir a mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);  // Atualiza o texto do label
        } else {
            System.out.println("O Label de boas-vindas não foi inicializado.");
        }
    }
}
