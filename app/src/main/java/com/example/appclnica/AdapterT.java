package com.example.appclnica;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterT extends RecyclerView.Adapter<AdapterT.ViewHolder> {

    private Context Ctx;
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
    public AdapterT.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(Ctx);
        View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.actcardter, null,false);
        CDW3=view.findViewById( R.id.cardViewERR );
        return new AdapterT.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterT.ViewHolder holder, int position) {
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

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent I =new Intent( v.getContext(),InsertaTT.class );
                    I.putExtra("nam",nam.getText() );
                    I.putExtra( "tip", tip.getText() );
                    v.getContext().startActivity( I );


                }
            } );
        }
    }
}
