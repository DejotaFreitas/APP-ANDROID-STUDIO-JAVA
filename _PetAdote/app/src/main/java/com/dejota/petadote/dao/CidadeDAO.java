package com.dejota.petadote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dejota.petadote.entity.Cidade;

import java.util.ArrayList;
import java.util.List;

public class CidadeDAO extends SQLiteOpenHelper {

    private SQLiteDatabase dbLeitor;

    private static final int VERSAO = 1;
    private static final String DATABASE = "petadote";
    private static final String TABELA = "cidade";

    public CidadeDAO(Context context) { super(context, DATABASE, null, VERSAO);  }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT NOT NULL," +
                "estado_id INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Cidade x) {
        try{
            ContentValues values = new ContentValues();
            if (x.getId() != null){ values.put("id", x.getId()); }
            values.put("descricao", x.getDescricao());
            values.put("estado_id", x.getEstadoId());
            getWritableDatabase().insert(TABELA, null, values);
        } catch (Exception e){}
    }

    public void delete(Cidade x) {
        String[] id = {x.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public void update(Cidade x) {
        ContentValues values = new ContentValues();
        values.put("descricao", x.getDescricao());
        values.put("estado_id", x.getEstadoId());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public List<Cidade> selectAll() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        List<Cidade> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            Cidade x = new Cidade();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            x.setEstadoId(cursor.getInt(cursor.getColumnIndex("estado_id")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }

    public List<Cidade> selectAllForEstadoId(long estado_id) {
        String[] e_id = {String.valueOf(estado_id)};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+" WHERE estado_id=?;", e_id);
        List<Cidade> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            Cidade x = new Cidade();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            x.setEstadoId(cursor.getInt(cursor.getColumnIndex("estado_id")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }

    public Cidade selectId(Integer id) {
        openLeitor();
        Cursor cursor = dbLeitor.rawQuery(
                "SELECT * FROM "+TABELA+" WHERE id=?", new String[] {id.toString()});
        Cidade x = new Cidade();
        if (cursor.moveToFirst()){
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            x.setEstadoId(cursor.getInt(cursor.getColumnIndex("estado_id")));
        } else {
            x = null;
        }
        cursor.close();
        closeLeitor();
        return x;
    }


    public void openLeitor(){
        dbLeitor = getReadableDatabase();
    }

    public void closeLeitor(){
        dbLeitor.close();
    }



}
