package com.dejota.cameraougaleria;

import java.io.Serializable;

public class Contato implements Serializable {

    private Long id;
    private String nome;
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Nome: "+nome+", Foto: "+foto;
    }
}
