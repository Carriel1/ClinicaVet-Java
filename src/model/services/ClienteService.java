package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

/**
 * Serviço responsável pela lógica de negócios relacionada a {@link Cliente}.
 * Contém métodos para autenticação, registro, busca, remoção e atualização de clientes.
 * Utiliza o DAO de clientes para interagir com o banco de dados.
 */
public class ClienteService {

    private ClienteDao dao;
    private static Cliente loggedCliente;  // Cliente atualmente autenticado
    
    /**
     * Construtor que inicializa o serviço com o DAO de clientes.
     * O DAO é obtido por meio de uma fábrica de DAOs.
     */
    public ClienteService() {
        this.dao = DaoFactory.createClienteDao(); // Inicializa o DAO usando a fábrica
    }

    /**
     * Busca todos os clientes no banco de dados.
     * 
     * @return Uma lista de {@link Cliente} contendo todos os clientes encontrados.
     * @throws IllegalStateException Se não houver clientes no banco de dados.
     */
    public List<Cliente> findAll() {
        List<Cliente> clientes = dao.findAll();
        if (clientes == null || clientes.isEmpty()) {
            throw new IllegalStateException("Nenhum cliente encontrado no banco de dados.");
        }
        return clientes;
    }

    /**
     * Salva um cliente, ou atualiza caso já exista.
     * 
     * @param obj O objeto {@link Cliente} a ser salvo ou atualizado.
     * @throws IllegalArgumentException Se o cliente for inválido.
     */
    public void saveOrUpdate(Cliente obj) {
        if (obj.getId() == null) {
            dao.insert(obj); // Insere novo cliente se ID for nulo
        } else {
            dao.update(obj); // Atualiza cliente existente
        }
    }

    /**
     * Remove um cliente do banco de dados.
     * 
     * @param obj O objeto {@link Cliente} a ser removido.
     * @throws IllegalArgumentException Se o cliente ou ID for nulo.
     */
    public void remove(Cliente obj) {
        if (obj == null || obj.getId() == null) {
            throw new IllegalArgumentException("Cliente ou ID não pode ser nulo.");
        }
        dao.deleteById(obj.getId()); // Deleta cliente
    }

    /**
     * Autentica um cliente por seu nome de usuário (username) e senha.
     * 
     * @param username O nome de usuário do cliente.
     * @param password A senha do cliente.
     * @return O objeto {@link Cliente} autenticado, ou {@code null} se a autenticação falhar.
     */
    public Cliente authenticate(String username, String password) {
        Cliente cliente = dao.findByUsername(username); // Busca cliente pelo username
        if (cliente != null && cliente.getSenha().equals(password)) {
            loggedCliente = cliente; // Armazena o cliente logado
            return cliente; // Retorna cliente autenticado
        }
        return null; // Retorna null se autenticação falhar
    }

    /**
     * Obtém o ID do cliente atualmente autenticado.
     * 
     * @return O ID do cliente logado, ou {@code null} se não houver cliente logado.
     */
    public static Integer getLoggedClienteId() {
        if (loggedCliente != null) {
            return loggedCliente.getId(); // Retorna o ID do cliente logado
        }
        return null; // Se não houver cliente logado, retorna null
    }

    /**
     * Registra um novo cliente no banco de dados.
     * Realiza validações dos campos necessários (nome, email, telefone, senha, CPF).
     * 
     * @param nome O nome do cliente.
     * @param email O email do cliente.
     * @param telefone O telefone do cliente.
     * @param senha A senha do cliente.
     * @param endereco O endereço do cliente.
     * @param cpf O CPF do cliente.
     * @throws IllegalArgumentException Se algum campo for inválido ou faltar informação.
     */
    public void registrarCliente(String nome, String email, String telefone, String senha, String endereco, String cpf) {

    	if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }
        if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 números.");
        }

        // Cria novo cliente
        Cliente cliente = new Cliente(null, nome, email, telefone, senha, endereco, cpf);
        dao.insert(cliente); // Insere o cliente no banco
    }
}
