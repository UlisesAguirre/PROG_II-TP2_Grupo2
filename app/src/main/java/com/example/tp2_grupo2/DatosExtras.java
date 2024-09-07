package com.example.tp2_grupo2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DatosExtras extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_extras);

        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");
        String telefono = getIntent().getStringExtra("telefono");
        String sp_telefono = getIntent().getStringExtra("sp_telefono");
        String direccion = getIntent().getStringExtra("direccion");
        String email = getIntent().getStringExtra("email");
        String nacimiento = getIntent().getStringExtra("nacimiento");
    }
}