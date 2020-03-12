package com.example.appclnica.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.appclnica.MainActivity;
import com.example.appclnica.NavigationActivity;
import com.example.appclnica.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private int[] mImages = new int[]{
            R.drawable.doctor, R.drawable.htdscaro
    };

    String A="";
    private HomeViewModel homeViewModel;
    private RequestQueue requestQueue;
    private String ID;
    private static String Message;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        final CarouselView carouselView = root.findViewById(R.id.carousel);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            Message = bundle.getString("Mensaje");
            ID = bundle.getString("ID");
        }

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Calendar calendario = Calendar.getInstance();
                int hora, minutos,segundos;
                hora =calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);
                segundos = calendario.get(Calendar.SECOND);
                if(hora>=00&&hora<=11)
                { A="Buenos Dias";textView.setText( A );
                }else
                if(hora >=12&&hora<=18)
                { A="Buenas Tardes"; textView.setText( A );
                }else
                if(hora >=19&&hora<=23)
                { A="Buenas Noches";textView.setText( A );
                }

                textView.setText(A +" : "+ Message);
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
}