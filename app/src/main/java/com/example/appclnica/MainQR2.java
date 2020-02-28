package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainQR2 extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    String V="";
    RequestQueue requestQueue;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mqr2 );

        setContentView( R.layout.activity_mqr );
        scannerView = (ZXingScannerView) findViewById( R.id.zxscan );


        Dexter.withActivity( this )
                .withPermission( Manifest.permission.CAMERA )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler( MainQR2.this );
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                } )
                .check();
    }
    @Override
    public void onDestroy(){
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult.toString().length() <= 4 ) {
            Intent I = new Intent(MainQR2.this,Reportes.class);
            I.putExtra("ID", rawResult.toString());
            /*TraerEquipo( "https://asesoresconsultoreslabs.com/asesores/App_Android/ConsultaA.php?id="+ rawResult +"" );*/

            I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, I, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(I);
        }
    }


    /*private void TraerEquipo(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) { JSONObject InfoEquipo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        InfoEquipo = response.getJSONObject( i );
                        if (InfoEquipo != null) {
                            String A = InfoEquipo.getString( "id_equipo" );
                            String B = InfoEquipo.getString( "reporte_servicio" );
                            Toast.makeText( getApplicationContext(),B,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT ).show();
            }

        } );
        requestQueue = Volley.newRequestQueue( getApplicationContext() );
        requestQueue.add( jsonArrayRequest );
    }*/


}
