package com.example.appclnica;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclnica.ui.home.HomeFragment;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    String Tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mqr );
        scannerView = (ZXingScannerView) findViewById( R.id.zxscanProductos);

        Dexter.withActivity( this )
                .withPermission( Manifest.permission.CAMERA )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler( MainQR.this );
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                } )
                .check();
    }
        @Override
        public void onDestroy(){
            scannerView.stopCamera();
            super.onDestroy();
        }
        @Override
        public void handleResult(Result rawResult) {
        if (rawResult.toString().length() <= 4) {
            Intent I = new Intent(MainQR.this, MainActivityTable.class);
            I.putExtra("ID", rawResult.toString());
            I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, I, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(I);
        }
        /*if (rawResult.toString().length() > 4){
            String uso = rawResult.toString();
            String id_seg = uso.substring(20);
            String id_rea = uso.substring(0, 11);
            UpdateSeg(Tipo, id_seg, id_rea, HomeFragment.filial);
            onBackPressed();
            /*ActivityAlmacenE.lote2.setText(rawResult.toString());
            ActivityAlmacenE.cad.setText(rawResult.toString());
            onBackPressed();
        }*/
    }
};

