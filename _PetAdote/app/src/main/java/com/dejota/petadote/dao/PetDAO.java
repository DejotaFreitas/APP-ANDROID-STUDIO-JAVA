package com.dejota.petadote.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dejota.petadote.entity.MeuPet;
import com.dejota.petadote.entity.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String DATABASE = "petadote";
    private static final String TABELA = "pet";

    public PetDAO(Context context) {
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
                "foto TEXT NOT NULL" +
                "nome TEXT NOT NULL" +
                "descricao TEXT NOT NULL," +
                "tipo TEXT NOT NULL" +
                "genero TEXT NOT NULL" +
                "faixa_etaria TEXT NOT NULL" +
                "tamanho TEXT NOT NULL" +
                "castrado TEXT NOT NULL" +
                "time INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Pet x) {
        ContentValues values = new ContentValues();
        if (x.getId() != null){ values.put("id", x.getId()); }
        values.put("foto", x.getFoto());
        values.put("nome", x.getNome());
        values.put("descricao", x.getDescricao());
        values.put("tipo", x.getTipo());
        values.put("genero", x.getGenero());
        values.put("faixa_etaria", x.getFaixaEtaria());
        values.put("tamanho", x.getTamanho());
        values.put("castrado", x.getCastrado());
        values.put("time", x.getTime());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public void delete(Pet x) {
        if (x.getId() != null){
            String[] id = {x.getId().toString()};
            getWritableDatabase().delete(TABELA, "id=?", id);
        }
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public void update(Pet x) {
        ContentValues values = new ContentValues();
        if (x.getId() != null){ values.put("id", x.getId()); }
        values.put("foto", x.getFoto());
        values.put("nome", x.getNome());
        values.put("descricao", x.getDescricao());
        values.put("tipo", x.getTipo());
        values.put("genero", x.getGenero());
        values.put("faixa_etaria", x.getFaixaEtaria());
        values.put("tamanho", x.getTamanho());
        values.put("castrado", x.getCastrado());
        values.put("time", x.getTime());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public List<Pet> selectAll() {
        List<Pet> lista = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        while (cursor.moveToNext()){
            Pet x = new Pet();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            x.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            x.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
            x.setGenero(cursor.getString(cursor.getColumnIndex("genero")));
            x.setFaixaEtaria(cursor.getString(cursor.getColumnIndex("faixa_etaria")));
            x.setTamanho(cursor.getString(cursor.getColumnIndex("tamanho")));
            x.setCastrado(cursor.getString(cursor.getColumnIndex("castrado")));
            x.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }

}
