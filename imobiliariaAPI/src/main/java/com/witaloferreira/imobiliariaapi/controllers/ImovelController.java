package com.witaloferreira.imobiliariaapi.controllers;

import com.witaloferreira.imobiliariaapi.models.Cliente;
import com.witaloferreira.imobiliariaapi.models.Imovel;
import com.witaloferreira.imobiliariaapi.services.ClienteService;
import com.witaloferreira.imobiliariaapi.services.ImovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/imoveis")
public class ImovelController {

    private final ImovelService imovelService;
    private final ClienteService clienteService;

    public ImovelController(ImovelService imovelService, ClienteService clienteService) {
        this.imovelService = imovelService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Imovel>> findAll() {
        return ResponseEntity.ok(imovelService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable int id) {
        Imovel imovel = imovelService.buscarPorId(id);
        if (imovel != null) {
            return ResponseEntity.ok(imovel);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Imovel imovel) {
        if (imovel.getProprietario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: O imóvel deve possuir um proprietário associado.");
        }

        int proprietarioId = imovel.getProprietario().getId();
        boolean proprietarioExiste = clienteService.existeClienteComId(proprietarioId);

        if (!proprietarioExiste) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Proprietário com ID " + proprietarioId + " não existe no sistema.");
        }

        Cliente donoCompleto = clienteService.buscarPorId(proprietarioId);

        if (donoCompleto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Proprietário não existe.");
        }

        imovel.setProprietario(donoCompleto);

        // Como o dono existe, salva no serviço definitivo
        imovelService.salvar(imovel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(imovel.getId()).toUri();

        return ResponseEntity.created(uri).body(imovel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerImovel(@PathVariable int id) {
        boolean removido = imovelService.remover(id);
        if (removido) {
            return ResponseEntity.ok("Imóvel removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Imóvel não encontrado.");
        }
    }
}