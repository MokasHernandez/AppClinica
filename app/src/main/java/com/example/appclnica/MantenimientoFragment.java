package com.example.appclnica;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoFragment extends Fragment {
    QRInfoFragment QRInfoFragment =new QRInfoFragment();
    String IDMT= QRInfoFragment.IDQR;
    List<MantConstructor> ListMant;
    RecyclerView RCV;
    AlertDialog InfoMant;
    Button btn;
    RequestQueue queue;
    RelativeLayout expandableView;
    CardView cd;
    TextView txt1,txt2,txt3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view= inflater.inflate(R.layout.fragment_mantenimiento, container, false);
        RCV=(RecyclerView)view.findViewById( R.id.rcv );
        /*txt1=(TextView)view.findViewById( R.id.TXTNON );
        txt2=(TextView)view.findViewById( R.id.desc );
        txt3=(TextView)view.findViewById( R.id.TXTMANTTIPO );*/
        RCV.setHasFixedSize( true );
        RCV.setLayoutManager( new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL,false ) );
        ListMant=new ArrayList<>(  );

        /*btn=(Button)view.findViewById( R.id.btndep );
        cd=(CardView) view.findViewById( R.id.CDW2 ) ;*/

        Mostrar("https://asesoresconsultoreslabs.com/asesores/App_Android/GetMant.php");
        return  view;
    }


    public void Mostrar(String URL) {
        StringRequest stringRequest= new StringRequest( Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array= new JSONArray( response );
                            for (int i=0;i<array.length();i++){
                                JSONObject mants=array.getJSONObject( i );
                                ListMant.add(new MantConstructor(
                                        mants.getInt( "id_equipo" ),
                                        mants.getString( "fecha" ),
                                        mants.getString( "nombre" ),
                                        mants.getString( "fecha" ),
                                        mants.getString( "nombre" ),
                                        mants.getString( "fecha" ),
                                        mants.getString( "tipo_mant" )

                                ) );
                                /*
                                *   String id = item.getString("Id");
                        String sucursal = item.getString("Sucursal");
                        String area = item.getString("Area");
                        String nombre = item.getString("Nombre")+ ":" + item.getString("Id");
                        String tipo = item.getString("Tipo");
                        String empresa = item.getString("Empresa");
                        String fecha = item.getString("Fecha");*/
                                Toast.makeText( getActivity().getApplicationContext(),mants.getString( "id_equipo" ),Toast.LENGTH_SHORT ).show();
                            }
                            Adapter adapterData=new Adapter( getActivity().getApplicationContext(),ListMant );
                            RCV.setAdapter( adapterData);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getActivity(), "no hay conexion", Toast.LENGTH_SHORT ).show();
            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add( stringRequest );
    }



/*************************Metodos de Fragments**********************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mantenimiento, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_status) {
            goToAttract();
        }
        return true;
    }

    public void goToAttract()
    {
        Intent intent = new Intent(getActivity().getApplication(), Mantenimientos.class);
        startActivity(intent);
    }
}
