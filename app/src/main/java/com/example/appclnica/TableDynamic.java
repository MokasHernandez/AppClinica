package com.example.appclnica;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[]header;
    private ArrayList<String[]>data;
    private TableRow tableRow;
    private TextView txtcell;
    private int indexC;
    private int indexR;

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
        txtcell.setTextSize( 10);

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

    private TableRow.LayoutParams  newTableParams(){
        TableRow.LayoutParams params=new TableRow.LayoutParams(  );
        params.setMargins( 1,1,1,1 );
        params.weight=1;
        return params;
    }



}
