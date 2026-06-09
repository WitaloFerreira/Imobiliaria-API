package com.witaloferreira.imobiliariaapi.services;

import com.witaloferreira.imobiliariaapi.models.Cliente;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final List<Cliente> listaClientesReais = new ArrayList<>();

    public void salvar(Cliente cliente) {
        listaClientesReais.add(cliente);
        System.out.println("DEBUG: Cliente " + cliente.getNome() + " salvo! Total de clientes agora: " + listaClientesReais.size());
    }

    public boolean existeClienteComId(int id) {
        for (Cliente cliente : listaClientesReais) {
            if (cliente.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public List<Cliente> listarTodos() {
        return listaClientesReais;
    }

    public Cliente buscarPorId(int id) {
        for (Cliente cliente : listaClientesReais) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        return listaClientesReais.removeIf(c -> c.getId() == id);
    }
}