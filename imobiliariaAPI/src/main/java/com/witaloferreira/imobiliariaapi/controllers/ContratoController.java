package com.witaloferreira.imobiliariaapi.controllers;

import com.witaloferreira.imobiliariaapi.models.Contrato;
import com.witaloferreira.imobiliariaapi.models.Cliente;
import com.witaloferreira.imobiliariaapi.models.Imovel;
import com.witaloferreira.imobiliariaapi.services.ClienteService;
import com.witaloferreira.imobiliariaapi.services.ImovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping(value = "/contratos")
public class ContratoController {

    private final List<Contrato> bancoContratos = new ArrayList<>();

    // 1. Nossa Fila de Mensagens em Memória
    private final Queue<String> filaMensagens = new LinkedList<>();

    private final ClienteService clienteService;
    private final ImovelService imovelService;

    public ContratoController(ClienteService clienteService, ImovelService imovelService) {
        this.clienteService = clienteService;
        this.imovelService = imovelService;
    }

    @GetMapping
    public ResponseEntity<List<Contrato>> findAll() {
        return ResponseEntity.ok(bancoContratos);
    }

    // 2. Endpoint para o Consumidor ler e esvaziar a fila
    @GetMapping("/fila")
    public ResponseEntity<List<String>> consumirFila() {
        List<String> mensagens = new ArrayList<>(filaMensagens);
        filaMensagens.clear(); // Esvazia a fila após a leitura
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscarPorId(@PathVariable int id) {
        for (Contrato contrato : bancoContratos) {
            if (contrato.getId() == id) {
                return ResponseEntity.ok(contrato);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Contrato contrato) {
        int locatarioId = contrato.getLocatario().getId();
        int propriedadeId = contrato.getPropriedade().getId();

        Cliente locatarioCompleto = clienteService.buscarPorId(locatarioId);
        Imovel propriedadeCompleta = imovelService.buscarPorId(propriedadeId);

        if (locatarioCompleto == null || propriedadeCompleta == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Cliente ou Imóvel não encontrados.");
        }

        contrato.setLocatario(locatarioCompleto);
        contrato.setPropriedade(propriedadeCompleta);

        bancoContratos.add(contrato);

        // 3. Adiciona a mensagem na fila internamente
        filaMensagens.add("Notificação: Novo contrato gerado (ID " + contrato.getId() + ") para " + locatarioCompleto.getNome());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(contrato.getId()).toUri();

        return ResponseEntity.created(uri).body(contrato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerContrato(@PathVariable int id) {
        boolean removido = bancoContratos.removeIf(contrato -> contrato.getId() == id);
        if (removido) {
            return ResponseEntity.ok("Contrato removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Contrato não encontrado.");
        }
    }
}