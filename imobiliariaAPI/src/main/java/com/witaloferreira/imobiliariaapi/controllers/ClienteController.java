package com.witaloferreira.imobiliariaapi.controllers;

import com.witaloferreira.imobiliariaapi.models.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
    private List<Cliente> bancoClientes;

    public ClienteController() {
        bancoClientes = new ArrayList<Cliente>();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(bancoClientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable int id) {
        // Passagem por Valor Local: varrendo a lista no servidor
        for (Cliente cliente : bancoClientes) {
            if (cliente.getId() == id) {
                return ResponseEntity.ok(cliente);
            }
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente) {
        bancoClientes.add(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerCliente(@PathVariable int id) {
        boolean removido = bancoClientes.removeIf(cliente -> cliente.getId() == id);

        if (removido) {
            return ResponseEntity.ok("Imóvel removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: Imóvel não encontrado para remoção.");
        }
    }
}
