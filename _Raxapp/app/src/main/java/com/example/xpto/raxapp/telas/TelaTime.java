package com.example.xpto.raxapp.telas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.entityDAO.JogadorDAO;
import com.example.xpto.raxapp.entityDAO.Pessoa;
import com.example.xpto.raxapp.global.Global;

import java.util.ArrayList;
import java.util.Collections;

public class TelaTime extends Fragment {

    private Button times_aleatorios;
    private Button times_ordem_chegada;

    private TextView times;



    public TelaTime() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_tela_time, container, false);
        times_aleatorios = view.findViewById(R.id.times_aleatorios);
        times_ordem_chegada = view.findViewById(R.id.times_ordem_chegada);

        times = view.findViewById(R.id.tela_time_times);
        times.setText(Global.times_string_global);

        Global.jogadores = new JogadorDAO(getActivity()).selectAll();

        times_aleatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                times_aleatorios_sortiar();
            }
        });

        times_ordem_chegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                times_ordem_chegada_sortiar();
            }
        });


        return view;
    }


    public void times_aleatorios_sortiar(){
        ArrayList<Pessoa> jogadores_temp = new ArrayList<>();
        jogadores_temp.addAll(Global.jogadores);
        Collections.shuffle(jogadores_temp);
        String times_str = "\n\n TIME 1\n";
        for (int i = 1; i <= jogadores_temp.size(); i++){
            times_str += "  -  "+jogadores_temp.get(i-1).getNome()+"\n";
            if (i%4 == 0){
                times_str += "\n\n";
                if (i + 1 <= jogadores_temp.size()){
                    times_str += "TIME "+(i/4+1)+"\n";
                }
            }
        }
        Global.times_string_global = times_str;
        times.setText(Global.times_string_global);
    }


    public void times_ordem_chegada_sortiar(){

        int numero_jogadores = Global.jogadores.size();
        int quantidade_times_fechado = numero_jogadores/8;
        int quantidades_jogadores_restante = numero_jogadores%8;

        Log.i("LOGI", "numero_jogadores: "+numero_jogadores);
        Log.i("LOGI", "quantidade_times_fechado: "+quantidade_times_fechado);
        Log.i("LOGI", "quantidades_jogadores_restante: "+quantidades_jogadores_restante);

        ArrayList<Pessoa> jogadores_sorteados = new ArrayList<>();

        for (int i = 0; i < quantidade_times_fechado; i++) {
            ArrayList<Pessoa> jogadores_temp = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                jogadores_temp.add(Global.jogadores.get(8*i+j));
            }
            Collections.shuffle(jogadores_temp);
            jogadores_sorteados.addAll(jogadores_temp);
        }

        if (quantidades_jogadores_restante > 0) {
            ArrayList<Pessoa> jogadores_restantes_temp = new ArrayList<>();
            int loops_restante = numero_jogadores - quantidades_jogadores_restante;
            for (int i = (numero_jogadores-1); i >= loops_restante; i--) {
                jogadores_restantes_temp.add(Global.jogadores.get(i));
            }
            Collections.shuffle(jogadores_restantes_temp);
            jogadores_sorteados.addAll(jogadores_restantes_temp);
        }

        for (int i = 0; i < jogadores_sorteados.size()-1; i++) {
            Log.i("LOGI", "----TIMES----: "+Global.jogadores.get(i).getNome());
        }


        String times_str = "\n\n TIME 1\n";
        for (int i = 1; i <= jogadores_sorteados.size(); i++){
            times_str += "  -  "+jogadores_sorteados.get(i-1).getNome()+"\n";
            if (i%4 == 0){
                times_str += "\n\n";
                if (i + 1 <= jogadores_sorteados.size()){
                    times_str += "TIME "+(i/4+1)+"\n";
                }
            }
        }
        Global.times_string_global = times_str;
        times.setText(Global.times_string_global);
    }


}
