package com.example.julian.sistemaaulas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginCadastrese extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cadastrese);
    }

    public void login(View view){
        Intent it = new Intent(this, Login.class);
        startActivity(it);
    }

    public void cadastrese(View view){
        Intent it = new Intent(this, Cadastrese.class);
        startActivity(it);
    }
}