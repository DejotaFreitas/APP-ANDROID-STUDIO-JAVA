package com.dejota.petadote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dejota.petadote.conteudo.Adocao;
import com.dejota.petadote.conteudo.Chat;
import com.dejota.petadote.menu.MenuPrincipalInterarcao;
import com.dejota.petadote.conteudo.MeusPets;
import com.dejota.petadote.conteudo.Pets;
import com.dejota.petadote.menuretratil.MenuRetratil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MenuPrincipalInterarcao {

    private Fragment tela = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuSetTela(0);
    }

    @Override
    public void menuSetTela(int indexTela) {
        switch (indexTela){
            case 0: tela = new Pets();  break;
            case 1: tela = new Adocao();  break;
            case 2: tela = new MeusPets();  break;
            case 3: tela = new Chat();  break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteiner, tela);
        ft.commit();
    }



    public void menu_retratil(View view){
        Intent it = new Intent(MainActivity.this, MenuRetratil.class);
        startActivity(it);
    }








}
