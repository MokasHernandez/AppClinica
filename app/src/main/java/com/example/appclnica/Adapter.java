package com.example.appclnica;

import android.content.Context;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Adapter(Context ctx, List<MantConstructor> LC) {
        this.ctx = ctx;
        ListMant = LC;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater inflater=LayoutInflater.from( ctx );
         View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.activitycard2,null,false );
         cdw1=(CardView)view.findViewById( R. id.cardView);
         expandableView =view.findViewById( R.id.expandableView );
       btn= view.findViewById( R.id.BTNARROW );

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
        } );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MantConstructor mants = ListMant.get( position );
        /**************Texview 1*************************/
        holder.name.setText( mants.getNombre() );
        holder.tipo.setText( mants.getTipo() );
        holder.fecha.setText( mants.getFecha() );
        /**************Textview 2*******************************/
        holder.N.setText( mants.getNombre() );
        holder.T.setText( mants.getTipo() );
        holder.F.setText( mants.getFecha() );
        holder.sucursal.setText( mants.getSucursal() );
        holder.empresa.setText( mants.getEmpresa() );
        holder.area.setText( mants.getArea() );
        holder.mantenimiento.setText( mants.getMantenimiento());
        /**************Textview 2*******************************/
        /**************Color Textview*******************************/
        holder.name.setTextColor( Color.BLACK );
        holder.tipo.setTextColor( Color.BLACK );
        holder.fecha.setTextColor( Color.BLACK );

    }

    @Override
    public int getItemCount() {
        return ListMant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,tipo,fecha,sucursal,area,empresa,N,T,F,mantenimiento;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            /**************Texview 1*************************/
            name=itemView.findViewById( R.id.TXTNON);
            tipo=itemView.findViewById( R.id.TXT22 );
            fecha=itemView.findViewById( R.id.TXTMANTTIPO2 );
            /**************Textview 2*******************************/
            N=itemView.findViewById( R.id.TXTN2 );
            T=itemView.findViewById( R.id.TXTPO2 );
            F=itemView.findViewById( R.id.TXTF2 );
            empresa=itemView.findViewById( R.id.EDTEMPRE2 );
            area=itemView.findViewById( R.id.TXTA2 );
            sucursal=itemView.findViewById( R.id.TXTS2 );
            mantenimiento=itemView.findViewById( R.id.TXTC2 );
        }


    }









}
