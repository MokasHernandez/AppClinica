package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InsertaTT extends AppCompatActivity {
    public String p2,p1;
    TextView T1,T2,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8;
    EditText e1,e2,e3,e4,e5,e6,e7,eh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_insertatt );
        T1=(TextView) findViewById( R.id.t1 );
        T2=(TextView) findViewById( R.id.t2 );
        e1=(EditText) findViewById( R.id.e1);
        e2=(EditText) findViewById( R.id.e2);
        e3=(EditText) findViewById( R.id.e3);
        e4=(EditText) findViewById( R.id.e4);
        e5=(EditText) findViewById( R.id.e5);
        e6=(EditText) findViewById( R.id.e6);
        e7=(EditText) findViewById( R.id.e7);
        eh=(EditText) findViewById( R.id.eh);
        txt1=(TextView) findViewById( R.id.txt1 );
        txt2=(TextView) findViewById( R.id.txth2 );
        txt3=(TextView) findViewById( R.id.txt3 );
        txt4=(TextView) findViewById( R.id.txth4 );
        txt5=(TextView) findViewById( R.id.txt5);
        txt6=(TextView) findViewById( R.id.txth6 );
        txt7=(TextView) findViewById( R.id.txtac7 );

        Button b1=findViewById( R.id.b1 );
        Bundle bundle =  getIntent().getExtras();
        p1 = bundle.getString( "nam" );
        p2 = bundle.getString( "tip" );

        T1.setText( p1 );
        T2.setText( p2 );
        eh.setBackgroundColor( Color.TRANSPARENT);

        txt1.setVisibility( View.INVISIBLE );
        txt2.setVisibility( View.INVISIBLE );
        txt3.setVisibility( View.INVISIBLE );
        txt4.setVisibility( View.INVISIBLE );
        txt5.setVisibility( View.INVISIBLE );
        txt6.setVisibility( View.INVISIBLE );
        txt7.setVisibility( View.INVISIBLE );
        txt1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );
        txt2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );
        txt3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );
        txt4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );
        txt5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );
        txt6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );
        txt7.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Se Encuentra Fuera de Tiempo",Toast.LENGTH_SHORT ).show();
            }
        } );




        Calendar calendario = Calendar.getInstance();
        final int hora, minutos,segundos;
        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        if(hora>=00&&hora<=11){
            eh.setText( "Buenos Dias" );
            if(hora>=8&&hora<9){
                e1.setVisibility( View.VISIBLE);
                e2.setVisibility( View.VISIBLE );
                e7.setVisibility( View.VISIBLE );

                e3.setVisibility( View.INVISIBLE );
                e4.setVisibility( View.INVISIBLE );
                e5.setVisibility( View.INVISIBLE );
                e6.setVisibility( View.INVISIBLE );

                txt3.setVisibility( View.VISIBLE );
                txt4.setVisibility( View.VISIBLE );
                txt5.setVisibility( View.VISIBLE );
                txt6.setVisibility( View.VISIBLE );
               }else{
                txt3.setVisibility( View.VISIBLE );
                txt4.setVisibility( View.VISIBLE );
                txt5.setVisibility( View.VISIBLE );
                txt6.setVisibility( View.VISIBLE );
                txt1.setVisibility( View.VISIBLE );
                txt2.setVisibility( View.VISIBLE );
                txt7.setVisibility( View.VISIBLE );

                e1.setVisibility( View.INVISIBLE);
                e2.setVisibility( View.INVISIBLE );
                e3.setVisibility( View.INVISIBLE );
                e4.setVisibility( View.INVISIBLE );
                e5.setVisibility( View.INVISIBLE );
                e6.setVisibility( View.INVISIBLE );
                e7.setVisibility( View.INVISIBLE );

            }

        }else
        if(hora>=12&&hora<=18){
            eh.setText( "Buenas Tardes " );
            if(hora>=15&&hora<16){
                e3.setVisibility( View.VISIBLE );
                e4.setVisibility( View.VISIBLE );
                e7.setVisibility( View.VISIBLE );

                e1.setVisibility( View.INVISIBLE);
                e2.setVisibility( View.INVISIBLE );
                e5.setVisibility( View.INVISIBLE );
                e6.setVisibility( View.INVISIBLE );

                txt1.setVisibility( View.VISIBLE );
                txt2.setVisibility( View.VISIBLE );
                txt6.setVisibility( View.VISIBLE );
                txt5.setVisibility( View.VISIBLE );
                }else{
                txt1.setVisibility( View.VISIBLE );
                txt2.setVisibility( View.VISIBLE );
                txt6.setVisibility( View.VISIBLE );
                txt5.setVisibility( View.VISIBLE );
                txt3.setVisibility( View.VISIBLE );
                txt4.setVisibility( View.VISIBLE );
                txt7.setVisibility( View.VISIBLE );

                e1.setVisibility( View.INVISIBLE);
                e2.setVisibility( View.INVISIBLE );
                e3.setVisibility( View.INVISIBLE );
                e4.setVisibility( View.INVISIBLE );
                e5.setVisibility( View.INVISIBLE );
                e6.setVisibility( View.INVISIBLE );
                e7.setVisibility( View.INVISIBLE );

            }
        }else
        if(hora>=19&&hora<=23){
            eh.setText( "Buenas Noches" );
            if(hora>=18&&hora<19){
                e1.setVisibility( View.INVISIBLE);
                e2.setVisibility( View.INVISIBLE );
                e3.setVisibility( View.INVISIBLE );
                e4.setVisibility( View.INVISIBLE );

                e5.setVisibility( View.VISIBLE );
                e6.setVisibility( View.VISIBLE );
                e7.setVisibility( View.VISIBLE );

                txt3.setVisibility( View.VISIBLE );
                txt4.setVisibility( View.VISIBLE );
                txt1.setVisibility( View.VISIBLE );
                txt2.setVisibility( View.VISIBLE );
                }else{
                txt1.setVisibility( View.VISIBLE );
                txt2.setVisibility( View.VISIBLE );
                txt6.setVisibility( View.VISIBLE );
                txt5.setVisibility( View.VISIBLE );
                txt3.setVisibility( View.VISIBLE );
                txt4.setVisibility( View.VISIBLE );
                txt7.setVisibility( View.VISIBLE );

                e1.setVisibility( View.INVISIBLE);
                e2.setVisibility( View.INVISIBLE );
                e3.setVisibility( View.INVISIBLE );
                e4.setVisibility( View.INVISIBLE );
                e5.setVisibility( View.INVISIBLE );
                e6.setVisibility( View.INVISIBLE );
                e7.setVisibility( View.INVISIBLE );
            }
        }


        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hora>=8&&minutos==00&&segundos==00&&hora<=8&&minutos==55&&segundos==50)
                {
                    A( "https://asesoresconsultoreslabs.com/asesores/App_Android/AddTermo.php" );
                }else
                if(hora>=15&&minutos==00&&segundos==00&&hora<=15&&minutos==55&&segundos==00)
                {
                    B("https://asesoresconsultoreslabs.com/asesores/App_Android/AddTermo.php");
                }else
                if(hora>=18&&minutos==00&&segundos==00&&hora<=18&&minutos<=55&&segundos==00)
                {
                    C("https://asesoresconsultoreslabs.com/asesores/App_Android/AddTermo.php");
                }

            }
        } );

    }

    public void A(String Aa){
        StringRequest stringRequest = new  StringRequest( Request.Method.POST, Aa,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText( getApplicationContext(), "exito", Toast.LENGTH_SHORT ).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(),"error",Toast.LENGTH_SHORT ).show();
            }
        } ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> P=new HashMap<String, String>( );
                P.put( "nombre",T1.getText().toString());
                P.put( "area",T2.getText().toString() );
                P.put( "temperatura_1",e1.getText().toString());
                P.put( "humedad_1", e2.getText().toString());
                P.put("accion_generada",e7.getText().toString());
                return P;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add( stringRequest );
    }

    public void B(String Bb){
        StringRequest stringRequest = new  StringRequest( Request.Method.POST, Bb,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText( getApplicationContext(), "exito", Toast.LENGTH_SHORT ).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(),"error",Toast.LENGTH_SHORT ).show();
            }
        } ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> P=new HashMap<String, String>( );
                P.put( "nombre",T1.getText().toString());
                P.put( "area",T2.getText().toString() );
                P.put( "temperatura_2",e3.getText().toString( ));
                P.put( "humedad_2",e4.getText().toString());
                P.put("accion_generada",e7.getText().toString());
                return P;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add( stringRequest );
    }

    public void C(String Cc){
        StringRequest stringRequest = new  StringRequest( Request.Method.POST, Cc,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText( getApplicationContext(), "exito", Toast.LENGTH_SHORT ).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(),"error",Toast.LENGTH_SHORT ).show();
            }
        } ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> P=new HashMap<String, String>( );
                P.put( "nombre",T1.getText().toString());
                P.put( "area",T2.getText().toString() );
                P.put( "temperatura_3",e5.getText().toString( ));
                P.put( "humedad_3", e6.getText().toString( ));
                P.put("accion_generada",e7.getText().toString());
                return P;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add( stringRequest );
    }





}
