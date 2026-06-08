package com.witaloferreira.imobiliariaapi.models;

import java.io.Serializable;

public class Imovel implements Serializable {
    private int id;
    private Endereco endereco;
    private double valor;
    private double tamanho;
    private Cliente proprietario;

    public Imovel() {}

    public Imovel(int id, Endereco endereco, double valor, double tamanho, Cliente proprietario) {
        this.id = id;
        this.endereco = endereco;
        this.valor = valor;
        this.tamanho = tamanho;
        this.proprietario = proprietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Imovel [ID=" + id + ", Endereço=" + endereco +
                ", Valor=R$ " + valor + ", Tamanho=" + tamanho + "m², Proprietário=" + (proprietario != null ? proprietario.getNome() : "Nenhum") + "]";
    }
}