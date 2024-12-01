package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.FuncionarioDao;
import model.entities.Funcionario;

/**
 * Serviço responsável pela lógica de negócios relacionada aos funcionários.
 * Permite a gestão de funcionários, incluindo operações como a criação, atualização,
 * remoção e autenticação.
 * 
 * Utiliza o {@link FuncionarioDao} para interagir com o banco de dados.
 */
public class FuncionarioService {

    private FuncionarioDao dao = DaoFactory.createFuncionarioDao();

    /**
     * Busca todos os funcionários do banco de dados.
     * 
     * @return Lista de todos os funcionários.
     */
    public List<Funcionario> findAll() {
        return dao.findAll();
    }

    /**
     * Salva ou atualiza um funcionário.
     * Se o funcionário não tiver ID, ele é inserido; caso contrário, é atualizado.
     * 
     * @param obj O funcionário a ser salvo ou atualizado.
     */
    public void saveOrUpdate(Funcionario obj) {
        if (obj.getId() == null) {
            dao.insert(obj);  // Inserir novo funcionário
        } else {
            dao.update(obj);  // Atualizar funcionário existente
        }
    }

    /**
     * Remove um funcionário do banco de dados.
     * 
     * @param obj O funcionário a ser removido.
     */
    public void remove(Funcionario obj) {
        dao.deleteById(obj.getId());  // Deletar funcionário pelo ID
    }

    /**
     * Realiza a autenticação de um funcionário através do nome de usuário e senha.
     * 
     * @param username O nome de usuário do funcionário.
     * @param password A senha do funcionário.
     * @return {@code true} se a autenticação for bem-sucedida, {@code false} caso contrário.
     */
    public boolean authenticate(String username, String password) {
        // Busca um funcionário pelo nome de usuário
        Funcionario funcionario = dao.findByUsername(username);
        
        // Verifica se o funcionário existe e se a senha está correta
        if (funcionario != null && funcionario.getPassword().equals(password)) {
            return true;  // Autenticação bem-sucedida
        }
        return false;  // Falha na autenticação
    }
}
