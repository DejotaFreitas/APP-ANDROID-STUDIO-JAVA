package com.dejota.petadote.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dejota.petadote.R;
import com.dejota.petadote.conteudo2.MeuPetAdotantes;
import com.dejota.petadote.conteudo2.PetProfile;
import com.dejota.petadote.imagem.ImgLoadPet;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;


public class MeuPetAdapter extends BaseAdapter {

    private Activity activity;

    public MeuPetAdapter(Activity activity) {
        this.activity =activity;
    }

    @Override
    public int getCount() {
        return Global.meuspets.size();
    }

    @Override
    public Object getItem(int i) {
        return Global.meuspets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Global.meuspets.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = view;
        linha = activity.getLayoutInflater().inflate(R.layout.view_adapter_meus_pets_item_list, viewGroup, false);

        ImageView foto = linha.findViewById(R.id.meus_pets_item_list_foto);
        TextView nome = linha.findViewById(R.id.meus_pets_item_list_nome);
        TextView tipo = linha.findViewById(R.id.meus_pets_item_list_tipo);
        TextView genero = linha.findViewById(R.id.meus_pets_item_list_genero);
        TextView num_adotantes = linha.findViewById(R.id.meus_pets_item_list_fila_adocao);
        LinearLayout ll_adotantes = linha.findViewById(R.id.meus_pets_item_list_adotantes);


        if (Global.petImg.containsKey(Global.meuspets.get(i).getId())){
            foto.setImageBitmap(Global.petImg.get(Global.meuspets.get(i).getId()));
        } else {
            new ImgLoadPet(foto, Global.meuspets.get(i).getId(), Constantes.DOMINIO, Global.meuspets.get(i).getFoto()).execute();
        }

        nome.setText(Global.meuspets.get(i).getNome());
        tipo.setText(Global.meuspets.get(i).getTipo());
        genero.setText(Global.meuspets.get(i).getGenero());
        num_adotantes.setText(""+Global.meuspets.get(i).getAdotantes().size());

        final int index = i;
        ll_adotantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MeuPetAdotantes.class);
                intent.putExtra("index",  index);
                activity.startActivity(intent);
            }
        });

        return linha;
    }
}
