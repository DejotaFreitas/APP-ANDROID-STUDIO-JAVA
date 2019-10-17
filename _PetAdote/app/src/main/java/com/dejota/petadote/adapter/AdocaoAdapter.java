package com.dejota.petadote.adapter;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.imagem.ImgLoadPet;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONObject;


public class AdocaoAdapter extends BaseAdapter implements RespostaHttp{

    private Activity activity;
    private Acesso acesso;
    private int index;

    public AdocaoAdapter(Activity activity) {
        this.activity =activity;
        acesso = new Acesso(activity);
    }

    @Override
    public int getCount() {
        return Global.listadocao.size();
    }

    @Override
    public Object getItem(int i) {
        return Global.listadocao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Global.listadocao.get(i).getPet().getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = view;
        linha = activity.getLayoutInflater().inflate(R.layout.view_adapter_adocao_item_list, viewGroup, false);

        ImageView foto = linha.findViewById(R.id.adocao_item_list_foto);
        TextView nome = linha.findViewById(R.id.adocao_item_list_nome_pet);
        TextView relacao = linha.findViewById(R.id.adocao_item_list_relacao);
        Button sair_lista = linha.findViewById(R.id.adocao_item_lista_sair_fila);
        Button inicia_char = linha.findViewById(R.id.adocao_item_lista_chat);

        if (Global.petImg.containsKey(Global.listadocao.get(i).getPet().getId())){
            foto.setImageBitmap(Global.petImg.get(Global.listadocao.get(i).getPet().getId()));
        } else {
            new ImgLoadPet(foto, Global.listadocao.get(i).getPet().getId(), Constantes.DOMINIO, Global.listadocao.get(i).getPet().getFoto()).execute();
        }

        nome.setText(Global.listadocao.get(i).getPet().getNome());
        relacao.setText(Global.listadocao.get(i).getRelacao());

        final String pet_id = Global.listadocao.get(i).getPet().getId().toString();
        final String usuario_id = acesso.getUsuario().getId().toString();
        final int idx = i;
        sair_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Constantes.DOMINIO+"android_sair_lista_adocao/"+usuario_id+"/"+pet_id;
                new GET(AdocaoAdapter.this, url).execute();
                index = idx;
            }
        });

        return linha;
    }

    @Override
    public void response(String response) {
        Log.i("LogX", "AdocaoAdapter: "+ response);
        try {
            JSONObject json = new JSONObject(response);
            String respota = json.getString("response");
            if ("ok".equals(respota)) {
                Toast.makeText(activity, "Saiu da lista de adoção com sucesso.", Toast.LENGTH_LONG).show();
                Global.pets.add(Global.listadocao.get(index).getPet());
                Global.listadocao.remove(index);
                notifyDataSetChanged();
            } else {
                Toast.makeText(activity, "Erro ao sair da lista de adoção.", Toast.LENGTH_LONG).show();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }


}
