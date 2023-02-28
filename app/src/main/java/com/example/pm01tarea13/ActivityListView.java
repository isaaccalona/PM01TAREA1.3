package com.example.pm01tarea13;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.pm01tarea13.configuracion.SQLiteConexion;
import com.example.pm01tarea13.transacciones.Personas;
import com.example.pm01tarea13.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityListView extends AppCompatActivity {
    // Variables Globales
    SQLiteConexion conexion;
    ListView listView;
    ArrayList<Personas> listapersonas;
    ArrayList<String> Arreglopersonas;

    Button eliminar,actualizar;

    Intent intent;
    final Context context = this;

    int previousPosition=10;
    int count=0;
    long pM=0;

    Personas personas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        intent = new Intent(getApplicationContext(), Actualizarp.class);

        listView = (ListView) findViewById(R.id.listview);

        eliminar = (Button) findViewById(R.id.beliminar);
        actualizar = (Button) findViewById(R.id.bactulizar);

        ObtenerListaPersonas();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Arreglopersonas);
        listView.setAdapter(adp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                previousPosition=i;
                count=1;
                pM=System.currentTimeMillis();
                personas = listapersonas.get(i);
                setContactoSeleccionado();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("Eliminar Contacto");

                alertDialogBuilder
                        .setMessage("¿Está seguro de eliminar el contacto?")
                        .setCancelable(false)
                        .setPositiveButton("SI",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                eliminarContacto();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    }

    private void ObtenerListaPersonas()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        listapersonas = new ArrayList<Personas>();

        // Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM "+Transacciones.tablapersonas, null );

        while(cursor.moveToNext())
        {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));
            person.setDireccion(cursor.getString(5));
            listapersonas.add(person);
        }

        cursor.close();
        FillList();
    }

    private void FillList()
    {
        Arreglopersonas = new ArrayList<String>();
        for(int i = 0; i < listapersonas.size(); i++)
        {
            Arreglopersonas.add(listapersonas.get(i).getId() + " | "+
                    listapersonas.get(i).getNombres() + " | "+
                    listapersonas.get(i).getApellidos() + " | ");
        }
    }

    private void eliminarContacto() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        int obtenerCodigo = personas.getId();

        db.delete(Transacciones.tablapersonas,Transacciones.id +" = "+ obtenerCodigo, null);

        Toast.makeText(getApplicationContext(), "Registro eliminado con exito, Codigo " + obtenerCodigo
                ,Toast.LENGTH_LONG).show();
        db.close();

        Intent intent = new Intent(this, ActivityListView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();


    }
    private void setContactoSeleccionado() {

        intent.putExtra("ids", personas.getId()+"");
        intent.putExtra("nombress", personas.getNombres());
        intent.putExtra("apellidoss", personas.getApellidos()+"");
        intent.putExtra("edads", personas.getEdad()+"");
        intent.putExtra("correos", personas.getCorreo());
        intent.putExtra("direccions", personas.getDireccion());

    }

}