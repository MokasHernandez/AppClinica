package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUsuario, txtPassword;
    private String Mensaje = "";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.buttonLogin);
        txtUsuario = findViewById(R.id.editTextUsuario);
        txtPassword = findViewById(R.id.editTextPassword);
        txtUsuario.requestFocus();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login("https://asesoresconsultoreslabs.com/App_Android/select.php?id=" + txtUsuario.getText() + "&pass=" + txtPassword.getText() + "");
            }
        });
    }

    private void Login(String URL){
        JsonArrayRequest Array = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        if (obj != null) {
                            Mensaje = obj.getString("nombre");
                            Toast.makeText(getApplicationContext(),"Login exitoso",Toast.LENGTH_SHORT).show();
                            Intent PasoPantalla = new Intent(MainActivity.this, NavigationActivity.class);
                            PasoPantalla.putExtra("Mensaje", Mensaje);
                            PasoPantalla.putExtra("ID", obj.getString("id"));
                            startActivity(PasoPantalla);
                            txtPassword.setText("");
                        }
                    } catch (JSONException c) {
                        Toast.makeText(getApplicationContext(), c.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Favor de verificar los campos", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(Array);
    }
}
