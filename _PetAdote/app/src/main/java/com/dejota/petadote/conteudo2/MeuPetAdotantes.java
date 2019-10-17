package com.dejota.petadote.conteudo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dejota.petadote.R;
import com.dejota.petadote.acesso.Acesso;
import com.dejota.petadote.adapter.AdocaoHistoricoAdapter;
import com.dejota.petadote.adapter.MeuPetAdotantesAdapter;
import com.dejota.petadote.entity.Pet;
import com.dejota.petadote.entity.Relacao;
import com.dejota.petadote.httpt.GET;
import com.dejota.petadote.util.Constantes;

import org.json.JSONArray;
import org.json.JSONObject;

public class MeuPetAdotantes extends AppCompatActivity {

    private GridView lista;
    private MeuPetAdotantesAdapter listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_pet_adotantes);

        lista = (GridView) findViewById(R.id.adotantes_lista);
        int petId = getIntent().getIntExtra("index", 0);
        listaAdapter = new MeuPetAdotantesAdapter(this, petId);
        lista.setAdapter(listaAdapter);

    }


    public void meu_pet_adotantantes_fechar (View view){
        finish();
    }


}
