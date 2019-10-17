package com.exemple.aula01_trocatela;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button telaInicial = (Button)findViewById(R.id.btn_logar);
        telaInicial.setOnClickListener(onClickTelaInicial());
    }

    private View.OnClickListener onClickTelaInicial() {
        return new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, TelaInicial.class);
                startActivity(it);
            }
        };

    }

    public void voltarInicio(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }




}
