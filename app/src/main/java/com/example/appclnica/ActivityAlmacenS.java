package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityAlmacenS extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RequestQueue requestQueue;
    TextView lbNum_Ped, lbUnidad, lbArea;
    Spinner SNum_P, SUdn, SArea;
    ArrayList<String> num_ped, unidad, areas;
    TableLayout tablaP;
    String unidada, orden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen_s);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        lbNum_Ped = findViewById(R.id.lbNum_Ped);
        lbUnidad = findViewById(R.id.lbUdn);
        lbArea = findViewById(R.id.lbArea);
        SNum_P = findViewById(R.id.spinnerNum_P);
        SUdn = findViewById(R.id.spinnerUnidad);
        SArea = findViewById(R.id.spinnerArea);
        tablaP = findViewById(R.id.tblPro);
        setSupportActionBar(toolbar);

        num_ped = new ArrayList<String>();
        CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=0", "folio_pedido", num_ped);
        LlenarSpinners(num_ped, SNum_P);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerNum_P:{
                if (position != 0){
                    orden = parent.getItemAtPosition(position).toString();

                    unidad = new ArrayList<String>();
                    CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=6&orden=" + orden, "udn", unidad);
                    LlenarSpinners(unidad, SUdn);

                    lbUnidad.setVisibility(View.VISIBLE);
                    SUdn.setVisibility(View.VISIBLE);
                }else{
                    lbUnidad.setVisibility(View.INVISIBLE);
                    SUdn.setVisibility(View.INVISIBLE);
                    lbArea.setVisibility(View.INVISIBLE);
                    SArea.setVisibility(View.INVISIBLE);
                    tablaP.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.spinnerUnidad:{
                if (position != 0){
                    unidada = parent.getItemAtPosition(position).toString();
                    tablaP.removeAllViews();

                    Character a = unidada.charAt(unidada.length()-1);

                    areas = new ArrayList<String>();
                    CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=7&udn=" + unidada, "nombre", areas);
                    LlenarSpinners(areas, SArea);

                    if (a == 'z') {
                        lbArea.setVisibility(View.VISIBLE);
                        SArea.setVisibility(View.VISIBLE);
                        tablaP.setVisibility(View.INVISIBLE);
                    }else{
                        lbArea.setVisibility(View.INVISIBLE);
                        SArea.setVisibility(View.INVISIBLE);
                        CrearEncabezado();
                        unidada = unidada.replace(" ", "%20").trim();
                        CargarDatosTabla("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=8&udn=" + unidada + "&area=" + "" + "&orden=" + orden);
                        tablaP.setVisibility(View.VISIBLE);
                    }
                }else {
                    lbArea.setVisibility(View.INVISIBLE);
                    SArea.setVisibility(View.INVISIBLE);
                    tablaP.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.spinnerArea:{
                if (position != 0){
                    String area = parent.getItemAtPosition(position).toString();

                    tablaP.removeAllViews();
                    CrearEncabezado();
                    area = area.replace(" ", "%20").trim();
                    CargarDatosTabla("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=8&udn=" + unidada + "&area=" + area + "&orden=" + orden);
                    tablaP.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void LlenarSpinners(ArrayList<String> list, Spinner spinner){
        list.add("-Seleccione-");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
                //Toast.makeText(getApplicationContext(),"Favor de verificar los campos", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(Array);
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
                            final String cantidad = obj.getString("cantidad");
                            final String cantidad_autorizada = obj.getString("cantidad_autorizada");

                            TableRow fila = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            fila.setLayoutParams(lp);

                            final EditText CanSurtir = new EditText(getApplicationContext());

                            LlenarTextView(producto, fila, 0);
                            LlenarTextView(cantidad, fila, 0);
                            LlenarTextView(cantidad_autorizada, fila, 0);
                            numero1 = Integer.parseInt(cantidad);

                            LlenarTextView(" ", fila, 0);
                            FormarEdit(CanSurtir, fila);

                            tablaP.addView(fila);

                            numero2[0] = Integer.parseInt(CanSurtir.getText().toString());

                            CanSurtir.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (CanSurtir.length()<1) {
                                        numero2[0] = 0;
                                    }else{
                                        numero2[0] = Integer.parseInt(CanSurtir.getText().toString());
                                        if (numero2[0] > numero1){
                                            CanSurtir.setError("Cant. mayor a la autorizada");
                                        }
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
        uso.setGravity(Gravity.CENTER);
        if (tipodato == 0) {
            uso.setTextColor(Color.BLACK);
        }else {
            uso.setTextColor(Color.GRAY);
        }
        fila.addView(uso);
    }

    private void CrearEncabezado(){
        TableRow fila = new TableRow(getApplicationContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(lp);

        LlenarTextView("Producto  ", fila, 1);
        LlenarTextView("Cantidad ", fila, 1);
        LlenarTextView("Cantidad \n Autorizada ", fila, 1);
        LlenarTextView(" ", fila, 0);
        LlenarTextView("Cantidad \n Surtir", fila, 1);
        tablaP.addView(fila, 0);
    }

    private void FormarEdit(EditText editText, TableRow fila){
        editText.setTextColor(Color.BLACK);
        editText.setGravity(Gravity.CENTER);
        editText.setTextSize(15);
        editText.setText("0");
        editText.setBackground(getResources().getDrawable(R.drawable.txt));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        fila.addView(editText);
    }
}
