package com.dejota.petadote.acesso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dejota.petadote.httpt.POST;
import com.dejota.petadote.imagem.IMG;
import com.dejota.petadote.R;
import com.dejota.petadote.entity.Usuario;
import com.dejota.petadote.httpt.RespostaHttp;
import com.dejota.petadote.notificacao.NotificacaoErro;
import com.dejota.petadote.notificacao.NotificacaoSucesso;
import com.dejota.petadote.util.Constantes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements RespostaHttp {

    private EditText edtemail;
    private EditText edtsenha;
    private Button btn_logar;
    private ProgressBar pb_carregando;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtemail = (EditText) findViewById(R.id.cadastro_email);
        edtsenha = (EditText) findViewById(R.id.cadastro_senha);
        btn_logar = (Button) findViewById(R.id.login_logar);
        pb_carregando = (ProgressBar) findViewById(R.id.login_pb_carregando);
        pb_carregando.setVisibility(View.INVISIBLE);
    }

    public void login_cancelar (View v){
        finish();
    }

    public void login_cadastro (View v){
        Intent i = new Intent(Login.this, Cadastro.class);
        startActivity(i);
        finish();
    }

    public void login_logar (View v){
        btn_logar.setClickable(false);
        String email = edtemail.getText().toString();
        String senha = edtsenha.getText().toString();

        if ("".equals(email) ||"".equals(senha)) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            btn_logar.setClickable(true);
            return;
        }

        if (email.length() < 5) {
            Toast.makeText(getApplicationContext(), "Email deve ter 5 caracteres no mínimo.", Toast.LENGTH_SHORT).show();
            btn_logar.setClickable(true);
            return;
        }

        if (senha.length() < 4) {
            Toast.makeText(getApplicationContext(), "Senha deve ter 4 caracteres no mínimo.", Toast.LENGTH_SHORT).show();
            btn_logar.setClickable(true);
            return;
        }

        pb_carregando.setVisibility(View.VISIBLE);

        Map<String, String> parametros = new HashMap<>();
        parametros.put("email", email);
        parametros.put("senha", senha);
        new POST(this, Constantes.DOMINIO+"android_login", parametros, null, null, null).execute();
    }

    @Override
    public void response(String response) {
        try{
            JSONObject json = new JSONObject(response);
            String respostaServer = json.getString("response");

            if ("ok".equals(respostaServer)) {
                try {
                    Usuario u = new Usuario();
                    u.setId(Integer.parseInt(json.getString("id")));
                    u.setNome(json.getString("nome"));
                    u.setEmail(json.getString("email"));
                    u.setSenha(json.getString("senha"));
                    u.setCidadeId(Integer.parseInt(json.getString("cidade_id")));
                    u.setTime(Long.parseLong(json.getString("time")));
                    u.setFoto(IMG.download(this, Constantes.DOMINIO, json.getString("foto")));

                    Acesso uc = new Acesso(this);
                    uc.logar(u);

                    Intent i = new Intent(this, NotificacaoSucesso.class);
                    i.putExtra("sucesso", "Login Sucesso!!!");
                    startActivity(i);
                    finish();

                } catch (Exception e){
                    Intent i = new Intent(this, NotificacaoErro.class);
                    i.putExtra("erro", "Erro ao efetuar o login, tente novamente.");
                    startActivity(i);
                }
            } else {
                Intent i = new Intent(this, NotificacaoErro.class);
                i.putExtra("erro", respostaServer);
                startActivity(i);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            Intent i = new Intent(this, NotificacaoErro.class);
            i.putExtra("erro", response);
            startActivity(i);
        }

        btn_logar.setClickable(true);
        pb_carregando.setVisibility(View.INVISIBLE);

    }



}
