package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclnica.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityVerificacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView lbPedido, lbSalida;
    Spinner SPedido, SFolioS;
    private RequestQueue requestQueue;
    private ArrayList<String> pedidos, folios;
    private ArrayList<String[]> updates;
    TableLayout tablaP;
    public static EditText canti_recibida;
    public static String cant_envi;
    public static Button btnLeer;
    static String orden;
    Button btnRegistro;
    private boolean bandera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        lbPedido = findViewById(R.id.lbPedido);
        lbSalida = findViewById(R.id.lbFolioS);
        SPedido = findViewById(R.id.spinnerPedido);
        SFolioS = findViewById(R.id.spinnerFolioS);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        tablaP = findViewById(R.id.tblSalidas);
        btnRegistro = findViewById(R.id.btnRegistrar);

        setSupportActionBar(toolbar);

        pedidos = new ArrayList<>();
        CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=12", "folio_pedido", pedidos);
        LlenarSpinners(pedidos, SPedido);
        lbSalida.setVisibility(View.INVISIBLE);
        SFolioS.setVisibility(View.INVISIBLE);
        tablaP.setVisibility(View.INVISIBLE);
        btnRegistro.setVisibility(View.INVISIBLE);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < updates.size(); i++){
                    String[] uso = updates.get(i);
                    String clave_mat = uso[0];
                    String cant_auto = uso[1];
                    String cant_re = uso[2];
                    ActualizarSalida(clave_mat, cant_auto, cant_re, SFolioS.getSelectedItem().toString());
                    if (bandera == true){
                        Toast.makeText(getApplicationContext(), "Se registraron los datos con éxito", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error en los registros", Toast.LENGTH_LONG).show();
                    }
                    SFolioS.setSelection(0);

                    folios = new ArrayList<>();
                    CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=13&orden=" + orden + "&udn=" + HomeFragment.filial, "folio_salida", folios);
                    LlenarSpinners(folios, SFolioS);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerPedido:{
                if (position != 0){

                    orden = parent.getItemAtPosition(position).toString();

                    folios = new ArrayList<>();
                    String filial2 = HomeFragment.filial.replace(" ", "%20");
                    CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=13&orden=" + orden + "&udn=" + filial2, "folio_salida", folios);
                    LlenarSpinners(folios, SFolioS);

                    lbSalida.setVisibility(View.VISIBLE);
                    SFolioS.setVisibility(View.VISIBLE);

                }else{
                    tablaP.removeAllViews();
                    lbSalida.setVisibility(View.INVISIBLE);
                    SFolioS.setVisibility(View.INVISIBLE);
                    tablaP.setVisibility(View.INVISIBLE);
                    btnRegistro.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.spinnerFolioS:{
                if (position != 0) {
                    String folio = parent.getItemAtPosition(position).toString();

                    tablaP.removeAllViews();
                    CrearEncabezado();
                    updates = new ArrayList<>();
                    CargarDatosTabla("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=14&orden=" + orden + "&folio=" + folio);
                    tablaP.setVisibility(View.VISIBLE);
                    btnRegistro.setVisibility(View.VISIBLE);
                }else {
                    tablaP.removeAllViews();
                    btnRegistro.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void CargarDatos(String URL, final String dato, final ArrayList<String> Lista){
        JsonArrayRequest Array = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        if (obj != null) {
                            String m = obj.getString(dato);
                            Lista.add(m);
                        }
                    } catch (JSONException c) {
                        Toast.makeText(getApplicationContext(), c.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (dato.equals("folio_salida")) {
                    new AlertDialog.Builder(ActivityVerificacion.this)
                            .setTitle("No existen salidas para ésta Unidad")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    lbSalida.setVisibility(View.INVISIBLE);
                                    SFolioS.setVisibility(View.INVISIBLE);
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(Array);
    }

    private void LlenarSpinners(ArrayList<String> list, Spinner spinner){
        list.add("-Seleccione-");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void CargarDatosTabla(String URL){
        JsonArrayRequest Array = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        if (obj != null) {
                            final int numero1;
                            final int[] numero2 = new int[1];
                            final String producto = obj.getString("clave_producto");
                            final String cantidad_salida = obj.getString("cantidad_salida");
                            final String cantidad_autorizada = obj.getString("cantidad_autorizada");
                            final String scantidad_recibida = obj.getString("cantidad_recibida");

                            TableRow fila = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            fila.setLayoutParams(lp);

                            final EditText cantidad_recibida = new EditText(getApplicationContext());
                            Button QR = new Button(getApplicationContext());

                            LlenarTextView(producto, fila, 2);
                            LlenarTextView("   ", fila, 1);
                            LlenarTextView(cantidad_autorizada, fila, 0);
                            LlenarTextView(cantidad_salida, fila, 0);

                            LlenarTextView(" ", fila, 0);
                            FormarEdit(cantidad_recibida, fila, scantidad_recibida);
                            LlenarTextView(" ", fila, 1);

                            QR.setBackground(getResources().getDrawable(R.drawable.btn_uno));
                            QR.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.boton)));
                            QR.setTextColor(getResources().getColor(R.color.icons));
                            QR.setTextSize(14);
                            QR.setText("Leer QR");
                            fila.addView(QR);
                            LlenarTextView(" ", fila, 1);

                            tablaP.addView(fila);

                            btnLeer = QR;
                            cant_envi = cantidad_salida;
                            canti_recibida = cantidad_recibida;

                            numero1 = Integer.parseInt(cantidad_autorizada);
                            numero2[0] = Integer.parseInt(cantidad_recibida.getText().toString());

                            final String[] nuevo = new String[]{producto, cantidad_autorizada, String.valueOf(numero2[0])};
                            updates.add(nuevo);

                            QR.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent PasoLectura = new Intent(getApplicationContext(), ActivityContadorQR.class);
                                    startActivity(PasoLectura);
                                }
                            });

                            cantidad_recibida.setEnabled(false);
                            cantidad_recibida.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    numero2[0] = Integer.parseInt(cantidad_recibida.getText().toString());

                                    if (numero2[0] > numero1) {
                                        cantidad_recibida.setError("No válido");
                                        btnRegistro.setVisibility(View.INVISIBLE);
                                    }else{
                                        cantidad_recibida.setError(null);
                                        nuevo[2] = cantidad_recibida.getText().toString();
                                        btnRegistro.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    } catch (JSONException c) {
                        Toast.makeText(getApplicationContext(), c.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                tablaP.removeAllViews();
                TableRow fila = new TableRow(getApplicationContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                fila.setLayoutParams(lp);
                LlenarTextView("No existen datos con éstos parámetros", fila, 0);
                tablaP.addView(fila, 0);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(Array);
    }

    private void LlenarTextView(String dato, TableRow fila, int tipodato){
        TextView uso = new TextView(getApplicationContext());
        uso.setText(dato);
        if (tipodato == 0) {
            uso.setTextColor(Color.BLACK);
            uso.setGravity(Gravity.CENTER);
        }
        else if (tipodato == 2){
            uso.setTextColor(Color.BLACK);
            uso.setGravity(Gravity.LEFT);

        }else {
            uso.setTextColor(Color.GRAY);
            uso.setGravity(Gravity.CENTER);
        }
        fila.addView(uso);
    }

    private void CrearEncabezado(){
        TableRow fila = new TableRow(getApplicationContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(lp);

        LlenarTextView("Clave  ", fila, 1);
        LlenarTextView("   ", fila, 1);
        LlenarTextView("Cantidad \n Autorizada ", fila, 1);
        LlenarTextView("Cantidad \n Enviada", fila, 1);
        LlenarTextView("   ", fila, 1);
        LlenarTextView("Cantidad \n Recibida", fila, 1);
        LlenarTextView("   ", fila, 1);
        LlenarTextView("   ", fila, 1);
        LlenarTextView("   ", fila, 1);
        tablaP.addView(fila, 0);
    }

    private void FormarEdit(EditText editText, TableRow fila, String texto){
        editText.setTextColor(Color.BLACK);
        editText.setGravity(Gravity.CENTER);
        editText.setTextSize(15);
        editText.setText(texto);
        editText.setBackground(getResources().getDrawable(R.drawable.txt));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        fila.addView(editText);
    }

    private void ActualizarSalida(final String materia, final String cantidad_auto, final String cantidad_recibida, final String folio_salida) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=15",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        bandera = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bandera = false;
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_mat", materia);
                parametros.put("cant_auto", cantidad_auto);
                parametros.put("cant_re", cantidad_recibida);
                parametros.put("folio_salida", folio_salida);
                return parametros;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
