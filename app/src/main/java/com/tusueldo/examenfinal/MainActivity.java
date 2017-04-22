package com.tusueldo.examenfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tusueldo.examenfinal.clases.Contacto;
import com.tusueldo.examenfinal.clases.ContactoHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> listaContactosMovil;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private ArrayAdapter<String> adapter;
    private ContactoHelper bdContactos;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
       preferencias = getSharedPreferences("CONTACTOS_CASA", Context.MODE_PRIVATE);
        bdContactos = new ContactoHelper(this);
        listaContactosMovil = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaContactosMovil);
        listView.setAdapter(adapter);

    }


    public void procesar(View view) {
        switch (view.getId()) {
            case R.id.botonImpar:
                mostrarContactosImpar();
                break;
            case R.id.botonPAR:
                mostrarContactosPar();
                break;
            case R.id.botonTODOS:
                mostrarContactosTodos();
                break;
        }
    }

    private void mostrarContactosImpar() {
        List<Contacto> contactos = obtenerContactos(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        Toast.makeText(this, contactos.toString(), Toast.LENGTH_SHORT).show();

    }

    private void mostrarContactosPar() {
        List<Contacto> contactos = obtenerContactos(ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        for (Contacto c : contactos) {
            if (!contactos.contains(c)) {
                editor = preferencias.edit();
                editor.putString(String.valueOf(c.getIdContacto()), c.toString());
                editor.commit();
            }
        }
        Map<String, ?> datos = preferencias.getAll();
        adapter.clear();
        for (Map.Entry<String, ?> e : datos.entrySet()) {
            listaContactosMovil.add(e.getValue().toString());
        }
        adapter.addAll(listaContactosMovil);

    }

    private void mostrarContactosTodos() {
        List<Contacto> contactos = obtenerContactos(ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        for (Contacto c : contactos)
            if (!contactos.contains(c))
                bdContactos.insert(c);
        List<String> todosContactos = bdContactos.getAll();
        adapter.clear();
        adapter.addAll(todosContactos);

    }


    private List<Contacto> obtenerContactos(int tipoContact) {
        List<Contacto> lista = new ArrayList<>();
        //Obtengo ID, NOMBRE, TIPO Y NUMERO(NO VACÍO) ordenados por _ID ASC
        String projection[] = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                + "' AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "='" + tipoContact + "' AND " + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Contacts._ID + " ASC";
        Cursor cursorContactos = getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, selectionClause, null, sortOrder);
        //Iteramos con el cursor obtenido
        while (cursorContactos.moveToNext()) {
            int id = cursorContactos.getInt(cursorContactos.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String nombre = cursorContactos.getString(cursorContactos.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME));
            String numero = cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int tipo = cursorContactos.getInt(cursorContactos.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));
            String tipoContacto = obtenerTipo(tipo);
            Contacto contacto = new Contacto(id, nombre, tipoContacto, numero);
            lista.add(contacto);
        }
        cursorContactos.close();
        return lista;
    }

    private String obtenerTipo(int tipo) {

        String tipoContacto = null;

        switch (tipo) {
            case 1:
                tipoContacto = "IMPAR";
                break;
            case 2:
                tipoContacto = "PAR";
                break;
            case 3:
                tipoContacto = "TODOS";
                break;
        }
        return tipoContacto;
    }


}



