package com.dejota.listacolunas;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XPTO on 18/10/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Produto>{

    public GridViewAdapter(@NonNull Context context,
                           @LayoutRes int resource,
                           @NonNull List<Produto> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(null==v){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }

        Produto p = getItem(position);
        ImageView imagem = (ImageView) v.findViewById(R.id.imagem);
        TextView nome = (TextView) v.findViewById(R.id.nome);
        TextView descricao = (TextView) v.findViewById(R.id.descricao);

        imagem.setImageResource(p.getImagemId());
        nome.setText(p.getNome());
        descricao.setText(p.getDescricao());

        return v;
    }
}
