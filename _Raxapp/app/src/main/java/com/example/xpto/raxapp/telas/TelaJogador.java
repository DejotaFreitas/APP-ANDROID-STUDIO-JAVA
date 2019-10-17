package com.example.xpto.raxapp.telas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.adapter.JogadorAdapter;
import com.example.xpto.raxapp.adapter.PessoaAdapter;
import com.example.xpto.raxapp.entityDAO.JogadorDAO;
import com.example.xpto.raxapp.entityDAO.Pessoa;
import com.example.xpto.raxapp.entityDAO.PessoaDAO;
import com.example.xpto.raxapp.global.Global;


public class TelaJogador extends Fragment {

    private GridView listaJogador;
    private ImageButton button_limpar;
    private JogadorAdapter jogadorAdapter;
    private JogadorDAO jdao;
    private PessoaDAO pdao;

    public TelaJogador() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.fragment_tela_jogador, container, false);
        button_limpar = view.findViewById(R.id.tela_pessoa_button_limpar);
        jdao = new JogadorDAO(getActivity());
        pdao = new PessoaDAO(getActivity());

        jogadorAdapter = new JogadorAdapter(getActivity());
        listaJogador = view.findViewById(R.id.tela_jogador_lista_jogadores);
        listaJogador.setAdapter(jogadorAdapter);

        atualizar_lista_jogadores();

        button_limpar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
                    button_limpar.setPadding(10, 10, 10, 10);
                } else {
                    button_limpar.setPadding(0, 0, 0, 0);
                }
                if (arg1.getAction()==MotionEvent.ACTION_UP){
                    for (Pessoa jogador: Global.jogadores) {
                        pdao.insert(jogador);
                    }
                    jdao.deleteAll();
                    atualizar_lista_jogadores();
                }
                return true;
            }
        });

        return view;
    }


    public void atualizar_lista_jogadores(){
        Global.jogadores = jdao.selectAll();
        jogadorAdapter.notifyDataSetChanged();
    }

}
