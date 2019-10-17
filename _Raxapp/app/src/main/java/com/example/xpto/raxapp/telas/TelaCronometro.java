package com.example.xpto.raxapp.telas;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.cronometro.Cronometro;
import com.example.xpto.raxapp.cronometro.CronometroInteracao;


public class TelaCronometro extends Fragment implements CronometroInteracao {


    private Cronometro cronometro = new Cronometro(8*60*1000, this);;
    private Button iniciar;
    private Button pausar;
    private Button zerar;
    private TextView tempo;


    public TelaCronometro() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tela_cronometro, container, false);
        this.iniciar = view.findViewById(R.id.tela_cronometro_iniciar);
        this.pausar = view.findViewById(R.id.tela_cronometro_pausar);
        this.zerar = view.findViewById(R.id.tela_cronometro_zerar);
        this.tempo = view.findViewById(R.id.tela_cronometro_tempo);
        this.tempo.setText(cronometro.getTempo());


        this.iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cronometro.iniciar();
            }
        });

        this.pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cronometro.pausar();
            }
        });

        this.zerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cronometro.zerar();
            }
        });

        return view;
    }


    @Override
    public void tempoRolando(String tempo) {
        this.tempo.setText(tempo);
    }

    @Override
    public void tempoFinalizado(String tempo) {
        this.tempo.setText(tempo);
        MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.acabou);
        mp.start();
    }
}
