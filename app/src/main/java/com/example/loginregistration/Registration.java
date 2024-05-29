package com.example.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    TextInputEditText textInputEdiTextNombre, textInputEditTextApellido,
            textInputEditTextCorreo, textInputEditTextPassword;
    Button buttonSubmit;
    String nombre, apellido, correo, password;
    TextView TextviewError;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        textInputEdiTextNombre = findViewById(R.id.nombre);
        textInputEditTextApellido = findViewById(R.id.apellido);
        textInputEditTextCorreo = findViewById(R.id.correo);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSubmit = findViewById(R.id.submit);
        TextviewError = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextviewError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                nombre = String.valueOf(textInputEdiTextNombre.getText());
                apellido = String.valueOf(textInputEditTextApellido.getText());
                correo = String.valueOf(textInputEditTextCorreo.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.92/login_registro/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if (response.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Registrations successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    TextviewError.setText(response);
                                    TextviewError.setVisibility(View.VISIBLE);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("nombre", nombre);
                        paramV.put("apellido", apellido);
                        paramV.put("correo", correo);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}