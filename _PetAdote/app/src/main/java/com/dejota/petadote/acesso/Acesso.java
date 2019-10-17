package com.dejota.petadote.acesso;


import android.content.Context;
import android.content.Intent;

import com.dejota.petadote.dao.UsuarioDAO;
import com.dejota.petadote.entity.Usuario;
import com.dejota.petadote.util.Global;

import java.util.List;

public class Acesso {

    private static Usuario usuario;
    private UsuarioDAO udao;
    private Context context;

    public Acesso(Context context) {
        this.context = context;
        udao = new UsuarioDAO(context);
    }

    public boolean isLogado(){
        if (this.usuario != null){ return true; }
        List<Usuario> listaUsuarios = udao.selectAll();
        if (listaUsuarios.size() == 1){
            this.usuario = listaUsuarios.get(0);
            return true;
        } else {
            if(listaUsuarios.size() > 1) { udao.clear(); }
            return false;
        }
    }

    public void telalogin(){
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
    }

    public boolean logar(Usuario u){
        deslogar();
        udao.insert(u);
        this.usuario = u;
        return true;
    }

    public boolean deslogar(){
        udao.clear();
        this.usuario = null;
        Global.pets.clear();
        Global.offset_pesquisa = 0;
        Global.meuspets.clear();
        Global.listadocao.clear();
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
