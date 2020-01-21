package com.example.appclnica.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RequestQueue requestQueue;
    private String ID;
    private String Message;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            Message = bundle.getString("Mensaje");
            ID = bundle.getString("ID");
        }

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                    textView.setText("Bienvenido " + Message);
            }
        });

        Login("https://asesoresconsultoreslabs.com/App_Android/Notificacion_Corres.php?ID_Usuario="+ID+"");
        return root;
    }

    private void Login(String URL){
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