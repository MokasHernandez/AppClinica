package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button btnRegistro = findViewById(R.id.buttonRe);
        final TextView txtUsuario = findViewById(R.id.editTextNom);

        Bundle bundle = getIntent().getExtras();
        final String Message = bundle.getString("Token");

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtUsuario.getText().toString().isEmpty()) {
                    enviarTokenToServer(Message, txtUsuario.getText().toString().trim());

                    Intent Principal = new Intent(getApplicationContext(), MainActivity.class);
                    Principal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(Principal);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"No ha ingresado ningún nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent Reinicio = new Intent(getApplicationContext(), ActivityRegistro.class);
        startActivity(Reinicio);
    }

    private void enviarTokenToServer(final String token, final String Usuario) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://asesoresconsultoreslabs.com/asesores/App_Android/CRUD.php?idt=3",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se registro exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Token", token);
                parametros.put("ID_Usuario", Usuario);

                return parametros;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
