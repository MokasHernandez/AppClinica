package com.example.appclnica;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MMFragment extends Fragment {


    private TextView Prueba;
    private Button BTN1;
    private Button BTN2;
    private Button BTN3;
    private Button BTN4;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mm, container, false);

        BTN1 =root.findViewById( R.id.BTN1 );
        BTN2=root.findViewById( R.id.BTN2 );
        BTN3=root.findViewById( R.id.BTN3 );
        BTN4=root.findViewById( R.id.BTN4 );
        Prueba=root.findViewById( R.id.text_tools );




        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada();
            }
        });
        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NK();
            }
        });
        BTN3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
        BTN4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salida=new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
               getActivity().finish();
            }
        } );


    return  root;
    }


    public void Llamada(){
        Intent QR = new Intent(getActivity().getApplicationContext(), MainQR.class);
        startActivity(QR);
    }
    public void NK(){
        Intent RP= new Intent( getActivity().getApplicationContext(),MainQR2.class );
        startActivity( RP );
    }






}
