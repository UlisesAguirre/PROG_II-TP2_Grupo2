package com.example.tp2_grupo2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class DatosExtras extends AppCompatActivity {

    private RadioGroup rg_nivelEstudios;
    private RadioButton rb_primarioIncompleto, rb_primarioCompleto, rb_secundarioIncompleto, rb_secundarioCompleto, rb_otros;
    private CheckBox cb_deporte, cb_arte, cb_musica, cb_tecnologia;
    private Switch sw_recibirInformacion;
    private Button btn_guardar;
    private String nivelEstudiosSeleccionado;
    private boolean recibirInfo;

    private String nombre, apellido, telefono, sp_telefono, direccion, email, sp_email, nacimiento;
    private StringBuilder interesesSeleccionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_extras);

        nombre = getIntent().getStringExtra("nombre");
        apellido = getIntent().getStringExtra("apellido");
        telefono = getIntent().getStringExtra("telefono");
        sp_telefono = getIntent().getStringExtra("sp_telefono");
        direccion = getIntent().getStringExtra("direccion");
        email = getIntent().getStringExtra("email");
        sp_email = getIntent().getStringExtra("sp_email");
        nacimiento = getIntent().getStringExtra("nacimiento");

        // Inicializar los componentes de la interfaz
        rg_nivelEstudios = findViewById(R.id.radio_group_nivel_estudios);
        rb_primarioIncompleto = findViewById(R.id.radio_primario_incompleto);
        rb_primarioCompleto = findViewById(R.id.radio_primario_completo);
        rb_secundarioIncompleto = findViewById(R.id.radio_secundario_incompleto);
        rb_secundarioCompleto = findViewById(R.id.radio_secundario_completo);
        rb_otros = findViewById(R.id.radio_otros);

        cb_deporte = findViewById(R.id.checkbox_deporte);
        cb_arte = findViewById(R.id.checkbox_arte);
        cb_musica = findViewById(R.id.checkbox_musica);
        cb_tecnologia = findViewById(R.id.checkbox_tecnologia);

        sw_recibirInformacion = findViewById(R.id.switch_recibir_informacion);

        btn_guardar = findViewById(R.id.button_guardar);
    }

    private void obtenerTodasLasSelecciones() {
        int selectedId = rg_nivelEstudios.getCheckedRadioButtonId();
        nivelEstudiosSeleccionado = "";
        if (selectedId == R.id.radio_primario_incompleto) {
            nivelEstudiosSeleccionado = "Primario incompleto";
        } else if (selectedId == R.id.radio_primario_completo) {
            nivelEstudiosSeleccionado = "Primario completo";
        } else if (selectedId == R.id.radio_secundario_incompleto) {
            nivelEstudiosSeleccionado = "Secundario incompleto";
        } else if (selectedId == R.id.radio_secundario_completo) {
            nivelEstudiosSeleccionado = "Secundario completo";
        } else if (selectedId == R.id.radio_otros) {
            nivelEstudiosSeleccionado = "Otros";
        }

        interesesSeleccionados = new StringBuilder();
        if (cb_deporte.isChecked()) {
            interesesSeleccionados.append("Deporte, ");
        }
        if (cb_arte.isChecked()) {
            interesesSeleccionados.append("Arte, ");
        }
        if (cb_musica.isChecked()) {
            interesesSeleccionados.append("Musica, ");
        }
        if (cb_tecnologia.isChecked()) {
            interesesSeleccionados.append("Tecnologia");
        }

        if (interesesSeleccionados.length() > 0 && interesesSeleccionados.charAt(interesesSeleccionados.length() - 1) == ' ') {
            interesesSeleccionados.delete(interesesSeleccionados.length() - 2, interesesSeleccionados.length());
        }

        recibirInfo = sw_recibirInformacion.isChecked();

        //Para mostrar por consola si se estaba mandando la data
       /* guardarDatos(
                nombre,
                apellido,
                telefono,
                sp_telefono,
                direccion,
                email,
                sp_email,
                nacimiento,
                nivelEstudiosSeleccionado,
                interesesSeleccionados.toString(),
                recibirInfo
        );*/
    }

   /*private void guardarDatos(
            String nombre,
            String apellido,
            String telefono,
            String spTelefono,
            String direccion,
            String email,
            String spEmail,
            String nacimiento,
            String nivelEstudiosSeleccionado,
            String interesesSeleccionados,
            boolean recibirInfo
    ) {
        Log.d("DatosGuardados", "Nombre: " + nombre);
        Log.d("DatosGuardados", "Apellido: " + apellido);
        Log.d("DatosGuardados", "Telefono: " + telefono);
        Log.d("DatosGuardados", "SP Telefono: " + spTelefono);
        Log.d("DatosGuardados", "Direccion: " + direccion);
        Log.d("DatosGuardados", "Email: " + email);
        Log.d("DatosGuardados", "SP Email: " + spEmail);
        Log.d("DatosGuardados", "Fecha de Nacimiento: " + nacimiento);
        Log.d("DatosGuardados", "Nivel de Estudios: " + nivelEstudiosSeleccionado);
        Log.d("DatosGuardados", "Intereses: " + interesesSeleccionados);
        Log.d("DatosGuardados", "Recibir Informacion: " + recibirInfo);


    }*/

    public void btn_Guardar(View view) {
        obtenerTodasLasSelecciones();

        // Crear un objeto JSON para el contacto
        JSONObject contacto = new JSONObject();
        try {
            contacto.put("nombre", nombre);
            contacto.put("apellido", apellido);
            contacto.put("telefono", telefono);
            contacto.put("spTelefono", sp_telefono);
            contacto.put("direccion", direccion);
            contacto.put("email", email);
            contacto.put("spEmail", sp_email);
            contacto.put("nacimiento", nacimiento);
            contacto.put("nivelEstudios", nivelEstudiosSeleccionado);
            contacto.put("intereses", interesesSeleccionados.toString());
            contacto.put("recibirInfo", recibirInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Guardar en SharedPreferences
        SharedPreferences preferencias = getSharedPreferences("contactos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        // Obtener los contactos existentes como array
        String contactosExistentes = preferencias.getString("lista_contactos", "[]");
        try {
            JSONArray contactosArray = new JSONArray(contactosExistentes);
            contactosArray.put(contacto);  // Agregar el nuevo contacto

            // Guardar la lista actualizada de contactos
            editor.putString("lista_contactos", contactosArray.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Contacto guardado correctamente", Toast.LENGTH_LONG).show();

        // Volver a la pantalla principal
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
