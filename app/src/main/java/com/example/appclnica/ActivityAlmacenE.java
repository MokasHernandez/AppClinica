package com.example.appclnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityAlmacenE extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static EditText lote2, cad;
    private RequestQueue requestQueue;
    private TextView lbProvee, lbOrden, lbFactura, lbIVA, lbMonto, lbIVATotal, lbMontoTotal;
    private EditText txtFactura, txtIVA;
    Button btnOrdenC, btnAgregarT;
    Spinner SProveedor, SPedido, SOrden;
    ArrayList<String> folioPedidos, proveedores, ordenesC, status;
    ArrayList<String[]> nombres;
    ArrayList<Float> Totales;
    ArrayList<CheckBox> checkBoxes;
    TableLayout tablaProductos;
    String orden, folioe, num_ent;
    Boolean bandera = true;
    ScrollView scrollView;
    float TotalMonto;

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
        scrollView = findViewById(R.id.ScrollView1);
        lbMonto = findViewById(R.id.lbMonto);
        lbIVATotal = findViewById(R.id.lbIVATotal);
        lbMontoTotal = findViewById(R.id.lbMontoTotal);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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

                    LlenarTextView("", fila, 1);
                    LlenarTextView("Clave", fila, 1);
                    LlenarTextView("  ", fila, 1);
                    LlenarTextView("Nombre_Producto  ", fila, 1);
                    LlenarTextView("Cantidad Unidad  ", fila, 1);
                    LlenarTextView("Unidad Entrada  ", fila, 1);
                    LlenarTextView("Unidad Salida  ", fila, 1);
                    LlenarTextView("Factor entre unidades  ", fila, 1);
                    LlenarTextView("Costo Unitario  ", fila, 1);
                    LlenarTextView("Costo Total  ", fila, 1);
                    LlenarTextView("  ", fila, 1);
                    LlenarTextView(" Descuento ", fila, 1);
                    LlenarTextView(" ", fila, 1);
                    LlenarTextView(" Estatus", fila, 1);
                    tablaProductos.addView(fila, 0);

                    nombres = new ArrayList<String[]>();
                    Totales = new ArrayList<>();
                    checkBoxes = new ArrayList<CheckBox>();
                    CargarDatosTabla("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=3&folio=" + folioe);

                    OcultarTeclado();

                    lbIVA.setVisibility(View.VISIBLE);
                    txtIVA.setVisibility(View.VISIBLE);
                    lbMonto.setVisibility(View.VISIBLE);
                    tablaProductos.setVisibility(View.VISIBLE);
                    lbMontoTotal.setVisibility(View.VISIBLE);
                    lbMonto.setVisibility(View.VISIBLE);
                    lbIVATotal.setVisibility(View.VISIBLE);
                    btnAgregarT.setVisibility(View.VISIBLE);
                    btnOrdenC.setVisibility(View.GONE);
                    txtFactura.setVisibility(View.GONE);
                    lbFactura.setVisibility(View.GONE);

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
                    String materia  = a[0];
                    String uni_ent = a[1];
                    String fact_entre = a[2];
                    String canti = a[3];
                    String lote = a[4];
                    String cadu = a[5];
                    String costo = a[6];
                    String desc = a[7];
                    String cumple = a[8];
                    String segui = a[9];
                    //Toast.makeText(getApplicationContext(), "" + materia, Toast.LENGTH_SHORT).show();
                    InsertDetalle_Orden(materia, num_ent, uni_ent, fact_entre, canti, lote, cadu, costo, desc,
                            txtIVA.getText().toString(), SPedido.getSelectedItem().toString(), cumple, segui, SProveedor.getSelectedItem().toString());
                }
                if (bandera == true){
                    Toast.makeText(getApplicationContext(), "Se registraron los datos con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error en los registros", Toast.LENGTH_LONG).show();
                }
                SProveedor.setSelection(0);
            }
        });
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tablaProductos.removeAllViews();
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
                    lbMontoTotal.setVisibility(View.GONE);
                    lbMonto.setVisibility(View.GONE);
                    lbIVATotal.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.spinnerProvee: {
            if (position != 0){
                tablaProductos.removeAllViews();
                String provee = parent.getItemAtPosition(position).toString();
                provee = provee.replace(" ", "%20").trim();
                provee = provee.replace("&", "%26");

                ordenesC = new ArrayList<String>();
                ordenesC.add("-Seleccione-");
                CargarDatos("http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=2&orden="+ orden+ "&provee="+ provee, "folio_orden", ordenesC);
                LlenarSpinners(ordenesC, SOrden);

                lbOrden.setVisibility(View.VISIBLE);
                SOrden.setVisibility(View.VISIBLE);

            }else{
                tablaProductos.removeAllViews();
                lbOrden.setVisibility(View.INVISIBLE);
                SOrden.setVisibility(View.INVISIBLE);
                tablaProductos.setVisibility(View.INVISIBLE);
                lbFactura.setVisibility(View.INVISIBLE);
                txtFactura.setVisibility(View.INVISIBLE);
                btnOrdenC.setVisibility(View.INVISIBLE);
                lbIVA.setVisibility(View.INVISIBLE);
                txtIVA.setVisibility(View.INVISIBLE);
                btnAgregarT.setVisibility(View.INVISIBLE);
                lbMontoTotal.setVisibility(View.GONE);
                lbMonto.setVisibility(View.GONE);
                lbIVATotal.setVisibility(View.GONE);
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
                lbMontoTotal.setVisibility(View.GONE);
                lbMonto.setVisibility(View.GONE);
                lbIVATotal.setVisibility(View.GONE);
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
                            String clave = obj.getString("clave");
                            final String m = obj.getString("nombre");
                            final String can = obj.getString("cantidad_unidad");
                            String uni_en = obj.getString("unidad_entrada");
                            String uni_sa = obj.getString("unidad_salida");
                            String factor = obj.getString("factor_entre_unidades");
                            String costo_ind = obj.getString("costo_individual");
                            String costo_tot = obj.getString("costo_total");
                            String lote = obj.getString("lote");
                            String QR = obj.getString("QR");
                            String seg = obj.getString("seguimiento");

                            final TableRow fila = new TableRow(getApplicationContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            fila.setLayoutParams(lp);

                            final float[] Total = new float[1];
                            final int entrada = Integer.parseInt(can);
                            final int factor_uni = Integer.parseInt(factor);
                            final float[] costo = {Float.parseFloat(costo_ind)};

                            Total[0] = (entrada * factor_uni * costo[0]);
                            Totales.add(Total[0]);

                            final int[] numero2 = new int[1];
                            final CheckBox select = new CheckBox(getApplicationContext());
                            final EditText Descuento = new EditText(getApplicationContext());
                            final Spinner Status = new Spinner(getApplicationContext());
                            final EditText Lote = new EditText(getApplicationContext());
                            final EditText Cad = new EditText(getApplicationContext());
                            final EditText Costo_Ind = new EditText(getApplicationContext());
                            Button QRLectura = new Button(getApplicationContext());

                            select.setChecked(true);
                            fila.addView(select);

                            LlenarTextView(clave, fila, 2);
                            LlenarTextView("  ", fila, 0);
                            LlenarTextView(m, fila, 2);
                            LlenarTextView(can, fila, 0);
                            LlenarTextView(uni_en, fila, 0);
                            LlenarTextView(uni_sa, fila, 0);
                            LlenarTextView(factor, fila, 0);
                            Costo_Ind.setText(costo_ind);
                            FormarEdit(Costo_Ind, fila, 0, "Des");
                            LlenarTextView(costo_tot, fila, 0);

                            LlenarTextView("  ", fila, 1);
                            Descuento.setText("0");
                            FormarEdit(Descuento, fila, 0, "Des");
                            LlenarTextView("  ", fila, 1);

                            LlenarSpinners(status, Status);
                            Status.setBackground(getResources().getDrawable(R.drawable.txt));
                            fila.addView(Status);
                            LlenarTextView(" ", fila, 1);

                            QRLectura.setBackground(getResources().getDrawable(R.drawable.btn_uno));
                            QRLectura.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.boton)));
                            QRLectura.setTextColor(getResources().getColor(R.color.icons));
                            QRLectura.setTextSize(14);
                            QRLectura.setText("Leer QR");

                            if (lote.length() > 0){
                                FormarEdit(Lote, fila, 1, "  Lote  ");
                                LlenarTextView("  ", fila, 1);
                                FormarEdit(Cad, fila, 0, "  Caducidad  ");
                                LlenarTextView("  ", fila, 1);
                                btnAgregarT.setVisibility(View.INVISIBLE);

                                if (QR.equals("1")){
                                    fila.addView(QRLectura);
                                    Lote.setEnabled(false);
                                    Cad.setEnabled(false);
                                    btnAgregarT.setVisibility(View.INVISIBLE);
                                }

                            }else{
                                LlenarTextView("  ", fila, 1);
                                LlenarTextView("  ", fila, 1);
                                LlenarTextView("  ", fila, 1);
                                LlenarTextView("  ", fila, 1);
                            }

                            lote2 = Lote;
                            cad = Cad;

                            tablaProductos.addView(fila);

                            final String[] a = new String[]{clave, uni_en, factor, can, "", "", costo_ind, Descuento.getText().toString(),
                                    "SI", seg};
                            nombres.add(a);

                            //checkBoxes.add(select);

                            Costo_Ind.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    Totales.remove(Total[0]);
                                    if (Costo_Ind.length() < 1) {
                                        costo[0] = 0;
                                    }else{
                                        costo[0] = Float.parseFloat(Costo_Ind.getText().toString());
                                    }
                                    Total[0] = (entrada * factor_uni * costo[0]);
                                    Totales.add(Total[0]);
                                    MostrarMontos();
                                }
                            });

                            Descuento.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (Descuento.length() < 1) {
                                        numero2[0] = 0;
                                    }else{
                                        numero2[0] = Integer.parseInt(Descuento.getText().toString());
                                        if (numero2[0] > 99){
                                            Descuento.setError("No válido");
                                            btnAgregarT.setVisibility(View.INVISIBLE);
                                        }else{
                                            a[7] = Descuento.getText().toString();
                                            btnAgregarT.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            });

                            Lote.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (Lote.length() < 1 || Cad.length() < 1) {
                                        btnAgregarT.setVisibility(View.INVISIBLE);
                                    }else{
                                        btnAgregarT.setVisibility(View.VISIBLE);
                                    }
                                    a[4] = Lote.getText().toString();
                                }
                            });

                            Cad.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (Cad.length() < 1 || Lote.length() < 1){
                                        btnAgregarT.setVisibility(View.INVISIBLE);
                                    }else{
                                        btnAgregarT.setVisibility(View.VISIBLE);
                                    }
                                    a[5] = Cad.getText().toString();

                                }
                            });

                            Cad.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDatePickerDialog(Cad);
                                }
                            });

                            Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0){
                                        a[8] = "SI";

                                    }else{
                                        a[8] = "NO";
                                        Descuento.requestFocus();
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
                                        Totales.add(Total[0]);
                                        MostrarMontos();
                                        btnAgregarT.setVisibility(View.VISIBLE);
                                    }else{
                                        nombres.remove(a);
                                        Totales.remove(Total[0]);
                                        MostrarMontos();

                                        if (nombres.size() < 1){
                                            btnAgregarT.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                            });

                            QRLectura.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent a = new Intent(getApplicationContext(), MainQR.class);
                                    startActivity(a);
                                }
                            });
                        }
                    } catch (JSONException c) {
                        Toast.makeText(getApplicationContext(), c.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                MostrarMontos();
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
        uso.setTextColor(Color.BLACK);
        if (tipodato == 0) {;
            uso.setGravity(Gravity.CENTER);
        }
        else if (tipodato == 2){
            uso.setGravity(Gravity.LEFT);

        }else {
            uso.setTextColor(Color.GRAY);
            uso.setGravity(Gravity.CENTER);
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
                editText.setFocusable(false);
                editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
            }
            else if(fondo == "Des"){
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
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
                        num_ent = response;
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

    private void InsertDetalle_Orden(final String materia, final String no, final String entrada, final String factor, final String canti, final String lote
    , final String caducidad, final String costo, final String desc, final String iva, final String pedido, final String cumple, final String segui, final String provee) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://asesoresconsultoreslabs.com/asesores/App_Android/Almacen.php?id=5",
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
                parametros.put("no_entrada", no);
                parametros.put("unidad_entrada", entrada);
                parametros.put("factor_entre_unidades", factor);
                parametros.put("cantidad", canti);
                parametros.put("lote", lote);
                parametros.put("caducidad", caducidad);
                parametros.put("costo", costo);
                parametros.put("descuento", desc);
                parametros.put("iva", iva);
                parametros.put("num_pedido", pedido);
                parametros.put("cumple", cumple);
                parametros.put("segui", segui);
                parametros.put("provee", provee);
                parametros.put("id_mat", materia);
                return parametros;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void showDatePickerDialog(final EditText uso){
        com.example.appclnica.DatePicker newFragment = com.example.appclnica.DatePicker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String selectedDate = year + "-" + (month) + "-" + dayOfMonth;
                if (month < 10){
                    selectedDate = year + "-0" + (month) + "-" + dayOfMonth;
                }
                if (dayOfMonth < 10){
                    selectedDate = year + "-" + (month) + "-0" + dayOfMonth;
                }
                if (month < 10 & dayOfMonth < 10) {
                    selectedDate = year + "-0" + (month) + "-0" + dayOfMonth;
                }
                uso.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void OcultarTeclado(){
        View view = this.getCurrentFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void MostrarMontos(){
        TotalMonto = 0;
        for (int i = 0; i < Totales.size(); i++){
            TotalMonto += Totales.get(i);
        }
        DecimalFormat formato1 = new DecimalFormat("#,###,##0.00");
        lbMonto.setText("Monto: " + formato1.format(TotalMonto));
        lbIVATotal.setText("IVA Total: " + formato1.format(TotalMonto * 0.16));
        lbMontoTotal.setText("Monto Total: " + formato1.format(TotalMonto * 1.16));
    }
}
