package com.example.ejemploroomexamen;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MedidasAdapter extends RecyclerView.Adapter<MedidasAdapter.MedidaViewHolder> {

    private Context mCtx;
    private List<Medida> mList;

    public MedidasAdapter(Context mCtx, List<Medida> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    @Override
    public MedidasAdapter.MedidaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_medida, parent, false);
        return new MedidaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedidaViewHolder holder, int position) {

        Medida medida = mList.get(position);
        holder.textViewfecha.setText(medida.getFecha());

        holder.textViewGrasa.setText(Integer.toString(medida.getGrasa()));
        holder.textViewMasaMuscular.setText(Integer.toString(medida.getMasaMuscular()));
        holder.textViewPeso.setText(Integer.toString(medida.getPeso()));
        holder.textViewEdadMetabolica.setText(Integer.toString(medida.getEdadMetabolica()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MedidaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewfecha, textViewGrasa, textViewMasaMuscular, textViewPeso, textViewEdadMetabolica;

        public MedidaViewHolder(View itemView) {
            super(itemView);

            textViewfecha = itemView.findViewById(R.id.textViewfecha);
            textViewGrasa = itemView.findViewById(R.id.textViewGrasa);
            textViewMasaMuscular = itemView.findViewById(R.id.textViewMasaMuscular);
            textViewPeso = itemView.findViewById(R.id.textViewPeso);
            textViewEdadMetabolica = itemView.findViewById(R.id.textViewEdadMetabolica);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Medida medida = mList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateMedidaActivity.class);
            intent.putExtra("medida", medida);

            mCtx.startActivity(intent);

        }
    }
}
