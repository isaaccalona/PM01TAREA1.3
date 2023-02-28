package com.example.pm01tarea13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm01tarea13.configuracion.SQLiteConexion;
import com.example.pm01tarea13.transacciones.Transacciones;

public class Actualizarp extends AppCompatActivity {

    EditText nom,apell,edad,correo,direccion,id;
    Button btnAc;
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizarp);

        nom = (EditText) findViewById(R.id.nombresc);
        apell = (EditText) findViewById(R.id.apellidosc);
        correo = (EditText) findViewById(R.id.correoc);
        edad = (EditText) findViewById(R.id.edadc);
        direccion=(EditText) findViewById(R.id.direccionc);
        id=(EditText) findViewById(R.id.codigoc);

        btnAc = (Button) findViewById(R.id.btnactualizar);
        id.setText(getIntent().getStringExtra("ids"));
        nom.setText(getIntent().getStringExtra("nombress"));
        apell.setText(getIntent().getStringExtra("apellidoss"));
        edad.setText(getIntent().getStringExtra("edads"));
        correo.setText(getIntent().getStringExtra("correos"));
        direccion.setText(getIntent().getStringExtra("direccions"));


        btnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactoactualizar();
            }
        });


    }

    public void contactoactualizar(){
        SQLiteDatabase db = conexion.getWritableDatabase();

        String ObjCodigo = id.getText().toString();

        ContentValues valores = new ContentValues();

        valores.put(Transacciones.nombres, nom.getText().toString());
        valores.put(Transacciones.apellidos, apell.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.direccion, direccion.getText().toString());

        try {
            db.update(Transacciones.tablapersonas,valores, Transacciones.id +" = "+ ObjCodigo, null);
            db.close();
            Toast.makeText(getApplicationContext(),"Los datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ActivityListView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"No se actualizaron los datos", Toast.LENGTH_SHORT).show();
        }
    }
}