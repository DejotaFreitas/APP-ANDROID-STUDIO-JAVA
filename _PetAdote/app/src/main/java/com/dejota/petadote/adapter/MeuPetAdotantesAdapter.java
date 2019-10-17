package com.dejota.petadote.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dejota.petadote.R;
import com.dejota.petadote.conteudo2.ConfirmarDoacao;
import com.dejota.petadote.conteudo2.MeuPetAdotantes;
import com.dejota.petadote.entity.Adotante;
import com.dejota.petadote.imagem.ImgLoadUsuario;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;


public class MeuPetAdotantesAdapter extends BaseAdapter{

    private Activity activity;
    private Integer index;

    public MeuPetAdotantesAdapter(Activity activity, Integer index) {
        this.index = index;
        this.activity =activity;
    }

    @Override
    public int getCount() {
        return Global.meuspets.get(index).getAdotantes().size();
    }

    @Override
    public Object getItem(int i) {
        return Global.meuspets.get(index).getAdotantes().get(i);
    }

    @Override
    public long getItemId(int i) {
        return Global.meuspets.get(index).getAdotantes().get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = view;
        linha = activity.getLayoutInflater().inflate(R.layout.view_adapter_adotante_item_list, viewGroup, false);

        ImageView foto = linha.findViewById(R.id.adotante_item_list_foto);
        TextView nome = linha.findViewById(R.id.adotante_item_list_nome);
        TextView estado = linha.findViewById(R.id.adotante_item_list_estado);
        TextView cidade = linha.findViewById(R.id.adotante_item_list_cidade);

        Button aceitar_adotante = linha.findViewById(R.id.adotante_item_lista_aceitar);
        Button inicia_char = linha.findViewById(R.id.adotante_item_lista_chat);

        Adotante adt = Global.meuspets.get(index).getAdotantes().get(i);

        if (Global.usuarioImg.containsKey(adt.getId())){
            foto.setImageBitmap(Global.usuarioImg.get(adt.getId()));
        } else {
            new ImgLoadUsuario(foto, adt.getId(), Constantes.DOMINIO, adt.getFoto()).execute();
        }

        nome.setText(adt.getNome());
        estado.setText(adt.getEstado());
        cidade.setText(adt.getCidade());

        final int adotanteIndex = i;
        aceitar_adotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ConfirmarDoacao.class);
                intent.putExtra("adotanteIndex",  adotanteIndex);
                intent.putExtra("petIndex",  index);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        return linha;
    }


}
