package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ActivityContadorQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    DatePickerDialog.OnDateSetListener setListener;
    TextView lbContador;
    String codigo, codigos;
    private ZXingScannerView scannerView;
    static int inicio;
    int contador;
    ImageButton btnNuevo, btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador_qr);

        lbContador = findViewById(R.id.lbPruebas);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        scannerView = findViewById(R.id.zxscanProductos);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnCerrar = findViewById(R.id.btnCerrar);

        setSupportActionBar(toolbar);

        scannerView.startCamera();
        scannerView.setResultHandler(this);

        contador = Integer.parseInt(ActivityVerificacion.canti_recibida.getText().toString());
        lbContador.setText("Total: " + contador);
        btnNuevo.setVisibility(View.INVISIBLE);
        inicio = 1;

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio++;
                scannerView.startCamera();
                scannerView.setResultHandler(ActivityContadorQR.this);
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityVerificacion.canti_recibida.setText("" + contador);
                onBackPressed();
            }
        });

    }

    @Override
    public void handleResult(Result rawResult) {
        codigo = rawResult.toString();
        contador++;

        if (inicio > 1){
            if (codigo.equals(codigos)){
                if (contador != Integer.parseInt(ActivityVerificacion.cant_envi)) {
                    lbContador.setText("Total: " + contador);
                    codigos = codigo;
                    btnNuevo.setVisibility(View.VISIBLE);
                }else{
                    ActivityVerificacion.canti_recibida.setText("" + contador);
                    onBackPressed();
                    ActivityVerificacion.btnLeer.setVisibility(View.INVISIBLE);
                }
            }else{
                ActivityVerificacion.canti_recibida.setText("" + (contador - 1));
                onBackPressed();
            }
        }else{
            if (contador != Integer.parseInt(ActivityVerificacion.cant_envi)) {
                lbContador.setText("Total: " + contador);
                codigos = codigo;
                btnNuevo.setVisibility(View.VISIBLE);
            }else{
                ActivityVerificacion.canti_recibida.setText("" + contador);
                onBackPressed();
                ActivityVerificacion.btnLeer.setVisibility(View.INVISIBLE);
            }
        }

        //Toast.makeText(getApplicationContext(), "" + inicio, Toast.LENGTH_LONG).show();

        /*if (contador != Integer.parseInt(ActivityVerificacion.cant_envi)) {
            lbContador.setText("Total: " + contador);
            codigos = codigo;
            btnNuevo.setVisibility(View.VISIBLE);
        }else{
            ActivityVerificacion.canti_recibida.setText("" + contador);
            onBackPressed();
            ActivityVerificacion.btnLeer.setVisibility(View.INVISIBLE);
        }*/
    }
}
