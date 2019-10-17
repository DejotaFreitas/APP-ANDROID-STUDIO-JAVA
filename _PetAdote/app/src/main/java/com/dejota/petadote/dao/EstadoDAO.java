package com.dejota.petadote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dejota.petadote.entity.Estado;

import java.util.ArrayList;
import java.util.List;

public class EstadoDAO extends SQLiteOpenHelper {


    private static final int VERSAO = 1;
    private static final String DATABASE = "petadote";
    private static final String TABELA = "estado";

    public EstadoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT NOT NULL," +
                "pais_id INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Estado x) {
        ContentValues values = new ContentValues();
        if (x.getId() != null){ values.put("id", x.getId()); }
        values.put("descricao", x.getDescricao());
        values.put("pais_id", x.getPaisId());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public void delete(Estado x) {
        String[] id = {x.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public void update(Estado x) {
        ContentValues values = new ContentValues();
        values.put("descricao", x.getDescricao());
        values.put("pais_id", x.getPaisId());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public List<Estado> selectAll() {
        List<Estado> lista = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        while (cursor.moveToNext()){
            Estado x = new Estado();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            x.setPaisId(cursor.getInt(cursor.getColumnIndex("pais_id")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }



}
