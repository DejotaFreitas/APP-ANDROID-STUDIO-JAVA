package com.dejota.petadote.entity;


public class Relacao {

    private Pet pet;
    private Integer usuarioId;
    private String relacao;
    private Long time;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getRelacao() {
        return relacao;
    }

    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Relacao){
            return this.usuarioId == ((Relacao) obj).getUsuarioId() &&
                    this.pet.getId() == ((Relacao) obj).pet.getId();
        }
        return false;
    }

}
