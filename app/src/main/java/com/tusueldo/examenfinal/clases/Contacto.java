package com.tusueldo.examenfinal.clases;

import android.content.ContentValues;

import com.tusueldo.examenfinal.clases.ContactoContract.*;


/**
 * Created by David on 26/11/2016.
 */


public class Contacto {
    private int idContacto;
    private String tipoContacto;
    private String nombre;
    private String numero;

    public Contacto(int idContacto, String nombre, String tipoContacto, String numero) {
        this.idContacto = idContacto;
        this.nombre = nombre;
        this.tipoContacto = tipoContacto;
        this.numero = numero;
    }


    @Override
    public String toString() {
        return idContacto + "- " + nombre + " - " + tipoContacto + " - " + numero + "\n";
    }

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactoEntry.NOMBRE, nombre);
        contentValues.put(ContactoEntry.TIPOCONTACTO, tipoContacto);
        contentValues.put(ContactoEntry.NUMERO, numero);
        return contentValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacto)) return false;

        Contacto contacto = (Contacto) o;

        if (getIdContacto() != contacto.getIdContacto()) return false;
        if (tipoContacto != null ? !tipoContacto.equals(contacto.tipoContacto) : contacto.tipoContacto != null)
            return false;
        if (nombre != null ? !nombre.equals(contacto.nombre) : contacto.nombre != null)
            return false;
        return numero != null ? numero.equals(contacto.numero) : contacto.numero == null;

    }

    @Override
    public int hashCode() {
        int result = getIdContacto();
        result = 31 * result + (tipoContacto != null ? tipoContacto.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }



}

