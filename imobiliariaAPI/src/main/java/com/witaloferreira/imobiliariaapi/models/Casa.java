package com.witaloferreira.imobiliariaapi.models;

import java.io.Serializable;

public class Casa extends Imovel implements Serializable {
    private boolean temQuintal;

    public Casa() {
        super();
    }

    public Casa(int id, Endereco endereco, double valor, double tamanho, Cliente proprietario, boolean temQuintal) {
        super(id, endereco, valor, tamanho, proprietario);
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal() {
        return temQuintal;
    }

    public void setTemQuintal(boolean temQuintal) {
        this.temQuintal = temQuintal;
    }

    @Override
    public String toString() {
        return super.toString() + " [Tipo: Casa, Quintal=" + (temQuintal ? "Sim" : "Não") + "]";
    }
}
