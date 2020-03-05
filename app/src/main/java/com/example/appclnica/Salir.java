package com.example.appclnica;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appclnica.MainActivity;
import com.example.appclnica.R;
import com.example.appclnica.ui.home.HomeFragment;

import org.json.JSONArray;

import static com.example.appclnica.ui.home.HomeFragment.ID;

public class Salir extends Fragment {
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intent intent = new Intent(getContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.contador = 0;
        Update("http://asesoresconsultoreslabs.com/asesores/App_Android/CRUD.php?idt=2&id=" + ID);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void Update(String URL){
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
