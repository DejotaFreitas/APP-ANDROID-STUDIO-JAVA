package com.dejota.petadote.conteudo;

import android.content.Intent;
import android.os.Bundle;
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
import com.dejota.petadote.adapter.AdocaoAdapter;
import com.dejota.petadote.conteudo2.AdocaoHistorico;
import com.dejota.petadote.entity.Pet;
import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONArray;
import org.json.JSONObject;

public class Adocao extends Fragment implements RespostaHttp {

    private GridView lista;
    private AdocaoAdapter listaAdapter;
    private Acesso acesso;
    private ProgressBar pb_carregando;
    private TextView mensagem;

    public Adocao() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conteudo_adocao, container, false);
        mensagem = view.findViewById(R.id.adocao_mensagem);
        pb_carregando = view.findViewById(R.id.adocao_pb_carregando);
        lista = view.findViewById(R.id.adocao_lista);
        acesso = new Acesso(getActivity());
        listaAdapter = new AdocaoAdapter(getActivity());
        lista.setAdapter(listaAdapter);
        onClickHistorico(view);
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
            if (Global.listadocao.isEmpty()){
                String url = Constantes.DOMINIO+"android_adocao_listar/"+acesso.getUsuario().getId();
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
        Log.i("LogX", "LogX: "+ response);
        try {
            JSONObject json = new JSONObject(response);
            String respota = json.getString("response");
            JSONArray jsonArray = json.getJSONArray("dados");
            int size = jsonArray.length();
            if ("ok".equals(respota) && size > 0) {
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
                    p.setDonoId(jobj.getInt("dono_id"));
                    Relacao rlc = new Relacao();
                    rlc.setPet(p);
                    rlc.setRelacao(jobj.getString("relacao"));
                    rlc.setTime(jobj.getLong("rtime"));
                    rlc.setUsuarioId(acesso.getUsuario().getId());

                    if (!Global.listadocao.contains(rlc)){
                        Global.listadocao.add(rlc);
                    }
                }
                listaAdapter.notifyDataSetChanged();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (Global.listadocao.isEmpty()){
            if (response==null){
                mensagem.setText("Problemas com a conexão de internet.");
                mensagem.setVisibility(View.VISIBLE);
            } else {
                mensagem.setText("Nenhum pet na lista de adoção.");
                mensagem.setVisibility(View.VISIBLE);
            }
        } else {
            mensagem.setVisibility(View.INVISIBLE);
        }
        pb_carregando.setVisibility(View.INVISIBLE);
    }




    public void onClickHistorico(View view){
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.adocao_fab_historico);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (acesso.isLogado()){
                    Intent i = new Intent(getActivity(), AdocaoHistorico.class);
                    startActivity(i);
                } else {
                    acesso.telalogin();
                }
            }
        });
    }

}
