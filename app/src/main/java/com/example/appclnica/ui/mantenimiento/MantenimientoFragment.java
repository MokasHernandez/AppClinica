package com.example.appclnica.ui.mantenimiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclnica.MyAdapter;
import com.example.appclnica.R;

import java.util.ArrayList;

public class MantenimientoFragment extends Fragment {

    private RecyclerView Lista;
    private RecyclerView.Adapter adapter;
    private TextView Prueba;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mantenimiento, container, false);

        Prueba = root.findViewById(R.id.text_tools);
        Lista = root.findViewById(R.id.recylcer);
        Prueba.setText("Selecciona una opción");

        String[] contactos = new String[200];
        contactos[0] = "Item1";
        contactos[1] = "Item2";
        contactos[2] = "Item3";
        adapter = new MyAdapter(contactos);
        Lista.setAdapter(adapter);

        return root;
    }
}