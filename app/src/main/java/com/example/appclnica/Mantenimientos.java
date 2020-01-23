package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class Mantenimientos extends AppCompatActivity {

    private CheckBox cb1,cb2;
    Button button;
    ArrayList nmb;
    EditText EDTEMPRESA,EDTFECHA,EDTHPARO,EDTEQUIPO,EDTCOSTO,EDTDESF,EDTREFS,EDTTIPO;
    private int InYear,Inmonth,InDay,AyearIn,AmonthIn,AdayIn;
    DatePickerDialog.OnDateSetListener setListener;
    String B;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mantenimientos );
        Intent I = new Intent( Mantenimientos.this, StatusFragment.class );

    }
}
