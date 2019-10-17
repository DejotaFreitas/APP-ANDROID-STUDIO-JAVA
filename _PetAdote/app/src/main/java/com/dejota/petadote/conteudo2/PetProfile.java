package com.dejota.petadote.conteudo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.arrastar.ArrastarDetecta;
import com.dejota.petadote.arrastar.ArrastarDirecao;
import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.imagem.ImgLoadPet;
import com.dejota.petadote.notificacao.NotificacaoErro;
import com.dejota.petadote.notificacao.NotificacaoSucesso;
import com.dejota.petadote.util.Constantes;
import com.dejota.petadote.util.Global;

import org.json.JSONObject;

public class PetProfile extends AppCompatActivity implements ArrastarDirecao, RespostaHttp {

    private ScrollView conteiner;
    private ImageView iv_foto;
    private TextView tv_nome;
    private TextView tv_tipo;
    private TextView tv_genero;
    private TextView tv_faixa_etaria;
    private TextView tv_tamanho;
    private TextView tv_castrado;
    private TextView tv_descricao;
    private TextView tv_estado;
    private TextView tv_cidade;
    private Button btn_entrar_lista_adocao;
    private Acesso acesso;
    private ProgressBar pb_carregando;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        conteiner = (ScrollView) findViewById(R.id.pet_profile_conteiner);
        iv_foto = (ImageView) findViewById(R.id.pet_profile_foto);
        tv_nome = (TextView) findViewById(R.id.pet_profile_nome);
        tv_tipo = (TextView) findViewById(R.id.pet_profile_tipo);
        tv_genero = (TextView) findViewById(R.id.pet_profile_genero);
        tv_faixa_etaria = (TextView) findViewById(R.id.pet_profile_faixa_etaria);
        tv_tamanho = (TextView) findViewById(R.id.pet_profile_tamanho);
        tv_castrado = (TextView) findViewById(R.id.pet_profile_castrado);
        tv_descricao = (TextView) findViewById(R.id.pet_profile_descricao);
        tv_estado = (TextView) findViewById(R.id.pet_profile_estado);
        tv_cidade = (TextView) findViewById(R.id.pet_profile_cidade);
        btn_entrar_lista_adocao = (Button) findViewById(R.id.pet_profile_entrar_lista_adocao);
        acesso = new Acesso(this);
        pb_carregando = (ProgressBar) findViewById(R.id.pet_profile_pb_carregando);
        index = getIntent().getIntExtra("index", 0);
        atualizar();

        ArrastarDetecta arrasto = new ArrastarDetecta(this);
        conteiner.setOnTouchListener(arrasto);
    }


    public void atualizar(){
        if (Global.petImg.containsKey(Global.pets.get(index).getId())){
            iv_foto.setImageBitmap(Global.petImg.get(Global.pets.get(index).getId()));
        } else {
            new ImgLoadPet(iv_foto, Global.pets.get(index).getId(), Constantes.DOMINIO, Global.pets.get(index).getFoto()).execute();
        }
        tv_nome.setText("Nome: "+Global.pets.get(index).getNome());
        tv_tipo.setText("Tipo: "+Global.pets.get(index).getTipo());
        tv_genero.setText("Gênero: "+Global.pets.get(index).getGenero());
        tv_faixa_etaria.setText("Faixa Etária: "+Global.pets.get(index).getFaixaEtaria());
        tv_tamanho.setText("Tamanho: "+Global.pets.get(index).getTamanho());
        tv_castrado.setText("Castrado: "+Global.pets.get(index).getCastrado());
        tv_descricao.setText("Descrição: "+Global.pets.get(index).getDescricao());
        tv_estado.setText("Estado: "+Global.pets.get(index).getEstado());
        tv_cidade.setText("Cidade: "+Global.pets.get(index).getCidade());
    }




    public void pet_profile_entrarListaAdocao(View v) {
        Log.i("LogX", "ENTRAR FILA ADOÇAO");
        btn_entrar_lista_adocao.setClickable(false);
        if (acesso.isLogado()){
            String url = Constantes.DOMINIO+"android_entrar_lista_adocao/"+acesso.getUsuario().getId()+"/"+Global.pets.get(index).getId();
            new GET(this, url).execute();
            pb_carregando.setVisibility(View.VISIBLE);
        } else {
            acesso.telalogin();
            btn_entrar_lista_adocao.setClickable(true);
            finish();
        }
    }

    @Override
    public void response(String response) {
        try {
            JSONObject json = new JSONObject(response);
            String respota = json.getString("response");
            if ("ok".equals(respota)) {
                Relacao rlc = new Relacao();
                rlc.setPet(Global.pets.get(index));
                rlc.setRelacao("Aguardando");
                rlc.setUsuarioId(acesso.getUsuario().getId());
                if (!Global.listadocao.isEmpty()){
                    Global.listadocao.add(rlc);
                }
                Global.pets.remove(index);
                Intent i = new Intent(this, NotificacaoSucesso.class);
                i.putExtra("sucesso", "Entrou na lista de adoção com sucesso.");
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, NotificacaoErro.class);
//                i.putExtra("erro", "Ocorreu algum problema ao tentar na lista de adoção.");
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
        btn_entrar_lista_adocao.setClickable(true);
        pb_carregando.setVisibility(View.INVISIBLE);
    }


    @Override
    public void arrastouPraDireita() {
        index += 1;
        if (index >= Global.pets.size()){
            index = 0;
        }
        atualizar();
    }

    @Override
    public void arrastouPraEsquerda() {
        index -= 1;
        if (index < 0){
            index = Global.pets.size()-1;
        }
        atualizar();
    }

    public void pet_profile_cancelar(View v) {
        finish();
    }

}
