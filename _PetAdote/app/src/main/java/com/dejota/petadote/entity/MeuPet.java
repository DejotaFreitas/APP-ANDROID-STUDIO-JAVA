package com.dejota.petadote.entity;


import java.util.ArrayList;
import java.util.List;

public class MeuPet {

    private Integer id;
    private String foto;
    private String nome;
    private String descricao;
    private String tipo;
    private String genero;
    private String faixaEtaria;
    private String tamanho;
    private String castrado;
    private List<Adotante> adotantes;
    private Long time;

    public MeuPet() {
        adotantes = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }

    public List<Adotante> getAdotantes() {
        return adotantes;
    }

    public void setAdotantes(List<Adotante> adotantes) {
        this.adotantes = adotantes;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MeuPet){
            return this.id == ((MeuPet) obj).getId();
        }
        return false;
    }
}
