package com.witaloferreira.imobiliariaapi.models;

public class Contrato {
    private int id;
    private Cliente locatario;
    private Imovel propriedade;
    private double valorMensal;

    public Contrato() {}

    public Contrato(int id, Cliente locatario, Imovel propriedade, double valorMensal) {
        this.id = id;
        this.locatario = locatario;
        this.propriedade = propriedade;
        this.valorMensal = valorMensal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getLocatario() { return locatario; }
    public void setLocatario(Cliente locatario) { this.locatario = locatario; }

    public Imovel getPropriedade() { return propriedade; }
    public void setPropriedade(Imovel propriedade) { this.propriedade = propriedade; }

    public double getValorMensal() { return valorMensal; }
    public void setValorMensal(double valorMensal) { this.valorMensal = valorMensal; }
}
