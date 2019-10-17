package com.dejota.petadote.conteudo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.notificacao.NotificacaoErro;
import com.dejota.petadote.notificacao.NotificacaoSucesso;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONObject;

public class ConfirmarDoacao extends AppCompatActivity implements RespostaHttp{

    private int adotanteIndex;
    private int petIndex;
    private Button confirmar_doacao;
    private ProgressBar pb_carregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_doacao);
        adotanteIndex = getIntent().getIntExtra("adotanteIndex", 0);
        petIndex = getIntent().getIntExtra("petIndex", 0);

        ImageView foto_pet = (ImageView) findViewById(R.id.confirmar_doacao_pet_foto);
        foto_pet.setImageBitmap(Global.petImg.get(Global.meuspets.get(petIndex).getId()));

        ImageView foto_adotante = (ImageView) findViewById(R.id.confirmar_doacao_adotante_foto);
        foto_adotante.setImageBitmap(Global.usuarioImg.get(Global.meuspets.get(petIndex).getAdotantes().get(adotanteIndex).getId()));

        TextView nome_pet = (TextView) findViewById(R.id.confirmar_doacao_pet_nome);
        nome_pet.setText(Global.meuspets.get(petIndex).getNome());

        TextView nome_adotante = (TextView) findViewById(R.id.confirmar_doacao_adotante_nome);
        nome_adotante.setText(Global.meuspets.get(petIndex).getAdotantes().get(adotanteIndex).getNome());

        confirmar_doacao = (Button) findViewById(R.id.confirmar_doacao_aceito);
        pb_carregando = (ProgressBar) findViewById(R.id.confirmar_doacao_pg_carregando);
    }

    public void confirmar_doacao_confirmar(View v){
        Log.i("LogX", "CONFIRMAR DOAÇÂO");
        confirmar_doacao.setClickable(false);
        String url = Constantes.DOMINIO+"android_meus_pets_aceitar_adotante/"+
                Global.meuspets.get(petIndex).getAdotantes().get(adotanteIndex).getId()
                +"/"+
                Global.meuspets.get(petIndex).getId();

        new GET(this, url).execute();
        pb_carregando.setVisibility(View.VISIBLE);
    }

    @Override
    public void response(String response) {
        try {
            JSONObject json = new JSONObject(response);
            String respota = json.getString("response");
            if ("ok".equals(respota)) {
                Global.meuspets.remove(petIndex);
                Intent i = new Intent(this, NotificacaoSucesso.class);
                i.putExtra("sucesso", "Doação completa com sucesso.");
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, NotificacaoErro.class);
                i.putExtra("erro", response);
                startActivity(i);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            if (response==null){
                Intent i = new Intent(this, NotificacaoErro.class);
                i.putExtra("erro", "Problemas com a conexão de internet.");
                startActivity(i);
            }
        }
        confirmar_doacao.setClickable(true);
        pb_carregando.setVisibility(View.INVISIBLE);

    }





    public void confirmar_doacao_cancelar(View v){
        finish();
    }



}
