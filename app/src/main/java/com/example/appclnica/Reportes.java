package com.example.appclnica;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.IOUtils;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Reportes extends AppCompatActivity {

    EditText EDTRINGE,EDTRHORA,EDTRDF,EDTRCF,EDTRFPM,EDTRCR,EDTNREPORT;
    Spinner ComboBox;
    DatePickerDialog.OnDateSetListener setListener;
    Button BTNFILE,BTNREPORT;
    String opt="ok";
    public static final String UPLOAD_URL = "https://asesoresconsultoreslabs.com/asesores/App_Android/UPLOAD.php";
    public static final int PICKFILE_RESULT_CODE = 1;
    public static String IDQReport="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reportes );
        EDTRINGE=(EditText)findViewById( R.id.EDTRINGE );
        EDTRHORA=(EditText)findViewById( R.id.EDTRHORA);
        EDTRDF=(EditText)findViewById( R.id.EDTRDF );
        EDTRCF=(EditText)findViewById( R.id.EDTRCF);
        EDTRFPM=(EditText)findViewById( R.id.EDTRFPM);
        EDTRCR=(EditText)findViewById( R.id.EDTRCR );
        ComboBox=(Spinner)findViewById( R.id.ComboBox );
        EDTRFPM=(EditText)findViewById( R.id.EDTRFPM);
        BTNFILE=(Button)findViewById( R.id.BTNFILE );
        BTNREPORT=(Button)findViewById( R.id.BTNREPORT );
        EDTNREPORT=(EditText) findViewById( R.id.EDTNREPORT );
        Bundle bundle =  getIntent().getExtras();
        IDQReport = bundle.getString( "ID" );
        Toast.makeText( getApplicationContext(),IDQReport,Toast.LENGTH_SHORT ).show();
        new OnBackPressedDispatcher();

        /****************************BOTÓN UPLOAD FILE PDF ON CREATE*****************************/
        BTNFILE.setOnClickListener( new View.OnClickListener() {@Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        } );
        /****************************spinner*****************************/
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(
                this,R.array.Combo1,android.R.layout.simple_spinner_item);
        ComboBox.setAdapter( adapter );
        ComboBox.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(ComboBox.getSelectedItemPosition()==3){
                   opt="ok";
                }else if (ComboBox.getSelectedItemPosition()!=3){
                    opt=parent.getItemAtPosition( position ).toString();
                }
            }@Override
            public void onNothingSelected(AdapterView<?> parent) { }} );

     /****************************EDITEXT CALENDARIO*****************************/
        Calendar calendar=Calendar.getInstance();
        final int Year=calendar.get( Calendar.YEAR );
        final int Mont=calendar.get(Calendar.MONTH);
        final int Day=calendar.get(Calendar.DAY_OF_MONTH);
        EDTRFPM.setOnClickListener( new View.OnClickListener() {@Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Reportes.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,Year,Mont,Day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show(); }} );

        EDTRFPM.setOnClickListener( new View.OnClickListener() {@Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                       Reportes .this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date =year+"/"+month+"/"+day;
                        EDTRFPM.setText( date );
                    }
                },Year,Mont,Day);
                datePickerDialog.show(); }} );

        BTNREPORT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( EDTRINGE.getText().toString().isEmpty()&& EDTRDF.getText().toString().isEmpty()&&EDTRHORA.getText().toString().isEmpty()&&
                         EDTRCF.getText().toString().isEmpty()&&EDTRFPM.getText().toString().isEmpty()&&EDTRCR.getText().toString().isEmpty())
                {
                    EDTRINGE.setError( "Favor de Llenar Campo" );
                    EDTRFPM.setError( "Favor de Llenar Campo" );
                    EDTRDF.setError( "Favor de Llenar Campo" );
                    EDTNREPORT.setError( "Elíja un Archivo" );
                    CMB( ComboBox );
                    EDTRHORA.setError( "Favor de Llenar Campo" );
                    EDTRCF.setError( "Favor de Llenar Campo" );
                    EDTRCR.setError( "Favor de Llenar Campo" );
                }
                if( !EDTRINGE.getText().toString().isEmpty()&&!EDTRHORA.getText().toString().isEmpty()&& !EDTRDF.getText().toString().isEmpty() && !EDTRCF.getText().toString().isEmpty()&&!EDTRFPM.getText().toString().isEmpty()&&!EDTRCR.getText().toString().isEmpty() &&!EDTRFPM.getText().toString().isEmpty()&&!EDTNREPORT.getText().toString().isEmpty()) {
                    ADDREPORT( "https://asesoresconsultoreslabs.com/asesores/App_Android/AddReport.php" );


                }
                BTNFILE.requestFocus();
                EDTRINGE.requestFocus();
                ComboBox.requestFocus();
                ComboBox.requestFocus();
                ComboBox.requestFocus();
                EDTRHORA.setText( " " );
                EDTRDF.setText( " " );
                EDTRCF.setText( " " );
                EDTRCR.setText( " " );
                EDTRFPM.setText( " " );
                EDTRFPM.setText( " " );
                EDTNREPORT.setText( " " );
                EDTRINGE.setText( " " );
            }

        } );
    }
    /****************************BOTÓN UPLOAD FILE PDF OVERRIDE*****************************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri uri= data.getData();
                    File file= new File(uri.getPath());
                    EDTNREPORT.setText(file.getName()  );

                }
                break;
        }
    }
/*************************************AGREGAR REPORTE*************************************/
   public void ADDREPORT(String URL){
        StringRequest
                stringRequest = new StringRequest( Request.Method.POST, URL, new Response.Listener<String>() {
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>( );
                String.valueOf(EDTRFPM);
                parametros.put( "id_equipo",IDQReport );
                parametros.put( "rep_serv",EDTNREPORT.getText().toString() );
                parametros.put("nombre_atendio",EDTRINGE.getText().toString());
                parametros.put( "horas_paro",EDTRHORA.getText().toString());
                parametros.put( "desc_falla", EDTRDF.getText().toString());
                parametros.put( "costoRefacciones",EDTRCR.getText().toString() );
                parametros.put( "fecha_puesta_marcha",EDTRFPM.getText().toString() );
                parametros.put( "reporte_servicio",opt );
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add( stringRequest );
    }
    /******************VALIDAR SPINNER***********************/
    public void CMB(Spinner comboBox){
        View selectedView = comboBox.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("Selecciona una opción");
        }

    }
    /**********UPLOADED PDF TO SERVER *****************/







}
