package model.entities;

public class Animal {
    private Integer id;
    private String nome;
    private Integer idade;
    private String raca;
    private String especie;
    private Cliente cliente;

    public Animal() {}

    public Animal(Integer id, String nome, Integer idade, String raca, String especie, Cliente cliente) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.raca = raca;
        this.especie = especie;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Método para definir o cliente_id diretamente
    public void setClienteId(Integer clienteId) {
        if (this.cliente == null) {
            this.cliente = new Cliente();
        }
        this.cliente.setId(clienteId);
    }

    @Override
    public String toString() {
        return "Animal [id=" + id + ", nome=" + nome + ", idade=" + idade + 
               ", raca=" + raca + ", especie=" + especie + "]";
    }
}

