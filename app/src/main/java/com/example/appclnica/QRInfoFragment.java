package com.example.appclnica;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QRInfoFragment extends Fragment {

    private TableLayout tableLayout;
    private String[] header = {"DATOS"," ", "INFORMACIÓN","","","","","","","","","","","","","","","","","",""};
    private ArrayList<String[]> rows = new ArrayList<>();
    RequestQueue requestQueue;
    public static String IDQR="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_qrinfo, container, false);
        Bundle bundle =  getActivity().getIntent().getExtras();
         IDQR = bundle.getString( "ID" );

        TraerEquipo( "https://asesoresconsultoreslabs.com/asesores/App_Android/ConsultaB.php?id=" + IDQR + "");
        tableLayout = view.findViewById( R.id.Table );

        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_qrinfo, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_chat) {
            goToAttract();
        }
        return true;
    }
    public void goToAttract()
    {
        Intent intent = new Intent(getActivity().getApplication(), MainQR.class);
        startActivity(intent);
    }

    private void TraerEquipo(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) { JSONObject InfoEquipo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        InfoEquipo = response.getJSONObject( i );
                        if (InfoEquipo != null) {
                            String A = InfoEquipo.getString( "id" );
                            String B = InfoEquipo.getString( "nombre" );
                            String C = InfoEquipo.getString( "marca" );
                            String D = InfoEquipo.getString( "modelo" );
                            String E = InfoEquipo.getString( "area" );
                            String F = InfoEquipo.getString( "instrumento" );
                            String G = InfoEquipo.getString( "identificador" );
                            String H = InfoEquipo.getString( "nomenclatura" );
                            String I = InfoEquipo.getString( "no_serie" );
                            String J = InfoEquipo.getString( "proveedor" );
                            String K = InfoEquipo.getString( "frec_mant" );
                            String L = InfoEquipo.getString( "resp_equipo" );
                            String M = InfoEquipo.getString( "sucursal" );
                            String V = InfoEquipo.getString( "costo_mantenimiento" );
                            String O = InfoEquipo.getString( "empresa" );
                            String P = InfoEquipo.getString( "telefono" );
                            String Q = InfoEquipo.getString( "email" );
                            String R = InfoEquipo.getString( "manual" );
                            String S = InfoEquipo.getString( "fecha_instalacion" );
                            String W = InfoEquipo.getString( "prot_instalacion" );
                            String X = InfoEquipo.getString( "fecha_inicio_operacion" );
                            rows.add( new String[]{"Id :","  ", A} );
                            rows.add( new String[]{"Nombre :","  ", B} );
                            rows.add( new String[]{"Marca :","  ", C} );
                            rows.add( new String[]{"Modelo :","  ", D} );
                            rows.add( new String[]{"Área :","  ", E} );
                            rows.add( new String[]{"Instrumento :","  ", F} );
                            rows.add( new String[]{"Identificador :","  ", G} );
                            rows.add( new String[]{"Nomenclatura :","  ", H} );
                            rows.add( new String[]{"N° Serie :","  ", I} );
                            rows.add( new String[]{"Proveedor :","  ", J} );
                            rows.add( new String[]{"Frec. Mantenimiento :","  ", K} );
                            rows.add( new String[]{"Responsable de Equipo :","  ", L} );
                            rows.add( new String[]{"Sucursal :","  ", M} );
                            rows.add( new String[]{"Costo Mantenimiento :","  ", V} );
                            rows.add( new String[]{"Empresa :","  ", O} );
                            rows.add( new String[]{"Teléfono :","  ", P} );
                            rows.add( new String[]{"E-mail :","  ", Q} );
                            rows.add( new String[]{"Manual :","  ", R} );
                            rows.add( new String[]{"Fecha Instalación :","  ", S} );
                            rows.add( new String[]{"P.Instalación :","  ", W} );
                            rows.add( new String[]{"Fecha Inicio Operación :","  ", X} );
                            TableDynamic tableDynamic = new TableDynamic( tableLayout, getActivity().getApplicationContext() );
                            tableDynamic.addHeader( header );
                            tableDynamic.addData( rows );
                        }
                    } catch (JSONException e) {
                        Toast.makeText( getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getActivity().getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT ).show();
            }

        } );
        requestQueue = Volley.newRequestQueue( getActivity().getApplicationContext() );
        requestQueue.add( jsonArrayRequest );
    }

}

