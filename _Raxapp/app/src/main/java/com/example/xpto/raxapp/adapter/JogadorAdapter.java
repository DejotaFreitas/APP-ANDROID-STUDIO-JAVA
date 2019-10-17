package com.example.xpto.raxapp.adapter;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.entityDAO.JogadorDAO;
import com.example.xpto.raxapp.entityDAO.PessoaDAO;
import com.example.xpto.raxapp.global.Global;

public class JogadorAdapter extends BaseAdapter {

    private Activity activity;
    private PessoaDAO pdao;

    public JogadorAdapter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return Global.jogadores.size();
    }

    @Override
    public Object getItem(int i) {
        return Global.jogadores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Global.jogadores.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        
        View linha = view;

        linha = activity.getLayoutInflater().inflate(R.layout.item_list_jogador,
                viewGroup, false);

        final ImageButton remover_jogadores = linha.findViewById(R.id.item_list_jogador_remover);
        TextView nome = linha.findViewById(R.id.item_list_jogador_nome);
        nome.setText((i+1)+" - "+Global.jogadores.get(i).getNome());

        final int index = i;

        remover_jogadores.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
                    remover_jogadores.setPadding(10, 10, 10, 10);
                } else {
                    remover_jogadores.setPadding(0, 0, 0, 0);
                }
                if (arg1.getAction()==MotionEvent.ACTION_UP){
                    new PessoaDAO(activity).insert(Global.jogadores.get(index));
                    Global.pessoas.add(Global.jogadores.get(index));
                    new JogadorDAO(activity).delete(Global.jogadores.get(index));
                    Global.jogadores.remove(index);
                    notifyDataSetChanged();}
                return true;
            }
        });


        return linha;

    }
}
