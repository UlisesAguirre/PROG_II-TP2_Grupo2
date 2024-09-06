package com.example.tp2_grupo2;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

public class SoloLetasTextWatcher implements TextWatcher {

    private final Context context;
    private final EditText editText;

    public SoloLetasTextWatcher(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String filtered = s.toString().replaceAll("[^a-zA-ZáéíóúüÁÉÍÓÚÜñÑ ]", "");

        if (!s.toString().equals(filtered)) {
            editText.setText(filtered);
            editText.setSelection(filtered.length()); // Mover el cursor al final
        }

        if (s.toString().matches(".*\\d.*")) {
            Toast.makeText(context, "Solo se permiten letras", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {}
}
