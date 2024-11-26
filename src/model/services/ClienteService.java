package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {

    private ClienteDao dao = DaoFactory.createClienteDao();

    public List<Cliente> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Cliente obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Cliente obj) {
        dao.deleteById(obj.getId());
    }

    // Alteração no método authenticate
    public Cliente authenticate(String username, String password) {
        Cliente cliente = dao.findByUsername(username); // Supondo que findByUsername retorna um Cliente
        if (cliente != null && cliente.getSenha().equals(password)) {
            return cliente; // Retorna o cliente autenticado
        }
        return null; // Retorna null caso o cliente não seja encontrado ou a senha não corresponda
    }
    
    // Novo método registrarCliente com o campo 'cpf'
    public void registrarCliente(String nome, String email, String telefone, String senha, String endereco, String cpf) {
        // Validar os dados de entrada (pode adicionar mais validações conforme necessário)
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 números.");
        }

        // Criar novo cliente com CPF e outros dados
        Cliente cliente = new Cliente(null, nome, email, telefone, senha, endereco, cpf);

        // Inserir no banco de dados
        dao.insert(cliente);
    }
}

