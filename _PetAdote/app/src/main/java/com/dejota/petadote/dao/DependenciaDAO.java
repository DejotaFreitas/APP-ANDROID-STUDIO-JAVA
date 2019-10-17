package com.dejota.petadote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dejota.petadote.entity.Dependencia;


public class DependenciaDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String DATABASE = "petadote";
    private static final String TABELA = "config";
    private static final String _ID = "id";
    private static final String PAIS_QNT = "pais_qnt";
    private static final String ESTADO_QNT = "estado_qnt";
    private static final String CIDADE_QNT = "cidade_qnt";


    public DependenciaDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                _ID +" INTEGER  PRIMARY KEY AUTOINCREMENT," +
                PAIS_QNT+" INTEGER NOT NULL," +
                ESTADO_QNT+" INTEGER NOT NULL," +
                CIDADE_QNT+" INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Dependencia x) {
        ContentValues values = new ContentValues();
        values.put(_ID, x.getId());
        values.put(PAIS_QNT, x.getPaisQnt());
        values.put(ESTADO_QNT, x.getEstadoQnt());
        values.put(CIDADE_QNT, x.getCidadeQnt());
        getWritableDatabase().insert(TABELA, null, values);
    }

    public void update(Dependencia x) {
        ContentValues values = new ContentValues();
        values.put(PAIS_QNT, x.getPaisQnt());
        values.put(ESTADO_QNT, x.getEstadoQnt());
        values.put(CIDADE_QNT, x.getCidadeQnt());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, _ID +"=?", id);
    }

    public void delete(Dependencia x) {
        String[] id = {x.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public Dependencia selectId(Integer id) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM "+TABELA+" WHERE "+ _ID +"=?", new String[] {id.toString()});
        Dependencia x = new Dependencia();
        if (cursor.moveToFirst()){
            x.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            x.setPaisQnt(cursor.getInt(cursor.getColumnIndex(PAIS_QNT)));
            x.setEstadoQnt(cursor.getInt(cursor.getColumnIndex(ESTADO_QNT)));
            x.setCidadeQnt(cursor.getInt(cursor.getColumnIndex(CIDADE_QNT)));
        } else {
            x = null;
        }
        cursor.close();
        return x;
    }




}
