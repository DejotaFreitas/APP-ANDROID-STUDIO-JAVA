package com.dejota.petadote.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dejota.petadote.R;

public class MenuPrincipal extends Fragment {

    private final int[] BOTOES = {R.id.menu_principal_pet, R.id.menu_principal_adocao, R.id.menu_principal_meus_pets, R.id.menu_principal_chat};

    public MenuPrincipal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewInflater =  inflater.inflate(R.layout.fragment_menu_principal, container, false);
        ImageButton botao;
        for (int i = 0; i < BOTOES.length; i++) {
            botao = (ImageButton) viewInflater.findViewById(BOTOES[i]);
            final int numBotao = i;
            botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity activity = getActivity();
                    ((MenuPrincipalInterarcao) activity).menuSetTela(numBotao);
                }
            });
        }
        return viewInflater;
    }

}
