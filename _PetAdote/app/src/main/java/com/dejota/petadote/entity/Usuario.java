package com.dejota.petadote.entity;

public class Usuario {

    private Integer id;
    private String foto;
    private String nome;
    private String email;
    private String senha;
    private Integer cidadeId;
    private Long time;

    public Usuario() { }

    public Usuario(Integer id, String nome, String email, String senha, Integer cidade_id, Long time) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cidadeId = cidade_id;
        this.time = time;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
