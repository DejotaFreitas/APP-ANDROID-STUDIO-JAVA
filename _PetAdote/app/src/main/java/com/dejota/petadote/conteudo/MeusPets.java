package com.dejota.petadote.conteudo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.adapter.MeuPetAdapter;
import com.dejota.petadote.conteudo2.CadastroPet;
import com.dejota.petadote.entity.Adotante;
import com.dejota.petadote.entity.MeuPet;
import com.dejota.petadote.entity.Usuario;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONArray;
import org.json.JSONObject;


public class MeusPets extends Fragment implements RespostaHttp {

    private GridView lista;
    private MeuPetAdapter listaAdapter;
    private Acesso acesso;
    private ProgressBar pb_carregando;
    private TextView mensagem;

    public MeusPets() {  }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conteudo_meus_pets, container, false);
        mensagem = view.findViewById(R.id.meus_pets_mensagem);
        pb_carregando = view.findViewById(R.id.meus_pets_pb_carregando);
        lista = view.findViewById(R.id.meus_pets_lista);
        acesso = new Acesso(getActivity());
        listaAdapter = new MeuPetAdapter(getActivity());
        lista.setAdapter(listaAdapter);
        onClickCadastrarPet(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizar();
        listaAdapter.notifyDataSetChanged();

    }

    public void atualizar() {
        if (acesso.isLogado()){
            if (Global.meuspets.isEmpty()){
                String url = Constantes.DOMINIO+"android_meus_pets/"+acesso.getUsuario().getId();
                new GET(this, url).execute();
                pb_carregando.setVisibility(View.VISIBLE);
            } else {
                mensagem.setVisibility(View.INVISIBLE);
            }
        } else {
            mensagem.setText("Efetue o login.");
            mensagem.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void response(String response) {
        Log.i("LogX", "MeusPets: "+ response);
        try {
            JSONObject json = new JSONObject(response);
            String respota = json.getString("response");
            JSONArray jsonArray = json.getJSONArray("dados");
            int size = jsonArray.length();
            if ("ok".equals(respota) && size > 0) {
                for (int i = 0; i < size; i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    MeuPet mp = new MeuPet();
                    mp.setId(jobj.getInt("id"));
                    mp.setNome(jobj.getString("nome"));
                    mp.setDescricao(jobj.getString("descricao"));
                    mp.setTipo(jobj.getString("tipo"));
                    mp.setGenero(jobj.getString("genero"));
                    mp.setFaixaEtaria(jobj.getString("faixa_etaria"));
                    mp.setTamanho(jobj.getString("tamanho"));
                    mp.setCastrado(jobj.getString("castrado"));
                    mp.setTime(jobj.getLong("time"));
                    mp.setFoto(jobj.getString("foto"));
                    JSONArray adotantes = jobj.getJSONArray("adotantes");
                    int size_adotatnes = adotantes.length();
                    for (int j = 0; j < size_adotatnes; j++) {
                        JSONObject adotante = adotantes.getJSONObject(j);
                        Adotante adt = new Adotante();
                        adt.setId(adotante.getInt("id"));
                        adt.setNome(adotante.getString("nome"));
                        adt.setFoto(adotante.getString("foto"));
                        adt.setEstado(adotante.getString("estado"));
                        adt.setCidade(adotante.getString("cidade"));
                        adt.setTime(adotante.getLong("time"));
                        mp.getAdotantes().add(adt);
                    }
                    if (!Global.meuspets.contains(mp)){
                        Global.meuspets.add(mp);
                    }
                }
                listaAdapter.notifyDataSetChanged();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (Global.meuspets.isEmpty()){
            if (response==null){
                mensagem.setText("Problemas com a conexão de internet.");
                mensagem.setVisibility(View.VISIBLE);
            } else {
                mensagem.setText("Nenhum pet cadastrado");
                mensagem.setVisibility(View.VISIBLE);
            }
        } else {
            mensagem.setVisibility(View.INVISIBLE);
        }
        pb_carregando.setVisibility(View.INVISIBLE);
    }


    public void onClickCadastrarPet(View view){
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.meus_pet_fab_cadastrar_pet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (acesso.isLogado()){
                    Intent i = new Intent(getActivity(), CadastroPet.class);
                    startActivity(i);
                } else {
                    acesso.telalogin();
                }
            }
        });
    }


}
