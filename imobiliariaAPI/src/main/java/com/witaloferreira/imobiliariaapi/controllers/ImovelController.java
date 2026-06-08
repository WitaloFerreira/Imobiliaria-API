package com.witaloferreira.imobiliariaapi.controllers;

import com.witaloferreira.imobiliariaapi.models.Imovel;
import com.witaloferreira.imobiliariaapi.services.ClienteService;
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

    private final List<Imovel> bancoDeDados;
    private final ClienteService clienteService;

    public ImovelController(ClienteService clienteService) {
        this.bancoDeDados = new ArrayList<Imovel>();
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Imovel>> findAll() {
        return ResponseEntity.ok(bancoDeDados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable int id) {
        for (Imovel imovel : bancoDeDados) {
            if (imovel.getId() == id) {
                return ResponseEntity.ok(imovel);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Imovel imovel) {
        if (imovel.getProprietario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: O imóvel deve possuir um proprietário associado.");
        }

        int proprietarioId = imovel.getProprietario().getId();
        boolean proprietarioExiste = clienteService.existeClienteComId(proprietarioId);

        if (!proprietarioExiste) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Não foi possível cadastrar o imóvel. O proprietário com ID " + proprietarioId + " não existe no sistema.");
        }

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