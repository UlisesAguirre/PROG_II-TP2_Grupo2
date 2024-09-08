package com.example.tp2_grupo2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class DetalleContacto extends AppCompatActivity {

    private TextView textViewDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        textViewDetalles = findViewById(R.id.textViewDetalles);

        // Obtener el contacto seleccionado
        String contactoString = getIntent().getStringExtra("contacto");

        try {
            // Convertir el string en un objeto JSON
            JSONObject contacto = new JSONObject(contactoString);

            String nombre = contacto.getString("nombre");
            String apellido = contacto.getString("apellido");
            String telefono = contacto.getString("telefono");
            String spTelefono = contacto.getString("spTelefono");
            String email = contacto.getString("email");
            String spEmail = contacto.getString("spEmail");
            String nacimiento = contacto.getString("nacimiento");
            String nivelEstudios = contacto.getString("nivelEstudios");
            String intereses = contacto.getString("intereses");
            boolean recibirInfo = contacto.getBoolean("recibirInfo");

            String recibirInfoTexto = recibirInfo ? "Sí" : "No";

            // Crear la información completa del contacto
            String contactoInfo = "Nombre: " + nombre + " " + apellido + "\n"
                    + "Teléfono: " + telefono + " (" + spTelefono + ")\n"
                    + "Email: " + email + " (" + spEmail + ")\n"
                    + "Fecha de nacimiento: " + nacimiento + "\n"
                    + "Nivel de estudios: " + nivelEstudios + "\n"
                    + "Intereses: " + intereses + "\n"
                    + "Recibir información: " + recibirInfoTexto;

            // Mostrar los detalles en el TextView
            textViewDetalles.setText(contactoInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

