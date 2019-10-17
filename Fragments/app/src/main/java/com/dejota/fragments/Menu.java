package com.dejota.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Menu extends Fragment {

    private final int[] BOTOES = {R.id.ib1, R.id.ib2, R.id.ib3};

    public Menu() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewInflater = inflater.inflate(R.layout.fragment_menu, container, false);
        ImageButton botao;
        for (int i = 0; i < BOTOES.length; i++) {
            botao = (ImageButton) viewInflater.findViewById(BOTOES[i]);
            final int numBotao = i;
            botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity activity = getActivity();
                    ((MenuComicacao) activity).menu(numBotao);
                }
            });
        }
        return viewInflater;
    }


}
