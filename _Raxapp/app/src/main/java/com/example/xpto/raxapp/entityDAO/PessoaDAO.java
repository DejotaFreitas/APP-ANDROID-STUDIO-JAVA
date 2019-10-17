package com.example.xpto.raxapp.entityDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.xpto.raxapp.entityDAO.Pessoa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XPTO on 28/03/2018.
 */

public class PessoaDAO  extends SQLiteOpenHelper {

    private static final int VERSAO = 2;
    private static final String DATABASE = "raxapp";
    private static final String TABELA = "pessoa";

    public PessoaDAO(Context context) { super(context, DATABASE, null, VERSAO);  }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Pessoa x) {
        ContentValues values = new ContentValues();
        if (x.getId() != null){ values.put("id", x.getId()); }
        values.put("nome", x.getNome());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public void delete(Pessoa x) {
        String[] id = {x.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public void update(Pessoa x) {
        ContentValues values = new ContentValues();
        values.put("nome", x.getNome());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public ArrayList<Pessoa> selectAll() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+" ORDER BY nome;", null);
        ArrayList<Pessoa> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            Pessoa x = new Pessoa();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }

    public ArrayList<Pessoa> buscarPorNome(String nome) {
        nome = nome.toLowerCase();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+" WHERE lower(nome) LIKE '%"+nome+"%' ORDER BY nome;", null);
        ArrayList<Pessoa> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            Pessoa x = new Pessoa();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }

}

