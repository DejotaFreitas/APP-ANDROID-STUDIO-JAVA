package com.dejota.petadote.entity;


public class Estado {

    private Integer id;
    private String descricao;
    private Integer paisId;

    public Estado() {}

    public Estado(Integer id) {
        this.id = id;
    }

    public Estado(String descricao, Integer paisId) {
        this.descricao = descricao;
        this.paisId = paisId;
    }

    public Estado(Integer id, String descricao, Integer paisId) {
        this.id = id;
        this.descricao = descricao;
        this.paisId = paisId;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPaisId() { return paisId; }

    public void setPaisId(Integer paisId) { this.paisId = paisId; }
}
