package com.dejota.petadote.entity;

public class Dependencia {

    private Integer id;
    private Integer paisQnt;
    private Integer estadoQnt;
    private Integer cidadeQnt;

    public Dependencia() {
    }

    public Dependencia(Integer id, Integer paisQnt, Integer estadoQnt, Integer cidadeQnt) {
        this.id = id;
        this.paisQnt = paisQnt;
        this.estadoQnt = estadoQnt;
        this.cidadeQnt = cidadeQnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaisQnt() {
        return paisQnt;
    }

    public void setPaisQnt(Integer paisQnt) {
        this.paisQnt = paisQnt;
    }

    public Integer getEstadoQnt() {
        return estadoQnt;
    }

    public void setEstadoQnt(Integer estadoQnt) {
        this.estadoQnt = estadoQnt;
    }

    public Integer getCidadeQnt() {
        return cidadeQnt;
    }

    public void setCidadeQnt(Integer cidadeQnt) {
        this.cidadeQnt = cidadeQnt;
    }
}
