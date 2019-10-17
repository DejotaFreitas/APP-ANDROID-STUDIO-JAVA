package com.exemple.aula01_trocatela;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
   }

    public void login(View view) {
        Intent it = new Intent(this, Login.class);
        startActivity(it);
    }

    public void cadastro(View view) {
        Intent it = new Intent(this, Cadastro.class);
        startActivity(it);
    }
    
}
