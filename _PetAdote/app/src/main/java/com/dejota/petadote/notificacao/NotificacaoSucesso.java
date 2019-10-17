package com.dejota.petadote.notificacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dejota.petadote.R;

public class NotificacaoSucesso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao_sucesso);
        TextView tv = (TextView) findViewById(R.id.notificacao_sucesso_text);
        String s = getIntent().getStringExtra("sucesso");
        tv.setText(s);
    }

    public void notificacao_sucesso_fechar(View v){
        finish();
    }
}
