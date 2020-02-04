package com.example.appclnica;

import android.content.Context;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private Context ctx;
    private List<MantConstructor>ListMant;
    CardView cdw1;
   ConstraintLayout expandableView;
    Button btn;

    public Adapter(ArrayList<String> lista) {
        this.lista = lista;
    }

    ArrayList<String>lista;


    public Adapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener listener;

    public Adapter(Context ctx, List<MantConstructor> listMant) {
        this.ctx = ctx;
        ListMant = listMant;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from( ctx );
        View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.activitycard2,null,false );
        cdw1=(CardView)view.findViewById( R. id.cardView);
       expandableView =view.findViewById( R.id.expandableView );
        /*btn= view.findViewById( R.id.BTNARROW );
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cdw1, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    btn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cdw1, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    btn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        } );*/

        LinearLayoutManager lm=new LinearLayoutManager( ctx );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MantConstructor mants=ListMant.get( position );
        holder.name.setText(mants.getNombre());
        holder.tipo.setText(mants.getTipo());
        holder.fecha.setText(mants.getFecha());
        holder.name.setTextColor( Color.BLACK );
        holder.tipo.setTextColor( Color.BLACK );
        holder.fecha.setTextColor( Color.BLACK );

    }

    @Override
    public int getItemCount() {
        return ListMant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,tipo,fecha,sucursal,area,empresa,id;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            name=itemView.findViewById( R.id.TXTNON);
            tipo=itemView.findViewById( R.id.desc);
            fecha=itemView.findViewById( R.id.TXTMANTTIPO);
            fecha.setTextColor(Color.BLACK );
            name.setTextColor(Color.BLACK );
            tipo.setTextColor(Color.BLACK );
        }


    }






}
