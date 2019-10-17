package com.dejota.cameraougaleria;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListaContatoAdapter extends BaseAdapter {

    private final List<Contato> contatos;
    private final Activity activity;

    public ListaContatoAdapter(Activity activity, List<Contato> contatos) {
        this.contatos = contatos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.contatos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.contatos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.contatos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = view;
        Contato contato = contatos.get(i);
        Bitmap bitmap;
        if (linha == null){
            linha = this.activity.getLayoutInflater().inflate(R.layout.celula_contato, viewGroup, false);
        }
        TextView nome = (TextView) linha.findViewById(R.id.celua_contato_nome);
        ImageView foto = (ImageView) linha.findViewById(R.id.celua_contato_foto);

        if (i%2==0){
            linha.setBackgroundColor(activity.getResources().getColor(R.color.corPar));
        } else {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.corImpar));
        }

        nome.setText(contato.getNome());
        if(contato.getFoto() != null){
            bitmap = BitmapFactory.decodeFile(contato.getFoto());
        } else {
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher_round);
        }
        foto.setImageBitmap(bitmap);

        return linha;
    }


}
