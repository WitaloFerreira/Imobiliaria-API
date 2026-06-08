package com.witaloferreira.imobiliariaapi.controllers;

import com.witaloferreira.imobiliariaapi.models.Imovel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/imoveis")
public class ImovelController {
    private List<Imovel> bancoDeDados;
    public ImovelController() {
        bancoDeDados = new ArrayList<Imovel>();
    }

    @GetMapping
    public ResponseEntity<List<Imovel>> findAll() {
        return ResponseEntity.ok(bancoDeDados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable int id) {
        // Passagem por Valor Local: varrendo a lista no servidor
        for (Imovel imovel : bancoDeDados) {
            if (imovel.getId() == id) {
                return ResponseEntity.ok(imovel);
            }
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Imovel> cadastrar(@RequestBody Imovel imovel) {
        bancoDeDados.add(imovel);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(imovel.getId()).toUri();
        return ResponseEntity.created(uri).body(imovel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerImovel(@PathVariable int id) {
        boolean removido = bancoDeDados.removeIf(imovel -> imovel.getId() == id);

        if (removido) {
            return ResponseEntity.ok("Imóvel removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: Imóvel não encontrado para remoção.");
        }
    }
}
