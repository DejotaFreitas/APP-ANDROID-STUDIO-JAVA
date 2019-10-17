package com.dejota.petadote.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.conteudo2.PetProfile;
import com.dejota.petadote.util.Global;
import com.dejota.petadote.imagem.ImgLoadPet;
import com.dejota.petadote.util.Constantes;


public class PetAdapter extends BaseAdapter{

    private Activity activity;

    public PetAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Global.pets.size();
    }

    @Override
    public Object getItem(int i) {
        return Global.pets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Global.pets.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = view;
        linha = activity.getLayoutInflater().inflate(R.layout.view_adapter_pets_item_list, viewGroup, false);

        ImageView foto = linha.findViewById(R.id.pets_item_list_foto);
        TextView nome = linha.findViewById(R.id.pets_item_list_nome);

        nome.setText(Global.pets.get(i).getNome());

        if (Global.petImg.containsKey(Global.pets.get(i).getId())){
            foto.setImageBitmap(Global.petImg.get(Global.pets.get(i).getId()));
        } else {
            new ImgLoadPet(foto, Global.pets.get(i).getId(), Constantes.DOMINIO, Global.pets.get(i).getFoto()).execute();
        }
        final int index = i;
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PetProfile.class);
                intent.putExtra("index",  index);
                activity.startActivity(intent);
            }
        });

        return linha;
    }



}
