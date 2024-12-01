package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {

    private ClienteDao dao;
    private static Cliente loggedCliente;  
    
    // Construtor para injeção do DAO
    public ClienteService() {
        this.dao = DaoFactory.createClienteDao(); // Inicializa o DAO usando a fábrica
    }

    // Método para buscar todos os clientes
    public List<Cliente> findAll() {
        // Busca todos os clientes disponíveis no banco
        List<Cliente> clientes = dao.findAll();
        if (clientes == null || clientes.isEmpty()) {
            throw new IllegalStateException("Nenhum cliente encontrado no banco de dados.");
        }
        return clientes;
    }

    // Método para salvar ou atualizar um cliente
    public void saveOrUpdate(Cliente obj) {
        if (obj.getId() == null) {
            dao.insert(obj); // Insere novo cliente se ID for nulo
        } else {
            dao.update(obj); // Atualiza cliente existente
        }
    }

    // Método para remover cliente pelo ID
    public void remove(Cliente obj) {
        if (obj == null || obj.getId() == null) {
            throw new IllegalArgumentException("Cliente ou ID não pode ser nulo.");
        }
        dao.deleteById(obj.getId()); // Deleta cliente
    }

    // Método para autenticar um cliente por username e senha
    public Cliente authenticate(String username, String password) {
        Cliente cliente = dao.findByUsername(username); // Busca cliente pelo username
        if (cliente != null && cliente.getSenha().equals(password)) {
            loggedCliente = cliente; // Armazena o cliente logado
            return cliente; // Retorna cliente autenticado
        }
        return null; // Retorna null se autenticação falhar
    }

    // Método para obter o ID do cliente logado
    public static Integer getLoggedClienteId() {
        if (loggedCliente != null) {
            return loggedCliente.getId(); // Retorna o ID do cliente logado
        }
        return null; // Se não houver cliente logado, retorna null
    }

    // Método para registrar um cliente
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
        dao.insert(cliente); 
    }
}

