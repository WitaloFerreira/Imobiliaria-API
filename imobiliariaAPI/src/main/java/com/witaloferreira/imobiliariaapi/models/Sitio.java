package com.witaloferreira.imobiliariaapi.models;

import java.io.Serializable;

public class Sitio extends Imovel implements Serializable {
    private double distanciaCidade;

    public Sitio() {
        super();
    }

    public Sitio(int id, Endereco endereco, double valor, double tamanho, Cliente proprietario, double distanciaCidade) {
        super(id, endereco, valor, tamanho, proprietario);
        this.distanciaCidade = distanciaCidade;
    }

    public double getDistanciaCidade() {
        return distanciaCidade;
    }

    public void setDistanciaCidade(double distanciaCidade) {
        this.distanciaCidade = distanciaCidade;
    }

    @Override
    public String toString() {
        return super.toString() + " [Tipo: Sítio, Distância da Cidade=" + distanciaCidade + "km]";
    }
}
