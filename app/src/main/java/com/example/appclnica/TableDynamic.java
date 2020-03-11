package com.example.appclnica;

import android.content.Context;
import android.graphics.Color;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TableDynamic extends AppCompatActivity {
    private TableLayout tableLayout;
    private Context context;
    private String[]header;
    private ArrayList<String[]>data;
    private TableRow tableRow;
    private TextView txtcell;
    private int indexC;
    private int indexR;
    private boolean MTCOLOR=false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_qrinfo);



    }


    public TableDynamic(TableLayout tableLayout, Context context) {
        this.tableLayout=tableLayout;
        this.context=context;
    }

    public void addHeader(String[]header){ this.header=header;createHeader(); }

    public void addData(ArrayList<String[]>data){ this.data=data;createDataTable(); }

    private void newRow(){ tableRow=new TableRow( context ); }

    private void newCell(){

        txtcell=new TextView(context);
        txtcell.setGravity( Gravity.LEFT );
        txtcell.setTextColor(Color.BLACK);
        txtcell.setIncludeFontPadding( true );
        txtcell.setTextSize(11);


    }

      private void createHeader(){
     indexC=0;
     newRow();
     while(indexC<header.length) {
        newCell();
        txtcell.setText( header[indexC++] );
        tableRow.addView( txtcell, newTableParams());
     }
     tableLayout.addView( tableRow );
     }

     private void createDataTable(){
        String info;
        for (indexR=1; indexR<=header.length;indexR++) {
            newRow();
            for(indexC=0; indexC<=header.length;indexC++){
                newCell();
                String[]row=data.get(indexR-1);
                info=(indexC<row.length)?row [indexC]:"";
                txtcell.setText( info );
                tableRow.addView( txtcell,newTableParams() );
            }
            tableLayout.addView( tableRow );
        }
     }
     public void backGroundHeader(int color){
        indexC=0;
        while(indexC<header.length){
            txtcell=getCell( 0,indexC++ );
            txtcell.setBackgroundColor( color );
        }
        tableLayout.addView( tableRow );

     }

    public void BGDATA(int firtColor, int SecondColor){
        for(indexR=1;indexR<=header.length;indexR++){
            MTCOLOR=!MTCOLOR;
            for(indexC=0;indexC<header.length;indexC++){
                txtcell=getCell( indexR,indexC );
                txtcell.setBackgroundColor( (MTCOLOR)?firtColor:SecondColor );
            }
            tableLayout.addView( tableRow );
        }
    }

    private TableRow getRow(int index){
        return (TableRow) tableLayout.getChildAt( index );
    }


    private TextView getCell(int Rowindex,int columIndex){
        tableRow=getRow( Rowindex );
        return (TextView) tableRow.getChildAt( columIndex );
    }


    private TableRow.LayoutParams  newTableParams(){
        TableRow.LayoutParams params=new TableRow.LayoutParams(  );
        params.setMargins( 5,5,1,1 );
        params.weight=1;
        return params;
    }

}
