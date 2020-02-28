package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclnica.ui.home.HomeFragment;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ActivityInicioFinQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    String Tipo;
    ZXingScannerView scannerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_regreso, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_fin_qr);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Tipo = bundle.getString("TipoUpdate");
        }


        Toolbar toolbar = findViewById(R.id.toolbar5);
        scannerView = findViewById(R.id.scannerIniFin);

        setSupportActionBar(toolbar);

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String uso = rawResult.toString();
        String id_seg = uso.substring(20);
        String id_rea = uso.substring(0, 11);
        UpdateSeg(Tipo, id_seg, id_rea, HomeFragment.filial);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_salir) {
            onBackPressed();
        }
        return true;
    }

    private void UpdateSeg(final String tipoupdate, final String id_seg, final String id_mat, final String unidad) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=16",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), " " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("tiup", tipoupdate);
                parametros.put("id_seg", id_seg);
                parametros.put("id_mat", id_mat);
                parametros.put("unidad", unidad);

                return parametros;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
