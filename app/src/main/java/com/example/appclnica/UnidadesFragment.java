package com.example.appclnica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UnidadesFragment extends Fragment {

    TextView Encabezado;
    Button btnVerificacion, btnInicio, btnFin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_unidades, container, false);

        Encabezado = root.findViewById(R.id.lbTrasUni);
        btnVerificacion = root.findViewById(R.id.btnVerificacion);
        btnInicio = root.findViewById(R.id.btnInicio);
        btnFin = root.findViewById(R.id.btnFin);

        Encabezado.setText("Módulo Unidades Almacén");

        btnVerificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasoVeri = new Intent(getActivity(), ActivityVerificacion.class);
                startActivity(pasoVeri);
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NuevoRea = new Intent(getActivity(), ActivityInicioFinQR.class);
                NuevoRea.putExtra("TipoUpdate", "1");
                startActivity(NuevoRea);
            }
        });

        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Verificación")
                        .setMessage("¿Está seguro de realizar ésta acción?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent NuevoRea = new Intent(getActivity(), ActivityInicioFinQR.class);
                                NuevoRea.putExtra("TipoUpdate", "2");
                                startActivity(NuevoRea);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        return root;
    }
}
