package com.dejota.listaarrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] conteudoLista = {"Posição 1", "Posição 2", "Posição 3", "Posição 4", "Posição 5", "Posição 6", "Posição 7",
            "Posição 8", "Posição 9", "Posição 10", "Posição 11", "Posição 12", "Posição 13", "Posição 14", "Posição 15",
            "Posição 16", "Posição 17", "Posição 18", "Posição 19", "Posição 20", "Posição 21"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lista = (ListView) findViewById(R.id.lista);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                conteudoLista
        );
        lista.setAdapter(aa);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(),conteudoLista[i], Toast.LENGTH_SHORT).show();
            }
        });

    }
}
