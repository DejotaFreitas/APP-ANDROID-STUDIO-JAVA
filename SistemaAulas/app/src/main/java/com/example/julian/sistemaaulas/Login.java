package com.example.julian.sistemaaulas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button avancar = (Button)findViewById(R.id.entrar);
        avancar.setOnClickListener(onClickEntrar());
    }

    private View.OnClickListener onClickEntrar() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView login = (TextView) findViewById(R.id.edtNome);
                TextView senha = (TextView)findViewById(R.id.edtSenha);
                String stringLogin = login.getText().toString();
                String stringSenha = senha.getText().toString();
                if(stringLogin.equals("dejota") && stringSenha.equals("123")){
                    Intent intent = new Intent(Login.this,TelaInicial.class);
                    intent.putExtra("Nome", stringLogin);
                    startActivity(intent);
                }else
                    alert("Login e senha não conferem!");
            }
        };
    }

    public void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
