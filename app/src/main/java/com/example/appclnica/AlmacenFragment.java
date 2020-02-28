package com.example.appclnica;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;

import java.io.File;
import java.io.IOException;

public class AlmacenFragment extends Fragment {

    private TextView Prueba;
    private Button btnEntrada;
    private Button btnSalida;
    private Button btnInicio;
    private Button btnFin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_almacen, container, false);

        btnEntrada = root.findViewById(R.id.BTN1 );
        btnSalida = root.findViewById(R.id.BTN2 );
        btnInicio = root.findViewById(R.id.BTN3 );
        btnFin = root.findViewById(R.id.BTN4 );
        Prueba = root.findViewById(R.id.text_tools);
        Prueba.setText("Módulo de Almacén");

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Entrada = new Intent(getActivity().getApplicationContext(), ActivityAlmacenE.class);
                startActivity(Entrada);
            }
        });

        btnSalida.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Salida = new Intent(getActivity().getApplicationContext(), ActivityAlmacenS.class);
                startActivity(Salida);
            }
        });

        btnInicio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada();
            }
        });

        return root;
    }


    public void Llamada(){
        Intent QR = new Intent(getActivity().getApplicationContext(), MainQR.class);
        startActivity(QR);
    }

}