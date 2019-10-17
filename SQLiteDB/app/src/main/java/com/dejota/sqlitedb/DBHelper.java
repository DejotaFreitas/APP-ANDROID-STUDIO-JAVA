package com.dejota.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String NOMEDB = "banco.db";
    public static final int VERSAO = 1;


    public DBHelper(Context context) {
        super(context, NOMEDB, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `usuario` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`nome`\tTEXT NOT NULL,\n" +
                "\t`email`\tTEXT NOT NULL,\n" +
                "\t`senha`\tTEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
