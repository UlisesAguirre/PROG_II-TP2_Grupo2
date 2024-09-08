package com.example.tp2_grupo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class listaContactos extends AppCompatActivity {

    private ListView listViewContactos;
    private ArrayList<String> contactosSimples;  // Para mostrar solo nombre, apellido y email
    private ArrayList<JSONObject> contactosCompletos;  // Para almacenar toda la información

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        listViewContactos = findViewById(R.id.lista_contactos);
        cargarContactos();

        // Agregar evento de click en la lista
        listViewContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Abrir la actividad de detalles al hacer clic
                Intent intent = new Intent(listaContactos.this, DetalleContacto.class);
                // Pasar la información del contacto seleccionado
                intent.putExtra("contacto", contactosCompletos.get(position).toString());
                startActivity(intent);
            }
        });
    }

    private void cargarContactos() {
        SharedPreferences preferencias = getSharedPreferences("contactos", MODE_PRIVATE);
        String contactosGuardados = preferencias.getString("lista_contactos", "[]");  // Array vacío por defecto

        contactosSimples = new ArrayList<>();
        contactosCompletos = new ArrayList<>();

        try {
            JSONArray contactosArray = new JSONArray(contactosGuardados);

            for (int i = 0; i < contactosArray.length(); i++) {
                JSONObject contacto = contactosArray.getJSONObject(i);
                contactosCompletos.add(contacto);  // Guardar la información completa

                // Solo agregar nombre, apellido y email para la lista
                String nombre = contacto.getString("nombre");
                String apellido = contacto.getString("apellido");
                String email = contacto.getString("email");
                String contactoSimple = nombre + " " + apellido + " - " + email;
                contactosSimples.add(contactoSimple);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactosSimples);
        listViewContactos.setAdapter(adaptador);
    }
}
