package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Mantenimientos extends AppCompatActivity {

    private CheckBox CheckCo,CheckPre;
    private Button BTNADD;
    private EditText EDTIPOM,EDTEMPRESA,EDTFECHA,EDTHORA,EDTEQUIPO,EDTCOSTOM,EDTDFALLA,EDTREFACC;
    private int InYear,Inmonth,InDay,AyearIn,AmonthIn,AdayIn;
    DatePickerDialog.OnDateSetListener setListener;
    String B;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mantenimientos );
        Intent I = new Intent( Mantenimientos.this, StatusFragment.class );

        CheckCo =(CheckBox)findViewById( R.id.CheckCo );
        CheckPre=(CheckBox)findViewById( R.id.CheckPre );

        BTNADD=(Button)findViewById( R.id.BTNADD );

        EDTIPOM=(EditText)findViewById( R.id.EDTIPOM );
        EDTEMPRESA=(EditText)findViewById( R.id.EDTEMPRESA );
        EDTFECHA=(EditText)findViewById( R.id.EDTFECHA );
        EDTHORA=(EditText)findViewById( R.id.EDTHORA );
        EDTEQUIPO=(EditText)findViewById( R.id.EDTEQUIPO );
        EDTCOSTOM=(EditText)findViewById( R.id.EDTCOSTOM );
        EDTEQUIPO=(EditText)findViewById( R.id.EDTEQUIPO );
        EDTDFALLA=(EditText)findViewById( R.id.EDTDFALLA);
        EDTREFACC=(EditText)findViewById( R.id.EDTREFACC);

        Calendar calendar=Calendar.getInstance();
        final int Year=calendar.get( Calendar.YEAR );
        final int Mont=calendar.get(Calendar.MONTH);
        final int Day=calendar.get(Calendar.DAY_OF_MONTH);

        EDTFECHA.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Mantenimientos.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,Year,Mont,Day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show();
            }
        } );

        EDTFECHA.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Mantenimientos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date =year+"/"+month+"/"+day;
                        EDTFECHA.setText( date );
                    }
                },Year,Mont,Day);
                datePickerDialog.show();
            }
        } );

        BTNADD.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ADDMANT( "https://asesoresconsultoreslabs.com/asesores/App_Android/AddMant.php");
            }
        } );

    }

    public void ADDMANT(String URL){
        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText( getApplicationContext(), "Inserccion Exitosa", Toast.LENGTH_SHORT ).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getApplicationContext(), "Insercion Erronea", Toast.LENGTH_SHORT ).show();
            }
        } ){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> parametros=new HashMap<String, String>( );
                String.valueOf(EDTFECHA);
                parametros.put( "tipo_mant",EDTIPOM.getText().toString());
                parametros.put( "empresa",EDTEMPRESA.getText().toString());
                parametros.put( "fecha", EDTFECHA.getText().toString());
                parametros.put( "horas_paro",EDTHORA.getText().toString() );
                parametros.put( "equipo",EDTEQUIPO.getText().toString() );
                parametros.put( "costo",EDTCOSTOM.getText().toString() );
                parametros.put( "desc_falla",EDTDFALLA.getText().toString() );
                parametros.put("costoRefacciones",EDTREFACC.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }
}
