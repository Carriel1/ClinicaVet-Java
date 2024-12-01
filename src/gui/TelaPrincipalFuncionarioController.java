package gui;

import java.io.IOException;
import java.util.List;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.entities.Veterinario;
import model.services.ClienteService;
import model.services.ConsultaService;
import model.services.VeterinarioService;

public class TelaPrincipalFuncionarioController {

    @FXML
    private Label lblWelcomeMessage;

    @FXML
    private Button btnCadastrarVeterinario;

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private Button btnConsulta;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnSair;

    private ClienteService clienteService;
    private VeterinarioService veterinarioService;
    private ConsultaService consultaService;

    @FXML
    public void initialize() {
        if (clienteService == null) {
            clienteService = new ClienteService();  
        }
        if (veterinarioService == null) {
            veterinarioService = new VeterinarioService();
        }
        if (consultaService == null) {
            consultaService = new ConsultaService();
        }
        
        List<Cliente> clientes = clienteService.findAll(); 
    }

    @FXML
    public void onCadastrarVeterinario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VeterinarioRegistro.fxml"));
            Parent parent = loader.load();

            VeterinarioRegistroController controller = loader.getController();
            controller.setVeterinarioService(veterinarioService);
            controller.setVeterinario(new Veterinario());

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Veterinário");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método chamado para cadastrar um novo cliente
    @FXML
    public void onCadastrarCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ClienteRegistro.fxml"));
            Parent parent = loader.load();

            ClienteRegistroController controller = loader.getController();
            controller.setClienteService(clienteService);
            controller.setCliente(new Cliente());

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cliente");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método chamado para aceitar ou marcar uma consulta
    @FXML
    public void onMarcarConsulta(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de registro de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultaRegistro.fxml"));
            Parent parent = loader.load();

            // Configura o controlador da nova tela
            ConsultaRegistroController controller = loader.getController();
            controller.setConsultaService(consultaService); 
            controller.setClienteService(clienteService);    
            controller.setVeterinarioService(veterinarioService); 

            Stage stage = new Stage();
            stage.setTitle("Registrar Consulta");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); 
            stage.initModality(Modality.WINDOW_MODAL);  
            stage.showAndWait(); 

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onCancelarConsulta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CancelarConsulta.fxml"));
            Parent root = loader.load();

            CancelarConsultaController cancelarConsultaController = loader.getController();
            
            cancelarConsultaController.setServices(consultaService); // Passa o ConsultaService

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de cancelamento de consulta.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onModificarConsulta(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de modificação de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModificarConsulta.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de modificação de consulta
            ModificarConsultaController modificarConsultaController = loader.getController();
            
            // Passa os serviços necessários para o controlador de ModificarConsulta
            modificarConsultaController.setServices(consultaService); 
            modificarConsultaController.setClienteService(clienteService); 
            modificarConsultaController.setVeterinarioService(veterinarioService); 

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Falha ao abrir a tela de modificação de consulta.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onExcluirVeterinario(ActionEvent event) {
        try {
            // Carrega o FXML para a tela de exclusão de veterinário
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ExcluirVeterinario.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de exclusão de veterinário
            ExcluirVeterinarioController excluirVeterinarioController = loader.getController();
            
            // Passa o serviço necessário para o controlador de exclusão
            excluirVeterinarioController.setVeterinarioService(veterinarioService);

            // Exibe a nova tela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Excluir Veterinário");
            stage.initOwner(Utils.currentStage(event)); 
            stage.initModality(Modality.WINDOW_MODAL);  
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void onAprovarConsulta(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de aprovação de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConsultaAprovacao.fxml"));
            Parent parent = loader.load();

            // Configura o controlador da nova tela
            ConsultaAprovacaoController controller = loader.getController();
            controller.setConsultaService(consultaService); 

            // Passando o clienteId para o novo controlador
            controller.setClienteId(clienteService.getLoggedClienteId()); 

            // Criação e exibição da nova janela de aprovação
            Stage stage = new Stage();
            stage.setTitle("Aprovação de Consulta");
            stage.setScene(new Scene(parent));
            stage.initOwner(Utils.currentStage(event)); 
            stage.initModality(Modality.WINDOW_MODAL);  
            stage.showAndWait(); // Espera a tela ser fechada antes de continuar

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar tela", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void ongerarRelatorio() {
        // Instanciando a classe RelatorioCTodosDadosDS
        RelatorioCTodosDadosDS relatorioCTodosDadosDS = new RelatorioCTodosDadosDS();

        // Chamando o método para salvar o relatório
        relatorioCTodosDadosDS.salvarRelatorio();

        // Exibindo um alerta de confirmação
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Relatório Gerado");
        alert.setHeaderText(null);
        alert.setContentText("O relatório foi gerado e salvo com sucesso!");
        alert.showAndWait();
    }


    // Método chamado para o controle de estoque
    @FXML
    public void onControleEstoque(ActionEvent event) {
        try {
            // Carregar o FXML da tela de estoque
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Estoque.fxml"));
            Parent estoqueView = loader.load();

            // Criar uma nova cena com a tela de estoque
            Scene estoqueScene = new Scene(estoqueView);

            // Criar um novo palco (janela)
            Stage estoqueStage = new Stage();
            estoqueStage.setTitle("Controle de Estoque");
            estoqueStage.setScene(estoqueScene);
            estoqueStage.show();

            // Se você quiser fechar a janela atual após abrir a nova:
            // ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela de estoque.");
        }
    }

    // Método chamado para sair da tela principal
    @FXML
    public void onSairAction(ActionEvent event) {
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
            stage.setScene(mainScene); 
            stage.show();  

            // Ajustar o tamanho da janela após a troca de cenas
            stage.sizeToScene();  // Ajusta a janela automaticamente ao conteúdo
            stage.centerOnScreen();  // Centraliza a janela na tela

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Erro ao carregar a tela principal", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para definir uma mensagem de boas-vindas
    public void setWelcomeMessage(String message) {
        if (lblWelcomeMessage != null) {
            lblWelcomeMessage.setText(message);
        }
    }
}

