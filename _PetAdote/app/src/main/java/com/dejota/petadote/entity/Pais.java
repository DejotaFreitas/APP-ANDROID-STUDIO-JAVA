package com.dejota.petadote.entity;


public class Pais {

    private Integer id;
    private String descricao;

    public Pais() { }

    public Pais(String descricao) {
        this.descricao = descricao;
    }

    public Pais(Integer id) {
        this.id = id;
    }

    public Pais(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() { return id;  }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
