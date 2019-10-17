package com.dejota.petadote.adapter;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dejota.petadote.R;

import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.imagem.ImgLoadPet;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;


import java.util.ArrayList;


public class AdocaoHistoricoAdapter extends BaseAdapter{

    private Activity activity;
    public ArrayList<Relacao> listadocaohistorico;

    public AdocaoHistoricoAdapter(Activity activity) {
        this.activity =activity;
        listadocaohistorico = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listadocaohistorico.size();
    }

    @Override
    public Object getItem(int i) {
        return listadocaohistorico.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listadocaohistorico.get(i).getPet().getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = view;
        linha = activity.getLayoutInflater().inflate(R.layout.view_adapter_adocao_historico_item_list, viewGroup, false);

        ImageView foto = linha.findViewById(R.id.adocao_historico_item_list_foto);
        TextView nome = linha.findViewById(R.id.adocao_historico_item_list_nome_pet);
        TextView relacao = linha.findViewById(R.id.adocao_historico_item_list_relacao);


        if (Global.petImg.containsKey(listadocaohistorico.get(i).getPet().getId())){
            foto.setImageBitmap(Global.petImg.get(listadocaohistorico.get(i).getPet().getId()));
        } else {
            new ImgLoadPet(foto, listadocaohistorico.get(i).getPet().getId(), Constantes.DOMINIO, listadocaohistorico.get(i).getPet().getFoto()).execute();
        }

        nome.setText(listadocaohistorico.get(i).getPet().getNome());
        relacao.setText(listadocaohistorico.get(i).getRelacao());

        return linha;
    }


}
