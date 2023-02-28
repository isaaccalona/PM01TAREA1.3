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

public class MainActivity extends AppCompatActivity {
    EditText nombres, apellidos, correo, edad, direccion;
    Button btnagregar;
    Button lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombres = (EditText) findViewById(R.id.nombres);
        apellidos = (EditText) findViewById(R.id.apellidos);
        correo = (EditText) findViewById(R.id.correo);
        edad = (EditText) findViewById(R.id.edad);
        direccion=(EditText) findViewById(R.id.direccion);

        btnagregar = (Button) findViewById(R.id.btnagregar);
        lista = (Button) findViewById(R.id.btn_lista);

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarPersona();
            }
        });

        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityListView.class);
                startActivity(intent);
            }
        });
    }

    private void AgregarPersona()
    {
        try
        {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null,1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("nombress", nombres.getText().toString());
            valores.put("apellidoss", apellidos.getText().toString());
            valores.put("correos", correo.getText().toString());
            valores.put("edads", edad.getText().toString());
            valores.put("direccions",direccion.getText().toString());

            Long Resultado = db.insert(Transacciones.tablapersonas, "id", valores);
            Toast.makeText(this, Resultado.toString(), Toast.LENGTH_SHORT).show();

            ClearScreen();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"No se pudo insertar el dato", Toast.LENGTH_LONG).show();
        }
    }

    private void ClearScreen()
    {
        nombres.setText(Transacciones.Empty);
        apellidos.setText(Transacciones.Empty);
        correo.setText(Transacciones.Empty);
        edad.setText(Transacciones.Empty);
        direccion.setText(Transacciones.Empty);
    }
}