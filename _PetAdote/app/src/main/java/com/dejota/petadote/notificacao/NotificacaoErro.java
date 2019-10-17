package com.dejota.petadote.notificacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dejota.petadote.R;

public class NotificacaoErro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao_erro);
        TextView tv = (TextView) findViewById(R.id.notificacao_erro_text);
        String s = getIntent().getStringExtra("erro");
        tv.setText(s);
    }

    public void notificacao_erro_fechar(View v){
        finish();
    }
}
