package com.example.xpto.raxapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xpto.raxapp.menu.MenuPrincipalInterarcao;
import com.example.xpto.raxapp.telas.TelaCronometro;
import com.example.xpto.raxapp.telas.TelaJogador;
import com.example.xpto.raxapp.telas.TelaPessoa;
import com.example.xpto.raxapp.telas.TelaTime;
import com.example.xpto.raxapp.telas.TelaTimeFora;

public class MainActivity extends AppCompatActivity implements MenuPrincipalInterarcao {

    private Fragment tela = null;
    private Fragment[] telas = {new TelaPessoa(), new TelaJogador(), new TelaTime(), new TelaTimeFora(), new TelaCronometro()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuSetTela(4);
    }

    @Override
    public void menuSetTela(int indexTela) {

        switch (indexTela){
            case 0: tela = telas[indexTela];  break;
            case 1: tela = telas[indexTela];  break;
            case 2: tela = telas[indexTela];  break;
            case 3: tela = telas[indexTela];  break;
            case 4: tela = telas[indexTela];  break;
            default: tela = telas[4]; break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteiner, tela);
        ft.commit();
    }

}
