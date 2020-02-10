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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Mantenimientos extends AppCompatActivity {

    private CheckBox CheckCo,CheckPre;
    private Button BTNADD;
    private EditText EDTIPOM,EDTEMPRESA,EDTFECHA,EDTHORA,EDTEQUIPO,EDTCOSTOM,EDTDFALLA,EDTREFACC;
    DatePickerDialog.OnDateSetListener setListener;
    QRInfoFragment QRInfoFragment =new QRInfoFragment();
    String B;
    String IDQR2= QRInfoFragment.IDQR;
    String Az="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mantenimientos );
        Intent I = new Intent( Mantenimientos.this, MantenimientoFragment.class );

        CheckCo =(CheckBox)findViewById( R.id.CheckCo );
        CheckPre=(CheckBox)findViewById( R.id.CheckPre );
        BTNADD=(Button)findViewById( R.id.BTNADD );
        EDTEMPRESA=(EditText)findViewById( R.id.EDTEMPRESA );
        EDTFECHA=(EditText)findViewById( R.id.EDTFECHA );
        EDTDFALLA =(EditText)findViewById(R.id.EDTDFALLA);
        EDTEMPRESA.requestFocus();
        CheckCo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckCo.isChecked()){
                    Az="correctivo";
                    CheckPre.setVisibility( View.INVISIBLE );
                }else if(CheckCo.isChecked()==false)
                {CheckPre.setVisibility( View.VISIBLE );}
            }
        } );
        CheckPre.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPre.isChecked()){
                    Az="preventivo";
                    CheckCo.setVisibility( View.INVISIBLE );
                }else if(CheckPre.isChecked()==false)
                {CheckCo.setVisibility( View.VISIBLE );}
            }
        } );

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
                if( EDTEMPRESA.getText().toString().isEmpty()&&EDTDFALLA.getText().toString().isEmpty()&&EDTFECHA.getText().toString().isEmpty())
                {
                    EDTEMPRESA.setError( "Favor de Llenar Campo" );
                    EDTFECHA.setError( "Favor de Llenar Campo" );
                    EDTDFALLA.setError( "Favor de Llenar Campo" );
                }else if(!EDTEMPRESA.getText().toString().isEmpty()&&!EDTDFALLA.getText().toString().isEmpty()&&!EDTFECHA.getText().toString().isEmpty()) {
                    ADDMANT( "https://asesoresconsultoreslabs.com/asesores/App_Android/AddMant.php" );
                    EDTEMPRESA.requestFocus();
                    EDTEMPRESA.setText( " " );
                    EDTDFALLA.setText( " " );
                    EDTFECHA.setText( " " );
                    CheckPre.setChecked( false );
                    CheckCo.setChecked( false );
                }
            }
        } );
    }

    public void ADDMANT(String URL){
        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText( getApplicationContext(), "Inserción Exitosa", Toast.LENGTH_SHORT ).show();
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
                parametros.put("tipo_mant",Az);
                parametros.put( "empresa_mant",EDTEMPRESA.getText().toString());
                parametros.put( "fecha", EDTFECHA.getText().toString());
                parametros.put( "equipo",IDQR2 );
                parametros.put( "desc_falla",EDTDFALLA.getText().toString() );
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add( stringRequest );
    }
}
