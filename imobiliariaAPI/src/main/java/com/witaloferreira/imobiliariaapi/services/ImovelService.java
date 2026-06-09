package com.witaloferreira.imobiliariaapi.services;

import com.witaloferreira.imobiliariaapi.models.Imovel;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImovelService {

    private final List<Imovel> listaImoveisReais = new ArrayList<>();

    // Salva ou atualiza o imóvel
    public void salvar(Imovel imovel) {
        boolean jaExistia = false;
        for (int i = 0; i < listaImoveisReais.size(); i++) {
            if (listaImoveisReais.get(i).getId() == imovel.getId()) {
                listaImoveisReais.set(i, imovel);
                jaExistia = true;
                break;
            }
        }
        if (!jaExistia) {
            listaImoveisReais.add(imovel);
        }
    }

    public Imovel buscarPorId(int id) {
        for (Imovel imovel : listaImoveisReais) {
            if (imovel.getId() == id) {
                return imovel;
            }
        }
        return null;
    }

    public List<Imovel> listarTodos() {
        return listaImoveisReais;
    }

    public boolean remover(int id) {
        return listaImoveisReais.removeIf(imovel -> imovel.getId() == id);
    }
}