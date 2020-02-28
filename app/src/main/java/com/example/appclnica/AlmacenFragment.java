package com.example.appclnica;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AlmacenFragment extends Fragment {

    private TextView Prueba;
    private Button btnEntrada;
    private Button btnSalida;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_almacen, container, false);

        btnEntrada = root.findViewById(R.id.btnEnt);
        btnSalida = root.findViewById(R.id.btnSal);
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

        return root;
    }


    public void Llamada(){
        Intent QR = new Intent(getActivity().getApplicationContext(), MainQR.class);
        startActivity(QR);
    }
}