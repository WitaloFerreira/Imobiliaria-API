package com.witaloferreira.imobiliariaapi.services;

import com.witaloferreira.imobiliariaapi.models.Cliente;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private List<Cliente> listaClientesReais = new ArrayList<>();

    public void salvar(Cliente cliente) {
        listaClientesReais.add(cliente);
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
}