package com.example.appclnica;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainQR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private TextView
            txtResult;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mqr );
        scannerView = (ZXingScannerView) findViewById( R.id.zxscan );
        txtResult = (TextView) findViewById( R.id.txt_result );


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
        txtResult.setText( rawResult.getText() );
        Intent I = new Intent( MainQR.this, MainActivityTable.class );
        I.putExtra( "ID", rawResult.toString() );
        I.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        PendingIntent pendingIntent = PendingIntent.getActivity( getApplicationContext(), 0, I, PendingIntent.FLAG_UPDATE_CURRENT );
        startActivity( I );
    }



}

