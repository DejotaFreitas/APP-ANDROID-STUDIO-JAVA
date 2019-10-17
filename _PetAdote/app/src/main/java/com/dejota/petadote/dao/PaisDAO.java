package com.dejota.petadote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dejota.petadote.entity.Pais;

import java.util.ArrayList;
import java.util.List;

public class PaisDAO extends SQLiteOpenHelper {


    private static final int VERSAO = 1;
    private static final String DATABASE = "petadote";
    private static final String TABELA = "pais";

    public PaisDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Pais x) {
        ContentValues values = new ContentValues();
        if (x.getId() != null){ values.put("id", x.getId()); }
        values.put("descricao", x.getDescricao());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public void delete(Pais x) {
        String[] id = {x.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public void update(Pais x) {
        ContentValues values = new ContentValues();
        values.put("descricao", x.getDescricao());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public List<Pais> selectAll() {
        List<Pais> lista = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        while (cursor.moveToNext()){
            Pais x = new Pais();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }





}
