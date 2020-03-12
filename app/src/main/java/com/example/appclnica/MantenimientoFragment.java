package com.example.appclnica;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoFragment extends Fragment {

    QRInfoFragment qrInfoFragment =new QRInfoFragment();
    String D=qrInfoFragment.IDQR;
    List<MantConstructor> ListMant;
    RecyclerView RCV;
    AlertDialog InfoMant;
    Button btn;
    RequestQueue queue;
    RelativeLayout expandableView;
    CardView cd;
    TextView txtA,txtB,txtC,txtD,txtE,txtF,txtG;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view= inflater.inflate(R.layout.fragment_mantenimiento, container, false);
        RCV=(RecyclerView)view.findViewById( R.id.rcv );
        txtA=(TextView)view.findViewById( R.id.TXTN2 );
        txtB=(TextView)view.findViewById( R.id.TXTS2 );
        txtC=(TextView)view.findViewById( R.id.TXTA2 );
        txtD=(TextView)view.findViewById( R.id.TXTN2 );
        txtE=(TextView)view.findViewById( R.id.TXTPO2 );
        txtF=(TextView)view.findViewById( R.id.EDTEMPRE2 );
        txtG=(TextView)view.findViewById( R.id.TXTC2 );
        RCV.setHasFixedSize( true );
        RCV.setLayoutManager( new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL,false ) );
        ListMant=new ArrayList<>(  );
        Mostrar("https://asesoresconsultoreslabs.com/asesores/App_Android/GetMant.php?id=" + D + "");
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
                                        mants.getString( "tipo_mant" ),
                                        mants.getString( "sucursal" ),
                                        mants.getString( "area" ),
                                        mants.getString( "nombre" ),
                                        mants.getString( "empresa_mant" ),
                                        mants.getString( "fecha" ),
                                        mants.getString("costoRefacciones" )+" " +":"+" "+ mants.getString("id_equipo")
                                ) );
                                /*Toast.makeText( getActivity().getApplicationContext(),mants.getString( "id_equipo" ),Toast.LENGTH_SHORT ).show();*/
                            }
                            Adapter adapterData=new Adapter( getActivity().getApplicationContext(),ListMant);
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

    private void ShowDialog(){
       AlertDialog.Builder alert;
       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
           alert=new AlertDialog.Builder( getActivity().getApplicationContext(),android.R.style.Theme_Material_Dialog_Alert );
       }else{
           alert= new AlertDialog.Builder( getActivity().getApplicationContext() );

       }
       LayoutInflater inflater=getLayoutInflater();
       View view =inflater.inflate( R.layout.activity_mantenimientos,null );
       alert.setView( view );
       alert.setCancelable( false );

       final AlertDialog dialog =alert.create();
       dialog.getWindow().requestFeature( Window.FEATURE_NO_TITLE );
       dialog.show();

    }
}
