package com.example.appclnica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlmacenFragment extends Fragment {

    private TextView Prueba;
    private Button btnEntrada;
    private Button btnSalida;
    private Button btnInicio;
    private Button btnFin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_almacen, container, false);

        btnEntrada = root.findViewById(R.id.BTN1 );
        btnSalida = root.findViewById(R.id.BTN2 );
        btnInicio = root.findViewById(R.id.BTN3 );
        btnFin = root.findViewById(R.id.BTN4 );
        Prueba = root.findViewById(R.id.text_tools);
        Prueba.setText("Módulo de Almacén");

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Entrada = new Intent(getActivity().getApplicationContext(), ActivityAlmacenE.class);
                startActivity(Entrada);
            }
        });

        btnSalida.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Salida = new Intent(getActivity().getApplicationContext(), ActivityAlmacenS.class);
                startActivity(Salida);
            }
        });

        btnInicio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Llamada();
            }
        });

        return root;
    }


    public void Llamada(){
        Intent QR = new Intent(getActivity().getApplicationContext(), MainQR.class);
        startActivity(QR);
    }

    public static class AdapterT extends RecyclerView.Adapter<AdapterT.ViewHolder> {

        private Context
                Ctx;
        CardView
                CDW3;

        public AdapterT(AdapterView.OnItemClickListener mListener) {
            this.mListener = mListener;
        }

        private AdapterView.OnItemClickListener  mListener;
        public interface OnItemClickListener {
            void onItemClick(int position);
        }



        public AdapterT(Context ctx, ArrayList<Termos> TL) {
            Ctx = ctx;
            this.TL = TL;
        }

        private ArrayList<Termos> TL;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(Ctx);
            View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.actcardter, null,false);
            CDW3=view.findViewById( R.id.cardViewERR );
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Termos title =TL.get(position);
            holder.tip.setText( title.getAREA() );
            holder.nam.setText( title.getNOMBRE() );
        }

        @Override
        public int getItemCount() {
            return TL.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nam,tip;

            public ViewHolder(@NonNull View itemView) {
                super( itemView );
                nam=itemView.findViewById( R.id.TXTNON2)  ;
                 tip=itemView.findViewById( R.id.TXT22 )  ;

                itemView.setOnClickListener( new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent I =new Intent( v.getContext() ,InsertaTT.class );
                        I.putExtra("nam",nam.getText() );
                        I.putExtra( "tip", tip.getText() );
                        v.getContext().startActivity( I );
                    }
                } );
            }
        }
    }
}