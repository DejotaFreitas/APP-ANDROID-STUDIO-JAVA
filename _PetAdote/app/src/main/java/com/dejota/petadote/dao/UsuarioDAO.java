package com.dejota.petadote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dejota.petadote.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String DATABASE = "petadote";
    private static final String TABELA = "usuario";

    public UsuarioDAO(Context context) { super(context, DATABASE, null, VERSAO);  }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "senha TEXT NOT NULL," +
                "cidade_id INTEGER NOT NULL," +
                "time INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void insert(Usuario x) {
        ContentValues values = new ContentValues();
        if (x.getId() != null){ values.put("id", x.getId()); }
        values.put("nome", x.getNome());
        values.put("email", x.getEmail());
        values.put("senha", x.getSenha());
        values.put("cidade_id", x.getCidadeId());
        values.put("time", x.getTime());
        getWritableDatabase().insert(TABELA, null, values);

    }

    public void delete(Usuario x) {
        String[] id = {x.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", id);
    }

    public void clear() {
        getWritableDatabase().delete(TABELA, null, null);
    }

    public void update(Usuario x) {
        ContentValues values = new ContentValues();
        values.put("nome", x.getNome());
        values.put("email", x.getEmail());
        values.put("senha", x.getSenha());
        values.put("cidade_id", x.getCidadeId());
        values.put("time", x.getTime());
        String[] id =  {x.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", id);
    }

    public List<Usuario> selectAll() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        List<Usuario> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            Usuario x = new Usuario();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            x.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            x.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            x.setCidadeId(cursor.getInt(cursor.getColumnIndex("cidade_id")));
            x.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            lista.add(x);
        }
        cursor.close();
        return lista;
    }

    public Usuario select() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+TABELA+";", null);
        if (cursor.moveToFirst()){
            Usuario x = new Usuario();
            x.setId(cursor.getInt(cursor.getColumnIndex("id")));
            x.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            x.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            x.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            x.setCidadeId(cursor.getInt(cursor.getColumnIndex("cidade_id")));
            x.setTime(cursor.getLong(cursor.getColumnIndex("time")));
            cursor.close();
            return x;
        }
        cursor.close();
        return null;
    }

    public boolean isLogado() {
        if (select()==null){ return false; }
        return true;
    }


}
