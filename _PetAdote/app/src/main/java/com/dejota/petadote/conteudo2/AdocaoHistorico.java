package com.dejota.petadote.conteudo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.adapter.AdocaoAdapter;
import com.dejota.petadote.adapter.AdocaoHistoricoAdapter;
import com.dejota.petadote.entity.Pet;
import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdocaoHistorico extends AppCompatActivity implements RespostaHttp{

    private GridView lista;
    private AdocaoHistoricoAdapter listaAdapter;
    private Acesso acesso;
    private ProgressBar pb_carregando;
    private TextView mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adocao_historico);
        mensagem = (TextView) findViewById(R.id.adocao_historico_mensagem);
        pb_carregando = (ProgressBar) findViewById(R.id.adocao_historico_pb_carregando);
        lista = (GridView) findViewById(R.id.adocao_historico_lista);
        acesso = new Acesso(this);
        listaAdapter = new AdocaoHistoricoAdapter(this);
        lista.setAdapter(listaAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        atualizar();
        listaAdapter.notifyDataSetChanged();

    }

    public void atualizar() {
        if (acesso.isLogado()){
            if (listaAdapter.listadocaohistorico.isEmpty()){
                String url = Constantes.DOMINIO+"android_adocao_historico/"+acesso.getUsuario().getId();
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
        Log.i("LogX", "ADOCAO HISTORICO: "+ response);
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
                    p.setFoto(jobj.getString("foto"));
                    Relacao rlc = new Relacao();
                    rlc.setPet(p);
                    rlc.setRelacao(jobj.getString("relacao"));
                    rlc.setUsuarioId(acesso.getUsuario().getId());

                    if (!listaAdapter.listadocaohistorico.contains(rlc)){
                        listaAdapter.listadocaohistorico.add(rlc);
                    }
                }
                listaAdapter.notifyDataSetChanged();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAdapter.listadocaohistorico.isEmpty()){
            if (response==null){
                mensagem.setText("Problemas com a conexão de internet.");
                mensagem.setVisibility(View.VISIBLE);
            } else {
                mensagem.setText("Nenhum pet no seu histórico.");
                mensagem.setVisibility(View.VISIBLE);
            }
        } else {
            mensagem.setVisibility(View.INVISIBLE);
        }
        pb_carregando.setVisibility(View.INVISIBLE);
    }


    public void adocao_historico_cancelar (View view){
        finish();
    }

}
