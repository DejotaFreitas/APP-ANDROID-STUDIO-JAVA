package com.dejota.aula01;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clicarTela(View view) {

        //=============Toast===================================

        Toast t = Toast.makeText(getApplicationContext(), "Mensagem de alerta do Toast", Toast.LENGTH_SHORT);
        t.show();

        //==========AlertDialog================================

        AlertDialog.Builder adc = new AlertDialog.Builder(this);
        adc.setIcon(R.drawable.pata);
        adc.setTitle("Titulo do AlertDialog");
        adc.setMessage("Titulo do AlertDialog");

        adc.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "SIM", Toast.LENGTH_SHORT).show();
            }
        });

        adc.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "NAO", Toast.LENGTH_SHORT).show();
            }
        });

        adc.setNeutralButton("Neutro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "NEUTRO", Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog ad = adc.create();
        ad.show();


    }


    //TRANSFERIR VALOR PARA OUTRA TELA ATRAVES INTENT

    public void irSegundaTela (View view){

        Intent it = new Intent(MainActivity.this, SegundaTela.class);
        it.putExtra("id_texto_enviado", "Conteudo vindo da primeira tela");
        startActivity(it);
    }



}
