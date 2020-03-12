package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainTermometro extends AppCompatActivity {
    ArrayList<Termos>
            TL;
    RecyclerView
            rc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_termometro );
        rc=(RecyclerView)findViewById( R.id.RCV2 );
        rc.setHasFixedSize( true );
        rc.setLayoutManager( new LinearLayoutManager( this ) );
        TL= new ArrayList<>(  );

        Mostrar("https://asesoresconsultoreslabs.com/asesores/App_Android/GetTermometrosList.php");


    }

    private void Mostrar(String URL) {
        StringRequest
                stringRequest= new StringRequest( Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array= new JSONArray( response );
                            for (int i=0;i<array.length();i++){
                                JSONObject terms=array.getJSONObject( i );
                                TL.add(new Termos(
                                        terms.getString( "nombre" ) ,
                                        terms.getString("area" )
                                ) );
                            }
                           AdapterT adapterData=new AdapterT( getApplicationContext(),TL);
                            rc.setAdapter( adapterData);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(), "no hay conexion", Toast.LENGTH_SHORT ).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add( stringRequest );
    }
}
