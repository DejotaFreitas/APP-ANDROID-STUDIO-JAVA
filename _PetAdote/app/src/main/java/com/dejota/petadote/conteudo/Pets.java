package com.dejota.petadote.conteudo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.adapter.PetAdapter;
import com.dejota.petadote.entity.Pet;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONArray;
import org.json.JSONObject;


public class Pets extends Fragment implements RespostaHttp {

    private GridView lista;
    private PetAdapter listaAdapter;
    private Acesso acesso;
    private ProgressBar pb_carregando;
    private TextView mensagem;
    boolean loadAcaoScrollDown = true;

    public Pets() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conteudo_pets, container, false);

        mensagem = view.findViewById(R.id.pets_mensagem);
        pb_carregando = view.findViewById(R.id.pets_pb_carregando);
        lista = view.findViewById(R.id.pets_lista);

        listaAdapter = new PetAdapter(getActivity());
        lista.setAdapter(listaAdapter);
        acesso = new Acesso(getActivity());

        lista.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView scrollview, int i) {
                View view = scrollview.getChildAt(scrollview.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollview.getHeight() + scrollview.getScrollY()));
                if (diff == 0 && loadAcaoScrollDown){
                    atualizar();
                    loadAcaoScrollDown = false;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizar();
        listaAdapter.notifyDataSetChanged();
    }

    public void atualizar(){
            if (acesso.isLogado()){
                String url = Constantes.DOMINIO+"android_pets/"+acesso.getUsuario().getId()+"/"+ Global.offset_pesquisa;
                new GET(this, url).execute();
            } else {
                new GET(this, Constantes.DOMINIO+"android_pets_all/"+"/"+ Global.offset_pesquisa).execute();
            }
            pb_carregando.setVisibility(View.VISIBLE);
    }


    @Override
    public void response(String response) {
        Log.i("LogX", "Pets: "+ response);
        try {
            JSONObject json = new JSONObject(response);
            String respota = json.getString("response");
            JSONArray jsonArray = json.getJSONArray("dados");
            int size = jsonArray.length();
            if ("ok".equals(respota) && size > 0) {
                boolean is_add = false;
                for (int i = 0; i < size; i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    Pet p = new Pet();
                    p.setId(jobj.getInt("id"));
                    p.setNome(jobj.getString("nome"));
                    p.setDescricao(jobj.getString("descricao"));
                    p.setTipo(jobj.getString("tipo"));
                    p.setGenero(jobj.getString("genero"));
                    p.setFaixaEtaria(jobj.getString("faixa_etaria"));
                    p.setTamanho(jobj.getString("tamanho"));
                    p.setCastrado(jobj.getString("castrado"));
                    p.setTime(jobj.getLong("time"));
                    p.setFoto(jobj.getString("foto"));
                    p.setEstado(jobj.getString("estado"));
                    p.setCidade(jobj.getString("cidade"));
                    p.setDonoId(jobj.getInt("dono_id"));
                    if (!Global.pets.contains(p)){
                        Global.pets.add(p);
                        is_add = true;
                    }
                }
                if (is_add){
                    listaAdapter.notifyDataSetChanged();
                    Global.offset_pesquisa += 1;
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (Global.pets.isEmpty()){
            if (response==null){
                mensagem.setText("Problemas com a conexão de internet.");
                mensagem.setVisibility(View.VISIBLE);
            } else {
                mensagem.setText("Nenhum pet encontrado.");
                mensagem.setVisibility(View.VISIBLE);
            }
        } else {
            mensagem.setVisibility(View.INVISIBLE);
        }
        pb_carregando.setVisibility(View.INVISIBLE);
        loadAcaoScrollDown = true;
    }



}
