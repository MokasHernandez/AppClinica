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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

public class MantenimientoFragment extends Fragment {

    FloatingActionButton addMantenancebtn;
    DatePickerDialog fechaMantenimiento;
    JSONObject requestSaveMantenimiento;
    JSONObject requestGetMantenimiento;
    JSONObject equipo;
    RequestQueue queue;
    String url;
    AlertDialog infoMantDialog;
    AlertDialog.Builder typeMantDialog;
    ArrayList<MantConstructor> listaMantenimientos;
    RecyclerView recyclerMantenimientos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        final View view= inflater.inflate(R.layout.fragment_mantenimiento, container, false);
        return  view;
    }

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
