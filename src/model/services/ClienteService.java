package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Cliente; // Importe a classe Cliente do pacote model.entities

public class ClienteService {

    // Lista para armazenar os clientes
    private List<Cliente> clientes;
    private int idCounter; // Contador para gerar IDs únicos

    // Construtor
    public ClienteService() {
        this.clientes = new ArrayList<>();
        this.idCounter = 1; // Começa a contagem de IDs a partir de 1
    }

    // Método para registrar um novo cliente
    public boolean registrarCliente(String nome, String email, String telefone, String senha) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório para o registro.");
        }
        
        // Cria um novo cliente com um ID único
        Cliente novoCliente = new Cliente(idCounter++, nome, email, telefone, senha); // Incrementa o ID
        return clientes.add(novoCliente); // Retorna true se o cliente for adicionado com sucesso
    }

    // Método para autenticar o cliente
    public boolean authenticate(String username, String password) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(username) && cliente.getSenha().equals(password)) {
                return true; // Retorna true se as credenciais forem válidas
            }
        }
        return false; // Retorna false se as credenciais não forem válidas
    }

    // Método para listar todos os clientes
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes); // Retorna uma cópia da lista de clientes
    }

    // Método para encontrar um cliente pelo ID
    public Cliente encontrarPorId(Integer id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null; // Retorna null se o cliente não for encontrado
    }
}
