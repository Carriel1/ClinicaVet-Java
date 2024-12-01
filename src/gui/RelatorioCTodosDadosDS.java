package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import model.dao.impl.AnimalDaoJDBC;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.ConsultaDaoJDBC;
import model.dao.impl.FuncionarioDaoJDBC;
import model.dao.impl.RelatorioDaoJDBC;
import model.dao.impl.VeterinarioDaoJDBC;
import model.entities.Animal;
import model.entities.Cliente;
import model.entities.Consulta;
import model.entities.Funcionario;
import model.entities.Relatorio;
import model.entities.Veterinario;

/**
 * Classe responsável por gerar e salvar um relatório completo contendo dados de
 * consultas, clientes, veterinários, funcionários, animais e relatórios.
 * A classe utiliza os DAOs para consultar os dados do banco e gerar o relatório.
 */
public class RelatorioCTodosDadosDS {

    private ConsultaDaoJDBC consultaDao;  // DAO para consultas
    private ClienteDaoJDBC clienteDao;    // DAO para clientes
    private RelatorioDaoJDBC relatorioDao; // DAO para relatórios
    private VeterinarioDaoJDBC veterinarioDao; // DAO para veterinários
    private FuncionarioDaoJDBC funcionarioDao; // DAO para funcionários
    private AnimalDaoJDBC animalDao;  // DAO para animais

    /**
     * Construtor da classe.
     * Inicializa os DAOs necessários para consulta dos dados.
     */
    public RelatorioCTodosDadosDS() {
        this.consultaDao = new ConsultaDaoJDBC();
        this.clienteDao = new ClienteDaoJDBC();
        this.relatorioDao = new RelatorioDaoJDBC();
        this.veterinarioDao = new VeterinarioDaoJDBC();
        this.funcionarioDao = new FuncionarioDaoJDBC();
        this.animalDao = new AnimalDaoJDBC();  
    }

    /**
     * Método que coleta todos os dados e gera o relatório com informações sobre
     * consultas, clientes, veterinários, funcionários, animais e relatórios.
     * 
     * @return O relatório gerado como uma string.
     */
    public String gerarRelatorio() {
        System.out.println("Método gerarRelatorio chamado!");
        StringBuilder relatorio = new StringBuilder();
        
        try {
            // Verificar e reabrir a conexão antes de fazer consultas
            consultaDao.verificarConexao();
            clienteDao.verificarConexao();
            relatorioDao.verificarConexao();
            veterinarioDao.verificarConexao();
            funcionarioDao.verificarConexao();
            animalDao.verificarConexao();

            // Consultando dados
            List<Consulta> consultas = consultaDao.findAll();
            List<Cliente> clientes = clienteDao.findAll();
            List<Relatorio> relatorios = relatorioDao.findAll();
            List<Veterinario> veterinarios = veterinarioDao.findAll();
            List<Funcionario> funcionarios = funcionarioDao.findAll();
            List<Animal> animais = animalDao.findAll();  // Buscando todos os animais

            // Gerando relatório com os dados
            relatorio.append("Relatório Completo de Dados\n\n");

            relatorio.append("Consultas:\n");
            for (Consulta consulta : consultas) {
                relatorio.append(consulta).append("\n");
            }

            relatorio.append("\nClientes:\n");
            for (Cliente cliente : clientes) {
                relatorio.append(cliente).append("\n");
            }

            relatorio.append("\nRelatórios:\n");
            for (Relatorio relatorioItem : relatorios) {
                relatorio.append(relatorioItem).append("\n");
            }

            relatorio.append("\nVeterinários:\n");
            for (Veterinario veterinario : veterinarios) {
                relatorio.append(veterinario).append("\n");
            }

            relatorio.append("\nFuncionários:\n");
            for (Funcionario funcionario : funcionarios) {
                relatorio.append(funcionario).append("\n");
            }

            // Adicionando os animais ao relatório
            relatorio.append("\nAnimais:\n");
            for (Animal animal : animais) {
                relatorio.append(animal).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao gerar o relatório: " + e.getMessage();
        }

        return relatorio.toString(); // Retorna o relatório gerado como uma string
    }

    /**
     * Método que salva o relatório gerado em um arquivo no caminho especificado.
     * Se o diretório não existir, ele será criado.
     * 
     * @param caminhoArquivo O caminho onde o relatório será salvo.
     */
    public void salvarRelatorioEmArquivo(String caminhoArquivo) {
        try {
            String dadosRelatorio = gerarRelatorio();  // Gera o conteúdo do relatório
            System.out.println("Conteúdo do relatório gerado:\n" + dadosRelatorio);  
            
            // Verificar e criar o diretório, se necessário
            File diretorio = new File(caminhoArquivo).getParentFile();
            System.out.println("Relatório salvo em: " + caminhoArquivo); 
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Escrever os dados no arquivo
            Files.write(Paths.get(caminhoArquivo), dadosRelatorio.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            System.out.println("Relatório salvo em: " + caminhoArquivo); // Mensagem de confirmação
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que salva o relatório gerado em um arquivo no diretório padrão.
     * O caminho do arquivo é fixo.
     */
    public void salvarRelatorio() {
        System.out.println("Método salvarRelatorio chamado!");
        // Definindo o caminho onde o relatório será salvo
        String caminhoArquivo = "C:/Users/mateu/OneDrive/Área de Trabalho/Projetos em JAVA/Eclipse/ClinicaVet/src/resources/relatorios/relatorio_completo.txt";
        System.out.println("Iniciando o salvamento do relatório...");
        // Chamando o método para salvar o relatório no arquivo
        salvarRelatorioEmArquivo(caminhoArquivo);
        System.out.println("Salvamento concluído.");
    }
}
