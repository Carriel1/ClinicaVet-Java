package model.entities;

import java.util.Date;

/**
 * Representa um funcionário no sistema, incluindo informações pessoais, dados de contato, salário base e senha.
 */
public class Funcionario {

    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;
    private String password;

    /**
     * Construtor padrão para criar uma instância de {@link Funcionario}.
     */
    public Funcionario() {
    }

    /**
     * Construtor para criar uma instância de {@link Funcionario} com os valores fornecidos.
     * 
     * @param id O ID do funcionário.
     * @param name O nome do funcionário.
     * @param email O e-mail do funcionário.
     * @param birthDate A data de nascimento do funcionário.
     * @param baseSalary O salário base do funcionário.
     * @param password A senha do funcionário.
     */
    public Funcionario(Integer id, String name, String email, Date birthDate, Double baseSalary, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.password = password;
    }

    // Getters e Setters

    /**
     * Retorna o ID do funcionário.
     * 
     * @return O ID do funcionário.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do funcionário.
     * 
     * @param id O ID do funcionário.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do funcionário.
     * 
     * @return O nome do funcionário.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do funcionário.
     * 
     * @param name O nome do funcionário.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o e-mail do funcionário.
     * 
     * @return O e-mail do funcionário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do funcionário.
     * 
     * @param email O e-mail do funcionário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a data de nascimento do funcionário.
     * 
     * @return A data de nascimento do funcionário.
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Define a data de nascimento do funcionário.
     * 
     * @param birthDate A data de nascimento do funcionário.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Retorna o salário base do funcionário.
     * 
     * @return O salário base do funcionário.
     */
    public Double getBaseSalary() {
        return baseSalary;
    }

    /**
     * Define o salário base do funcionário.
     * 
     * @param baseSalary O salário base do funcionário.
     */
    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    /**
     * Retorna a senha do funcionário.
     * 
     * @return A senha do funcionário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do funcionário.
     * 
     * @param password A senha do funcionário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna uma representação em string do objeto {@link Funcionario}.
     * 
     * @return Uma string representando o funcionário.
     */
    @Override
    public String toString() {
        return "Funcionario [id=" + id + ", name=" + name + ", email=" + email + 
               ", birthDate=" + birthDate + ", baseSalary=" + baseSalary + "]";
    }
}
