package com.dejota.petadote.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.entity.Cidade;

import java.util.ArrayList;
import java.util.List;

public class CidadeAdapter extends BaseAdapter {

    private final List<Cidade> cidades;
    private final Context context;

    public CidadeAdapter(Context context, String[] arrayStrings) {
        this.context = context;
        this.cidades = new ArrayList<>();
        for (String s:arrayStrings) {
            String[] item = s.split("-");
            this.cidades.add(new Cidade(Integer.parseInt(item[0]), item[1]));
        }
    }

    @Override
    public int getCount() {
        return this.cidades.size();
    }

    @Override
    public Object getItem(int i) {
        return this.cidades.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.cidades.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.z_spinner_item_custom, viewGroup, false);
        }
        TextView tv = (TextView) view.findViewById(R.id.z_spinner_custom);
        tv.setText(cidades.get(i).getDescricao());
        return view;

    }

}
