package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Veterinario;

public class VeterinarioService {
    private List<Veterinario> veterinarios;
    private int idCounter;

    public VeterinarioService() {
        this.veterinarios = new ArrayList<>();
        this.idCounter = 1; // Inicia o contador de IDs
    }

    public boolean registrarVeterinario(String nome, String email, String telefone, String senha) {
        Veterinario novoVeterinario = new Veterinario(idCounter++, nome, email, telefone, senha);
        return veterinarios.add(novoVeterinario);
    }

    public List<Veterinario> listarVeterinarios() {
        return new ArrayList<>(veterinarios);
    }

    public Veterinario encontrarPorId(Integer id) {
        for (Veterinario veterinario : veterinarios) {
            if (veterinario.getId().equals(id)) {
                return veterinario;
            }
        }
        return null; // Retorna null se não encontrar
    }

    public boolean authenticate(String email, String senha) {
        for (Veterinario veterinario : veterinarios) {
            if (veterinario.getEmail().equals(email) && veterinario.getSenha().equals(senha)) {
                return true; // Retorna true se as credenciais forem válidas
            }
        }
        return false; // Retorna false se as credenciais não forem válidas
    }
}
