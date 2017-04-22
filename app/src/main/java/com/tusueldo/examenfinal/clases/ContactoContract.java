package com.tusueldo.examenfinal.clases;

import android.provider.BaseColumns;


import android.provider.BaseColumns;

/**
 * Created by David on 12/11/2016.
 */

public class ContactoContract {

    public static final String TABLE_NAME = "contactos";
    /*CREATE TABLE contacto (_id INTEGER PRIMARY KEY, nombre TEXT, tipo TEXT, numero TEXT )*/
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ContactoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ContactoEntry.NOMBRE + " TEXT NOT NULL, " +
            ContactoEntry.TIPOCONTACTO + " TEXT NOT NULL, " +
            ContactoEntry.NUMERO + " TEXT NOT NULL, UNIQUE(" + ContactoEntry.NUMERO + ")) ";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    private ContactoContract() {
    }

    public static abstract class ContactoEntry implements BaseColumns {
        public static final String NOMBRE = "nombre";
        public static final String TIPOCONTACTO = "tipoContacto";
        public static final String NUMERO = "numero";


    }
}
