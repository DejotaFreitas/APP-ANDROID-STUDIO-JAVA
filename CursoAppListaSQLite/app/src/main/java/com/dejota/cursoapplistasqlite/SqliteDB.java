package com.dejota.cursoapplistasqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;


public class SqliteDB extends AppCompatActivity{

    private SQLiteDatabase db;
    private Cursor cursor;


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

    public ArrayList<ItemLista> listar(){
        ArrayList<ItemLista> lista = new ArrayList<ItemLista>();
        try{
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            cursor = db.rawQuery("SELECT * FROM lista", null);
            int idxId = cursor.getColumnIndex("id");
            int idxDesc = cursor.getColumnIndex("descricao");
            while (cursor.moveToNext()){
                Log.i("LogX", "Id:"+cursor.getLong(idxId)+", Descricao: "+ cursor.getString(idxDesc));
                lista.add(new ItemLista(cursor.getLong(idxId), cursor.getString(idxDesc)));
            }
        } catch (Exception e){
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
            if (db != null) { db.close(); }
        }
        return lista;
    }


    public void add(String itemLista){
        try {
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            db.execSQL("INSERT INTO lista (descricao) VALUES ('" + itemLista + "')");
        } catch (Exception e) {
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {  db.close();   }
        }
    }


    public void remove(Long id){
        try {
            db = openOrCreateDatabase("lista.db", MODE_PRIVATE, null);
            db.execSQL("DELETE FROM pessoa WHERE id = "+id);
        } catch (Exception e) {
            Log.i("LogX", e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {  db.close();   }
        }
    }


}
