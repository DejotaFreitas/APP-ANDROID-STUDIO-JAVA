package com.dejota.listacolunas;

public class Produto {

    private int imagemId;
    private String nome;
    private String descricao;

    public Produto(int imagemId, String nome, String descricao) {
        this.imagemId = imagemId;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getImagemId() {
        return imagemId;
    }

    public void setImagemId(int imagemId) {
        this.imagemId = imagemId;
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
}
