package com.tusueldo.examenfinal.clases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 12/11/2016.
 */

public class ContactoHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactos.db";

    public ContactoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactoContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContactoContract.DROP_TABLE);
        onCreate(db);
    }

    public void insert(Contacto contacto) {

        try {
            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insertOrThrow(ContactoContract.TABLE_NAME, null, contacto.toContentValues());
        } catch (SQLException e) {
            Log.e("ERROR: ", e.toString());
            sqLiteDatabase.close();
        }
        sqLiteDatabase.close();

    }

    public List<String> getAll() {
        sqLiteDatabase = getReadableDatabase();
        List<String> datos = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(ContactoContract.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContactoContract.ContactoEntry._ID));
            String nombre = cursor.getString(cursor.getColumnIndex(ContactoContract.ContactoEntry.NOMBRE));
            String tipo = cursor.getString(cursor.getColumnIndex(ContactoContract.ContactoEntry.TIPOCONTACTO));
            String numero = cursor.getString(cursor.getColumnIndex(ContactoContract.ContactoEntry.NUMERO));
            Contacto contacto = new Contacto(id, nombre, tipo, numero);
            datos.add(contacto.toString());
        }
        sqLiteDatabase.close();

        return datos;
    }


}
