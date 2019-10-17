package com.dejota.aula01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SegundaTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_tela);

        TextView tv = (TextView) findViewById(R.id.text_view_id);
        String s = getIntent().getStringExtra("id_texto_enviado");
        tv.setText(s);
    }
}
