package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.widget.ArrayAdapter.createFromResource;
import static androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE;

public class ActivityAlmacen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RequestQueue requestQueue;
    private TextView lbProvee, lbOrden, lbFactura, lbIVA;
    private EditText txtFactura, txtIVA;
    Button btnOrdenC, btnAgregarT;
    Spinner SProveedor, SPedido, SOrden, STatus;
    ArrayList<String> folioPedidos, proveedores, ordenesC, status;
    ArrayList<String[]> nombres;
    ArrayList<CheckBox> checkBoxes;
    TableLayout tablaProductos;
    String orden, folioe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen);

        lbProvee = findViewById(R.id.lbProvee);
        lbOrden = findViewById(R.id.lbOrden);
        SPedido = findViewById(R.id.spinnerPedido);
        SProveedor = findViewById(R.id.spinnerProvee);
        SOrden = findViewById(R.id.spinnerOrdenC);
        Toolbar toolbar = findViewById(R.id.toolbarMenu1);
        tablaProductos = findViewById(R.id.tablaProductos);
        lbFactura = findViewById(R.id.lbFactura);
        txtFactura = findViewById(R.id.txtFactura);
        btnOrdenC = findViewById(R.id.btnOrdenC);
        lbIVA = findViewById(R.id.lbIVA);
        txtIVA = findViewById(R.id.txtIVA);
        btnAgregarT = findViewById(R.id.btnAgregarT);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN & WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        status = new ArrayList<String>();
        status.add("Aprobado");
        status.add("No Aprobado");

        folioPedidos = new ArrayList<String>();
        folioPedidos.add("-Seleccione-");
        CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=0", "folio_pedido", folioPedidos);
        LlenarSpinners(folioPedidos, SPedido);

        btnOrdenC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tablaProductos.setVisibility(View.INVISIBLE);
                if (!txtFactura.getText().toString().isEmpty()) {
                    InsertEntrada(txtFactura.getText().toString(), SOrden.getSelectedItem().toString(),
                            SProveedor.getSelectedItem().toString(), SPedido.getSelectedItem().toString());
                    txtFactura.setText("");

                    TableRow fila = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    fila.setLayoutParams(lp);

                    /*final CheckBox ch = new CheckBox(getApplicationContext());
                    ch.setChecked(true);
                    fila.addView(ch);*/
                    LlenarTextView("", fila, 1);
                    LlenarTextView("Nombre_Producto  ", fila, 1);
                    LlenarTextView("Cantidad Unidad  ", fila, 1);
                    LlenarTextView("Unidad Entrada  ", fila, 1);
                    LlenarTextView("Unidad Salida  ", fila, 1);
                    LlenarTextView("Factor entre unidades  ", fila, 1);
                    LlenarTextView(" Descuento ", fila, 1);
                    LlenarTextView("  ", fila, 1);
                    LlenarTextView("Costo Unitario  ", fila, 1);
                    LlenarTextView("Costo Total  ", fila, 1);
                    LlenarTextView(" ", fila, 1);
                    LlenarTextView("     Status", fila, 1);
                    tablaProductos.addView(fila, 0);

                    /*ch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        for (int i = 0; i < checkBoxes.size(); i++){
                                if (ch.isChecked()){
                                    checkBoxes.get(i).setChecked(true);
                                }else{
                                    checkBoxes.get(i).setChecked(false);
                                }
                            }
                        }
                    });*/

                    nombres = new ArrayList<String[]>();
                    checkBoxes = new ArrayList<CheckBox>();
                    CargarDatosTabla("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=3&folio=" + folioe);

                    txtFactura.clearFocus();
                    lbIVA.setVisibility(View.VISIBLE);
                    txtIVA.setVisibility(View.VISIBLE);
                    tablaProductos.setVisibility(View.VISIBLE);
                    btnAgregarT.setVisibility(View.VISIBLE);

                }else{
                    txtFactura.setError("Favor de llenar éste campo");
                }
            }
        });

        btnAgregarT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < nombres.size(); i++){
                    String[] a = nombres.get(i);
                    String c = a[0];
                    String d = a[1];
                    String e = a[2];
                    Toast.makeText(getApplicationContext(), c + " " + d + " " + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerPedido: {
                if (position != 0) {
                    tablaProductos.removeAllViews();
                    orden = parent.getItemAtPosition(position).toString();

                    proveedores = new ArrayList<String>();
                    proveedores.add("-Seleccione-");
                    CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=1&orden=" + orden, "nombre", proveedores);
                    LlenarSpinners(proveedores, SProveedor);

                    lbProvee.setVisibility(View.VISIBLE);
                    SProveedor.setVisibility(View.VISIBLE);
                } else {
                    lbProvee.setVisibility(View.INVISIBLE);
                    SProveedor.setVisibility(View.INVISIBLE);
                    lbOrden.setVisibility(View.INVISIBLE);
                    SOrden.setVisibility(View.INVISIBLE);
                    tablaProductos.setVisibility(View.INVISIBLE);
                    lbFactura.setVisibility(View.INVISIBLE);
                    txtFactura.setVisibility(View.INVISIBLE);
                    btnOrdenC.setVisibility(View.INVISIBLE);
                    lbIVA.setVisibility(View.INVISIBLE);
                    txtIVA.setVisibility(View.INVISIBLE);
                    btnAgregarT.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.spinnerProvee: {
            if (position != 0){
                tablaProductos.removeAllViews();
                String provee = parent.getItemAtPosition(position).toString();
                provee = provee.replace(" ", "%20").trim();

                ordenesC = new ArrayList<String>();
                ordenesC.add("-Seleccione-");
                CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=2&orden="+ orden+ "&provee="+ provee, "folio_orden", ordenesC);
                LlenarSpinners(ordenesC, SOrden);

                lbOrden.setVisibility(View.VISIBLE);
                SOrden.setVisibility(View.VISIBLE);

            }else{
                lbOrden.setVisibility(View.INVISIBLE);
                SOrden.setVisibility(View.INVISIBLE);
                tablaProductos.setVisibility(View.INVISIBLE);
                lbFactura.setVisibility(View.INVISIBLE);
                txtFactura.setVisibility(View.INVISIBLE);
                btnOrdenC.setVisibility(View.INVISIBLE);
                lbIVA.setVisibility(View.INVISIBLE);
                txtIVA.setVisibility(View.INVISIBLE);
                btnAgregarT.setVisibility(View.INVISIBLE);
            }
                break;
            }
            case R.id.spinnerOrdenC:{
            if (position != 0){
                txtFactura.setText("");
                lbFactura.setVisibility(View.VISIBLE);
                txtFactura.setVisibility(View.VISIBLE);
                btnOrdenC.setVisibility(View.VISIBLE);

                String folio = parent.getItemAtPosition(position).toString();
                folioe = folio;

            }else{
                tablaProductos.setVisibility(View.INVISIBLE);
                lbFactura.setVisibility(View.INVISIBLE);
                txtFactura.setVisibility(View.INVISIBLE);
                btnOrdenC.setVisibility(View.INVISIBLE);
                lbIVA.setVisibility(View.INVISIBLE);
                txtIVA.setVisibility(View.INVISIBLE);
                btnAgregarT.setVisibility(View.INVISIBLE);
            }
                break;
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
                Toast.makeText(getApplicationContext(),"Favor de verificar los campos", Toast.LENGTH_SHORT).show();
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
                            final String m = obj.getString("nombre");
                            final String can = obj.getString("cantidad_unidad");
                            String uni_en = obj.getString("unidad_entrada");
                            String uni_sa = obj.getString("unidad_salida");
                            String factor = obj.getString("factor_entre_unidades");
                            String costo_ind = obj.getString("costo_individual");
                            String costo_tot = obj.getString("costo_total");
                            String lote = obj.getString("lote");

                            TableRow fila = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            fila.setLayoutParams(lp);

                            final CheckBox select = new CheckBox(getApplicationContext());
                            TextView tv = new TextView(getApplicationContext());
                            final EditText Descuento = new EditText(getApplicationContext());
                            final Spinner Status = new Spinner(getApplicationContext());
                            EditText Lote = new EditText(getApplicationContext());
                            final EditText Cad = new EditText(getApplicationContext());
                            STatus = Status;

                            select.setChecked(true);
                            fila.addView(select);

                            tv.setText(m + "  ");
                            tv.setTextColor(Color.BLACK);
                            fila.addView(tv);

                            LlenarTextView(can, fila, 0);
                            LlenarTextView(uni_en, fila, 0);
                            LlenarTextView(uni_sa, fila, 0);
                            LlenarTextView(factor, fila, 0);

                            FormarEdit(Descuento, fila, 0, "Des");
                            LlenarTextView("  ", fila, 1);

                            LlenarTextView(costo_ind, fila, 0);
                            LlenarTextView(costo_tot, fila, 0);
                            LlenarTextView(" ", fila, 1);

                            LlenarSpinners(status, Status);
                            Status.setBackground(getResources().getDrawable(R.drawable.txt));
                            fila.addView(Status);
                            LlenarTextView(" ", fila, 1);

                            if (lote.length() > 0){
                                FormarEdit(Lote, fila, 1, "  Lote  ");
                                LlenarTextView("  ", fila, 1);
                                FormarEdit(Cad, fila, 0, "  Caducidad  ");
                                LlenarTextView("  ", fila, 1);
                            }else{

                                LlenarTextView("  ", fila, 1);
                                LlenarTextView("  ", fila, 1);
                                LlenarTextView("  ", fila, 1);
                                LlenarTextView("  ", fila, 1);
                            }

                            tablaProductos.addView(fila);

                            final String[] a = new String[]{m, can, "SI"};
                            nombres.add(a);

                            //checkBoxes.add(select);

                            Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0){
                                        a[2] = "SI";

                                    }else{
                                        a[2] = "NO";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            Descuento.setFocusableInTouchMode(true);

                            select.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(select.isChecked()){
                                        nombres.add(a);
                                    }else{
                                        nombres.remove(a);
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
                Toast.makeText(getApplicationContext(),"Favor de verificar los campos", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(Array);
    }

    private void LlenarSpinners(ArrayList<String> list, Spinner spinner){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void LlenarTextView(String dato, TableRow fila, int tipodato){
        TextView uso = new TextView(getApplicationContext());
        uso.setText(dato);
        if (tipodato == 0) {
            uso.setTextColor(Color.BLACK);
            uso.setGravity(Gravity.CENTER);
        }else {
            uso.setTextColor(Color.GRAY);
        }
        fila.addView(uso);
    }

    private void FormarEdit(EditText editText, TableRow fila, int tipodato, String fondo){
        editText.setTextColor(Color.BLACK);
        editText.setGravity(Gravity.CENTER);
        editText.setTextSize(15);
        editText.setBackground(getResources().getDrawable(R.drawable.txt));
        if (tipodato == 0) {
            if (fondo == "  Caducidad  "){
                editText.setHint(fondo);
                editText.setHintTextColor(Color.GRAY);
            }
            else if(fondo == "Des"){
                editText.setText("0");
            }
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }else{
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setHint(fondo);
            editText.setHintTextColor(Color.GRAY);
        }
        fila.addView(editText);
    }

    private void InsertEntrada(final String factura, final String folio, String proveedor, final String pedido) {
        proveedor = proveedor.replace(" ", "%20").trim();
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=4&provee=" + proveedor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se registro exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("factura", factura);
                parametros.put("folio_orden_compra", folio);
                parametros.put("num_pedido", pedido);

                return parametros;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
