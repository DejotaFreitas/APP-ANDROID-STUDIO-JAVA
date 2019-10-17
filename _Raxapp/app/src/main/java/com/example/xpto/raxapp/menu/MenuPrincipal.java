package com.example.xpto.raxapp.menu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.xpto.raxapp.R;


public class MenuPrincipal extends Fragment {

    private final int[] BOTOES = {R.id.pessoa  , R.id.jogador, R.id.time, R.id.timefora, R.id.cronometro};

    public MenuPrincipal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       final View viewInflater =  inflater.inflate(R.layout.fragment_menu_principal, container, false);

        for (int i = 0; i < BOTOES.length; i++) {
            final int numBotao = i;
            final ImageButton botao = (ImageButton) viewInflater.findViewById(BOTOES[i]);
            botao.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    if (arg1.getAction()==MotionEvent.ACTION_DOWN){
                        botao.setPadding(10, 10, 10, 10);
                    } else {
                        botao.setPadding(5, 5, 5, 5);
                    }
                    if (arg1.getAction()==MotionEvent.ACTION_UP){
                        Activity activity = getActivity();
                        ((MenuPrincipalInterarcao) activity).menuSetTela(numBotao);
                        for (int j = 0; j < BOTOES.length; j++) {
                            if (j == numBotao){
                                ((ImageButton) viewInflater.findViewById(BOTOES[j])).setBackgroundColor(Color.parseColor("#EEEEEE"));
                            } else {
                                ((ImageButton) viewInflater.findViewById(BOTOES[j])).setBackgroundColor(Color.parseColor("#FFFFFF"));
                            }
                        }
                    }
                    return true;
                }
            });
        }
        return viewInflater;
    }

}
