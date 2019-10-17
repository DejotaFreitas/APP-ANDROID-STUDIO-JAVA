package com.example.xpto.raxapp.adapter;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xpto.raxapp.R;
import com.example.xpto.raxapp.entityDAO.JogadorDAO;
import com.example.xpto.raxapp.entityDAO.PessoaDAO;
import com.example.xpto.raxapp.global.Global;

public class PessoaAdapter extends BaseAdapter {

    private Activity activity;

    public PessoaAdapter(Activity activity) {
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return Global.pessoas.size();
    }

    @Override
    public Object getItem(int i) {
        return Global.pessoas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Global.pessoas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View linha = view;

        linha = activity.getLayoutInflater().inflate(R.layout.item_list_pessoa, viewGroup, false);

        final ImageButton apagar = linha.findViewById(R.id.item_list_pessoa_apagar);
        final ImageButton add_lista_jogadores = linha.findViewById(R.id.item_list_pessoa_add_lista_jogadores);
        TextView nome = linha.findViewById(R.id.item_list_pessoa_nome);
        nome.setText((i+1)+" - "+Global.pessoas.get(i).getNome());

        final int index = i;

        apagar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
                    apagar.setPadding(10, 10, 10, 10);

                } else {
                    apagar.setPadding(0, 0, 0, 0);
                }
                if (arg1.getAction()==MotionEvent.ACTION_UP){
                    new PessoaDAO(activity).delete(Global.pessoas.get(index));
                    Global.pessoas.remove(index);
                    notifyDataSetChanged();
                }
                return true;
            }
        });


        add_lista_jogadores.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN){
                    add_lista_jogadores.setPadding(10, 10, 10, 10);
                } else {
                    add_lista_jogadores.setPadding(0, 0, 0, 0);
                }
                if (arg1.getAction()==MotionEvent.ACTION_UP){
                    new JogadorDAO(activity).insert(Global.pessoas.get(index));
                    Global.jogadores.add(Global.pessoas.get(index));
                    new PessoaDAO(activity).delete(Global.pessoas.get(index));
                    Global.pessoas.remove(index);
                    notifyDataSetChanged();
                    ((EditText)activity.findViewById(R.id.tela_pessoa_input_nome)).setText("");
                }
                return true;
            }
        });


        return linha;
    }

}
