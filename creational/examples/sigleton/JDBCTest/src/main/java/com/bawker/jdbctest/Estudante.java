package com.bawker.jdbctest;

import java.io.Serializable;

public class Estudante implements Serializable {
    private int codigo;
    private String nome;
    private double teste1;
    private double teste2;

    private double media;


    public Estudante(int codigo, String nome, double teste1, double teste2) {
        this.codigo = codigo;
        this.nome = nome;
        this.teste1 = teste1;
        this.teste2 = teste2;
    }
    public Estudante() {}

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getTeste1() {
        return teste1;
    }

    public void setTeste1(double teste1) {
        this.teste1 = teste1;
    }

    public double getTeste2() {
        return teste2;
    }

    public void setTeste2(double teste2) {
        this.teste2 = teste2;
    }

    public double getMedia() {
        this.media = (teste1 + teste2)/2;
        return media;
    }

    @Override
    public String toString() {
        return "Estudante{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", teste1=" + teste1 +
                ", teste2=" + teste2 +
                '}';
    }

}
