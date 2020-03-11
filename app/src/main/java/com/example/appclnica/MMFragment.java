package com.example.appclnica;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appclnica.ui.home.HomeFragment;

public class MMFragment extends Fragment {

    private TextView Prueba;
    private Button BTN1;
    private Button BTN2;
    private Button BTN3;
    private Button BTN4;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_mm, container, false );

        BTN1 = root.findViewById( R.id.BTN1 );
        BTN2 = root.findViewById( R.id.BTN2 );
        BTN3 = root.findViewById( R.id.BTN3 );
        BTN4 = root.findViewById( R.id.BTN4 );
        Prueba = root.findViewById( R.id.text_tools );

        BTN1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada();
            }
        } );
        BTN2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada2();
            }
        } );
        BTN3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada3();
            }
        } );
        BTN4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada4();
            }
        } );
        return root;
    }


    public void Llamada() {
        Intent QR = new Intent( getActivity().getApplicationContext(), MainQR.class );
        startActivity( QR );
    }

    public void Llamada2() {
        Intent RP = new Intent( getActivity().getApplicationContext(), MainQR2.class );
        startActivity( RP );
    }

    public void Llamada3() {
        Intent TS = new Intent( getActivity().getApplicationContext(), MainTermometro.class );
        startActivity( TS );
    }
    public void Llamada4() {
        Intent OUT = new Intent( getActivity().getApplicationContext(), HomeFragment.class );
        startActivity( OUT );

    }
}







