package com.example.appclnica.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclnica.R;
import com.google.android.material.navigation.NavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private int[] mImages = new int[]{
            R.drawable.doctor, R.drawable.htdscaro
    };
    private HomeViewModel homeViewModel;
    private RequestQueue requestQueue;
    private int total;
    public static int contador;
    public static String filial, ID;
    private static String Message;
    private NavigationView navigationView;
    private Spinner SUnidad;
    private ArrayList<String> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        final CarouselView carouselView = root.findViewById(R.id.carousel);
        navigationView = getActivity().findViewById(R.id.nav_view);

        contador++;

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            Message = bundle.getString("Mensaje");
            ID = bundle.getString("ID");
            filial = bundle.getString("filial");
        }

        Consulta2("http://asesoresconsultoreslabs.com/asesores/App_Android/select.php?idt=1&id=" + ID + "");

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Bienvenido/a: " + Message);
            }
        });

        Consulta("https://asesoresconsultoreslabs.com/asesores/App_Android/Notificacion_Corres.php?ID_Usuario=" + ID + "");

        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });

        View roota = inflater.inflate(R.layout.dialog_layout, container, false);
        SUnidad = roota.findViewById(R.id.spinnerSubfiliales);

        if ((filial.equals("Tuxtepec") || filial.equals("TUXTEPEC")) && contador < 2) {
            CrearDialog(roota);
            list.add("Tuxtepec");
            list.add("Tuxtepec Toma 3 Valles");

        } else if ((filial.equals("Libres") || filial.equals("LIBRES")) && contador < 2) {
            CrearDialog(roota);
            list.add("Libres");
            list.add("Libres Toma");

        } else if ((filial.equals("Lara Grajales") || filial.equals("LARA GRAJALE")) && contador < 2) {
            CrearDialog(roota);
            list.add("Lara Grajales");
            list.add("Lara Grajales Toma");
            list.add("Lara Grajales RX");

        } else if ((filial.equals("Loma Bonita") || filial.equals("LOMA BONITA")) && contador < 2) {
            CrearDialog(roota);
            list.add("Loma Bonita");
            list.add("Loma Bonita Toma");

        } else if ((filial.equals("Hospital Los Pilares") || filial.equals("HOSPITAL LOS PILARES")) && contador < 2) {
            CrearDialog(roota);
            list.add("Hospital Los Pilares");
            list.add("Hospital Los Pilares Toma 18");

        } else if ((filial.equals("Acajete") || filial.equals("ACAJETE")) && contador < 2) {
            CrearDialog(roota);
            list.add("Acajete");
            list.add("Acajete Toma");

        } else if ((filial.equals("Huamantla") || filial.equals("HUAMANTLA")) && contador < 2) {
            CrearDialog(roota);
            list.add("Huamantla");
            list.add("Huamantla El Carmen");

        } else if ((filial.equals("Tecamachalco") || filial.equals("TECAMACHALCO")) && contador < 2) {
            CrearDialog(roota);
            list.add("Tecamachalco Sonolabs");
            list.add("Tecamachalco Hospital de Jesus");
            list.add("Tecamachalco Hospital de Jesus Toma");
            list.add("Tecamachalco Hospital de Jesus RX");

        } else if (filial.equals("NNOVAMEDIC") && contador < 2) {
            CrearDialog(roota);
            list.add("Nnovamedic Laboratorio");
            list.add("Nnovamedic RX");

        } else if ((filial.equals("Santa Maria") || filial.equals("SANTA MARIA")) && contador < 2) {
            CrearDialog(roota);
            list.add("Santa María Microbiología");
            list.add("Santa Maria Preparacion de Medios");
        }

        return root;
    }

    private void Consulta(String URL){
        JsonArrayRequest Array = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(Array);
    }

    private void Consulta2(String URL){
        JsonArrayRequest Array = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    JSONObject object = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            object = response.getJSONObject(i);
                            total = object.getInt("numero");
                            if (total == 1) {
                                navigationView.getMenu().findItem(R.id.nav_qr).setVisible(true);
                            } else if (total == 2) {
                                navigationView.getMenu().findItem(R.id.nav_mantenimiento).setVisible(true);
                            } else if (total == 3) {
                                navigationView.getMenu().findItem(R.id.nav_unidadesa).setVisible(true);
                            }

                        } catch (JSONException c) {

                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(Array);
    }

    private void CrearDialog(View roota){

        list = new ArrayList<String>();
        list.add(0,"-Seleccione-");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SUnidad.setAdapter(adapter);
        SUnidad.setOnItemSelectedListener(this);

        new AlertDialog.Builder(getActivity())
                .setView(roota)
                .setTitle("Seleccione una Unidad")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0){
            String unidad = parent.getItemAtPosition(position).toString();
            filial = unidad;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}