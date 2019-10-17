package com.dejota.petadote.menuretratil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.acesso.Login;

public class MenuRetratil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_retratil);
    }


    public void menuretratil_login (View v){
        Intent i = new Intent(MenuRetratil.this, Login.class);
        startActivity(i);
    }

    public void menuretratil_deslogar (View v){
       new Acesso(this).deslogar();
        finish();
    }

}
