package com.example.tp2_grupo2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Spinner sp_telefono;
    private Spinner sp_email;
    private EditText txt_nombre;
    private EditText txt_apellido;
    private EditText txt_telefono;
    private EditText txt_direccion;
    private EditText txt_email;
    private EditText txt_nacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        //Objetos del activity
        sp_telefono=(Spinner)findViewById(R.id.sp_telefono);
        sp_email=(Spinner)findViewById(R.id.sp_email);
        txt_nombre = (EditText)findViewById(R.id.txt_nombre);
        txt_apellido = (EditText)findViewById(R.id.txt_apellido);
        txt_telefono = (EditText)findViewById(R.id.txt_telefono);
        txt_direccion = (EditText)findViewById(R.id.txt_direccion);
        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_nacimiento = (EditText)findViewById(R.id.txt_nacimiento);

        txt_nombre.addTextChangedListener(new SoloLetasTextWatcher(this, txt_nombre));
        txt_apellido.addTextChangedListener(new SoloLetasTextWatcher(this, txt_apellido));

        String[] spinnerOpciones={"Casa","Trabajo","Movil"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerOpciones);
        sp_telefono.setAdapter(adapter);
        sp_email.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_item_2) {
            Intent intent = new Intent(this, listaContactos.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void datos_extras(View view){
        if (camposVacios()) {
            if (validarMail()) {
                if(validarNacimiento()) {
                    Intent intent = new Intent(this, DatosExtras.class);
                    intent.putExtra("nombre", txt_nombre.getText().toString());
                    intent.putExtra("apellido", txt_apellido.getText().toString());
                    intent.putExtra("telefono", txt_telefono.getText().toString());
                    intent.putExtra("sp_telefono", sp_telefono.getSelectedItem().toString());
                    intent.putExtra("direccion", txt_direccion.getText().toString());
                    intent.putExtra("email", txt_email.getText().toString());
                    intent.putExtra("sp_email", sp_email.getSelectedItem().toString());
                    intent.putExtra("nacimiento", txt_nacimiento.getText().toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Por favor ingrese una Fecha de Nacimiento valida", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Por favor utilice un Email valido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Boolean camposVacios() {
        boolean estado = true;
        if (txt_nombre.getText().toString().isEmpty()) {
            estado = false;
            Toast.makeText(this, "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show();
        }
        if (txt_apellido.getText().toString().isEmpty()) {
            estado = false;
            Toast.makeText(this, "Por favor ingrese su apellido", Toast.LENGTH_SHORT).show();
        }
        if (txt_telefono.getText().toString().isEmpty()) {
            estado = false;
            Toast.makeText(this, "Por favor ingrese su telefono", Toast.LENGTH_SHORT).show();
        }
        if (txt_email.getText().toString().isEmpty()) {
            estado = false;
            Toast.makeText(this, "Por favor ingrese su email", Toast.LENGTH_SHORT).show();
        }
        if (txt_direccion.getText().toString().isEmpty()) {
            estado = false;
            Toast.makeText(this, "Por favor ingrese su dirección", Toast.LENGTH_SHORT).show();
        }
        if (txt_nacimiento.getText().toString().isEmpty()) {
            estado = false;
            Toast.makeText(this, "Por favor ingrese su fecha de nacimiento", Toast.LENGTH_SHORT).show();
        }

        return estado;
    }

    private boolean validarNacimiento() {
        String nacimiento = txt_nacimiento.getText().toString();

        if (nacimiento.length() != 10) {
            return false;
        }
        if (nacimiento.isEmpty()) {
            return false;
        }

        SimpleDateFormat formatoEsperado = new SimpleDateFormat("dd/MM/yyyy");
        formatoEsperado.setLenient(false);

        try {
            Date fecha = formatoEsperado.parse(nacimiento);
            Calendar cal = Calendar.getInstance();

            //fecha actual para validar
            Date fechaActual = cal.getTime();

            // que sea anterior a la fecha actual
            if (!fecha.before(fechaActual)) {
                return false;
            }

            // Que la fecha no sea demasiada antigua #MirthaLegran't
            cal.add(Calendar.YEAR, -120);
            Date fechaLimiteAntigua = cal.getTime();

            if (fecha.before(fechaLimiteAntigua)) {
                return false;
            }

            // Y aca que no sea demasiado reciente
            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            Date fechaLimiteReciente = cal.getTime();

            if (fecha.after(fechaLimiteReciente)) {
                return false;
            }

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean validarMail() {
        String email = txt_email.getText().toString();
        if (email.isEmpty()) {
            return false;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }


}