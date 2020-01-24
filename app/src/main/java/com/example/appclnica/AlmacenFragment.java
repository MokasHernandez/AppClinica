package com.example.appclnica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AlmacenFragment extends Fragment {

    private RecyclerView Lista;
    private RecyclerView.Adapter adapter;
    private TextView Prueba;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_almacen, container, false);

        Prueba = root.findViewById(R.id.text_tools);
        Prueba.setText("Módulo de Almacén");

        return root;
    }
}