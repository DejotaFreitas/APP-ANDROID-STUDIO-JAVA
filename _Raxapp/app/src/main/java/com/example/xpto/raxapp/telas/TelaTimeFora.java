package com.example.xpto.raxapp.telas;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.global.Global;

import java.util.Random;


public class TelaTimeFora extends Fragment {

    private Button random_numero;
    private ImageButton limpar_numeros;
    private TextView numeros;

    public TelaTimeFora() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_tela_time_fora, container, false);
        random_numero = view.findViewById(R.id.tela_time_fora_random_numero);
        limpar_numeros = view.findViewById(R.id.tela_time_fora_limpar_numeros);
        numeros = view.findViewById(R.id.tela_time_fora_numeros);

        numeros.setText(Global.times_fora_string_global);

        random_numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random_numero();
            }
        });

        limpar_numeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpar_numeros();
            }
        });


        return view;
    }


    public void random_numero(){
        Random r = new Random();
        Integer ri = null;
        do {
            if (Global.times_fora_nunmeros_saidos.size() >= 10){
                limpar_numeros();
            }
            ri = r.nextInt(10)+1;
        } while (Global.times_fora_nunmeros_saidos.contains(ri));
        Global.times_fora_nunmeros_saidos.add(ri);
        random_numero.setText(""+ri);
        if (Global.times_fora_string_global.isEmpty()){
            Global.times_fora_string_global += ""+ri;
        } else {
            Global.times_fora_string_global += " - "+ri;
        }
        numeros.setText(Global.times_fora_string_global);
        random_numero.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                random_numero.setEnabled(true);
                random_numero.setText("?");
            }
        },2000);
    }

    public void limpar_numeros(){
        Global.times_fora_string_global = "";
        numeros.setText(Global.times_fora_string_global);
        random_numero.setText("?");
        Global.times_fora_nunmeros_saidos.clear();
    }

}
