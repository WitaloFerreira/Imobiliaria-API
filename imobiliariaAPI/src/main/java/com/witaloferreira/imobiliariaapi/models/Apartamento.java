package com.witaloferreira.imobiliariaapi.models;

import java.io.Serializable;

public class Apartamento extends Imovel implements Serializable {
    private int andar;

    public Apartamento() {
        super();
    }

    public Apartamento(int id, Endereco endereco, double valor, double tamanho, Cliente proprietario, int andar) {
        super(id, endereco, valor, tamanho, proprietario);
        this.andar = andar;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    @Override
    public String toString() {
        return super.toString() + " [Tipo: Apartamento, Andar=" + andar + "]";
    }
}