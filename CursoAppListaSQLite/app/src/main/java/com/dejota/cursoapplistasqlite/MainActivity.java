package com.dejota.cursoapplistasqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edt;
    private ListView listv;
    private Button btn;
    private SQLiteDatabase db;
    private Cursor cursor;

    private ArrayAdapter<String> listaAdapter;
    private ArrayList<Integer> listaIds;
    private ArrayList<String> listaItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt = (EditText) findViewById(R.id.editText);
        listv = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.button);

        criar();
        listar();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemLista = edt.getText().toString();
                if (!"".equals(itemLista)) {
                    addicionar(itemLista);
                    listar();
                }
            }});

        listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removerConfirma(i);
                return false;
            }
        });

    }


    public void criar(){
        try{
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS lista(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "descricao VARCHAR(50))");
        } catch (Exception e){
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) { db.close(); }
        }
    }

    public void listar(){
        listaIds = new ArrayList<>();
        listaItens = new ArrayList<>();
        listaAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listaItens);
        listv.setAdapter(listaAdapter);

         try{
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            cursor = db.rawQuery("SELECT * FROM lista ORDER BY descricao", null);
            int idxId = cursor.getColumnIndex("id");
            int idxDesc = cursor.getColumnIndex("descricao");
            while (cursor.moveToNext()){
                listaIds.add(cursor.getInt(idxId));
                listaItens.add(cursor.getString(idxDesc));
                            }
        } catch (Exception e){
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
            if (db != null) { db.close(); }
        }
    }


    public void addicionar(String text){
        try {
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            db.execSQL("INSERT INTO lista (descricao) VALUES ('" + text + "')");
            Toast.makeText(getApplicationContext(), text+" foi salvo.", Toast.LENGTH_SHORT).show();
            edt.setText("");
        } catch (Exception e) {
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {  db.close();   }
        }
    }


    public void remove(int id){
        try {
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            db.execSQL("DELETE FROM lista WHERE id="+id);
            listar();
        } catch (Exception e) {
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {  db.close();   }
        }
    }


    public void removerConfirma(int i){

        final int posicao = i;

        AlertDialog.Builder adc = new AlertDialog.Builder(this);
        adc.setTitle("Apagar ?");
        adc.setMessage("Deseja apagar "+listaItens.get(posicao)+" ?");

        adc.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String item = listaItens.get(posicao);
                remove(listaIds.get(posicao));
                Toast.makeText(getApplicationContext(), item+" foi apagado.", Toast.LENGTH_SHORT).show();
            }
        });

        adc.setNegativeButton("Não", null);

        AlertDialog ad = adc.create();
        ad.show();

    }


}
