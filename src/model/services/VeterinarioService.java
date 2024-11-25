package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VeterinarioDao;
import model.entities.Veterinario;

public class VeterinarioService {

    private VeterinarioDao dao = DaoFactory.createVeterinarioDao();

    public List<Veterinario> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Veterinario obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Veterinario obj) {
        dao.deleteById(obj.getId());
    }

    public boolean authenticate(String username, String password) {
        Veterinario veterinario = dao.findByUsername(username);
        if (veterinario != null && veterinario.getSenha().equals(password)) {
            return true;
        }
        return false;
    }

    // Novo método para registrar Veterinário com os campos necessários
    public void registrarVeterinario(String nome, String cpf, String email, String telefone, String senha) {
        // Validar os dados de entrada
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 números.");
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

        // Criar novo veterinário com os dados fornecidos
        Veterinario veterinario = new Veterinario(null, nome, cpf, email, telefone, senha);

        // Inserir no banco de dados
        dao.insert(veterinario);
    }
}
