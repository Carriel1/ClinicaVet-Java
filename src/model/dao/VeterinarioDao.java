package model.dao;

import java.util.List;

import model.entities.Veterinario;

/**
 * Interface que define as operações básicas de acesso a dados para a entidade Veterinario.
 * Contém os métodos necessários para realizar as operações de CRUD (Criar, Ler, Atualizar, Excluir)
 * no banco de dados relacionadas aos veterinários.
 * 
 * A interface é implementada por classes responsáveis pela execução real dessas operações no banco de dados.
 */
public interface VeterinarioDao {

    /**
     * Insere um novo veterinário no banco de dados.
     * 
     * @param obj O objeto Veterinario a ser inserido.
     */
    void insert(Veterinario obj);

    /**
     * Atualiza os dados de um veterinário no banco de dados.
     * 
     * @param obj O objeto Veterinario com os dados a serem atualizados.
     */
    void update(Veterinario obj);

    /**
     * Exclui um veterinário do banco de dados pelo seu ID.
     * 
     * @param id O ID do veterinário a ser excluído.
     */
    void deleteById(Integer id);

    /**
     * Retorna um veterinário do banco de dados pelo seu ID.
     * 
     * @param id O ID do veterinário a ser encontrado.
     * @return O objeto Veterinario com os dados correspondentes ao ID informado.
     */
    Veterinario findById(Integer id);

    /**
     * Retorna um veterinário do banco de dados pelo seu endereço de email.
     * 
     * @param email O email do veterinário a ser encontrado.
     * @return O objeto Veterinario correspondente ao email informado.
     */
    Veterinario findByEmail(String email);

    /**
     * Retorna um veterinário do banco de dados pelo seu CPF.
     * 
     * @param cpf O CPF do veterinário a ser encontrado.
     * @return O objeto Veterinario correspondente ao CPF informado.
     */
    Veterinario findByCpf(String cpf);

    /**
     * Retorna um veterinário do banco de dados pelo seu nome de usuário.
     * 
     * @param username O nome de usuário do veterinário a ser encontrado.
     * @return O objeto Veterinario correspondente ao nome de usuário informado.
     */
    Veterinario findByUsername(String username);

    /**
     * Retorna uma lista de todos os veterinários cadastrados no banco de dados.
     * 
     * @return Uma lista de objetos Veterinario.
     */
    List<Veterinario> findAll();
}
