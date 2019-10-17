package com.dejota.cameraougaleria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String DATABASE = "agenda";
    private static final String TABELA = "contato";

    public ContatoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+TABELA+" (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "caminhoFoto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void inserirContato(Contato c) {
        ContentValues values = new ContentValues();
        values.put("nome", c.getNome());
        values.put("caminhoFoto", c.getFoto());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public void removerContato(Contato c) {
        String[] id = {c.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void alterarContato(Contato c) {
        ContentValues values = new ContentValues();
        values.put("nome", c.getNome());
        values.put("caminhoFoto", c.getFoto());
        String[] id =  {c.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public List<Contato> carregarContato() {
        List<Contato> listacontatos = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        while (cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getLong(cursor.getColumnIndex("id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
            listacontatos.add(c);
        }
        cursor.close();
        return listacontatos;
    }

}
