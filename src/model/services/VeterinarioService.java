package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VeterinarioDao;
import model.entities.Veterinario;

/**
 * Serviço responsável pela lógica de negócios relacionada aos veterinários.
 * Permite o registro, atualização, remoção e autenticação de veterinários.
 * 
 * Utiliza {@link VeterinarioDao} para interagir com o banco de dados de veterinários.
 */
public class VeterinarioService {

    private VeterinarioDao dao = DaoFactory.createVeterinarioDao();

    /**
     * Recupera todos os veterinários cadastrados no banco de dados.
     * 
     * @return Uma lista de todos os veterinários.
     */
    public List<Veterinario> findAll() {
        return dao.findAll();
    }

    /**
     * Salva ou atualiza um veterinário no banco de dados.
     * Se o veterinário já possui um ID, o método realiza uma atualização; 
     * caso contrário, insere um novo veterinário.
     * 
     * @param obj O veterinário a ser salvo ou atualizado.
     */
    public void saveOrUpdate(Veterinario obj) {
        if (obj.getId() == null) {
            dao.insert(obj);  // Insere um novo veterinário
        } else {
            dao.update(obj);  // Atualiza o veterinário existente
        }
    }

    /**
     * Remove um veterinário do banco de dados pelo seu ID.
     * 
     * @param veterinario O veterinário a ser removido.
     * @throws IllegalArgumentException Se o veterinário ou o ID forem inválidos.
     */
    public void remove(Veterinario veterinario) {
        if (veterinario == null || veterinario.getId() == null) {
            throw new IllegalArgumentException("Veterinário inválido");
        }
        dao.deleteById(veterinario.getId());  // Deleta o veterinário pelo ID
    }

    /**
     * Realiza a autenticação de um veterinário pelo seu nome de usuário e senha.
     * 
     * @param username O nome de usuário do veterinário.
     * @param password A senha do veterinário.
     * @return {@code true} se a autenticação for bem-sucedida, {@code false} caso contrário.
     */
    public boolean authenticate(String username, String password) {
        Veterinario veterinario = dao.findByUsername(username);  // Busca o veterinário pelo nome de usuário
        if (veterinario != null && veterinario.getSenha().equals(password)) {
            return true;  // Autenticação bem-sucedida
        }
        return false;  // Falha na autenticação
    }

    /**
     * Registra um novo veterinário no banco de dados.
     * Realiza validações nos dados antes de salvar o veterinário.
     * 
     * @param nome O nome do veterinário.
     * @param cpf O CPF do veterinário (deve conter 11 números).
     * @param email O email do veterinário.
     * @param telefone O telefone de contato do veterinário.
     * @param senha A senha do veterinário.
     * @throws IllegalArgumentException Se algum dado de entrada for inválido.
     */
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

        // Inserir o veterinário no banco de dados
        dao.insert(veterinario);
    }
}
