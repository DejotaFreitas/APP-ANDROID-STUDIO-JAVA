package com.dejota.petadote.entity;



public class Cidade {

    private Integer id;
    private String descricao;
    private Integer estadoId;

    public Cidade() {}

    public Cidade(Integer id) {
        this.id = id;
    }

    public Cidade(String descricao, Integer estadoId) {
        this.descricao = descricao;
        this.estadoId = estadoId;
    }

    public Cidade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Cidade(Integer id, String descricao, Integer estadoId) {
        this.id = id;
        this.descricao = descricao;
        this.estadoId = estadoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getEstadoId() { return estadoId; }

    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId;  }

}
