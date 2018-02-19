package com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ramon.proyectofinalramonsolerhernan.MostrarNoticiaActivity;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Comentarios;
import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;
import com.example.ramon.proyectofinalramonsolerhernan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ramon on 14/02/2018.
 */

public class AdpaterComentarios extends RecyclerView.Adapter<AdpaterComentarios.ViewHolder>{

    Context context;
    ArrayList<Comentarios> comentarios;

    public AdpaterComentarios(Context context, ArrayList<Comentarios> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.item_comentario,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        for (Comentarios c: comentarios) {
            holder.contenido.setText(comentarios.get(position).getContenido());
            holder.autor.setText(comentarios.get(position).get());

        }
    }

    @Override
    public int getItemCount() {
        return this.comentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //TODO HACER Y CAMBIAR EL ITEM COMENTARIO
        LinearLayout comentario;
        TextView autor,contenido,fecha, titulo;
        ImageView imagendelete, imagenedit;
        public ViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.txtItemComentarioAutor);
            //titulo = itemView.findViewById(R.id.txtItemC);
            contenido = itemView.findViewById(R.id.txtItemComentario);
            fecha = itemView.findViewById(R.id.txtItemComentarioFecha);
            imagendelete = itemView.findViewById(R.id.imgItemComentarioDelete);
            imagenedit = itemView.findViewById(R.id.imgItemComentarioEdit);
            comentario = itemView.findViewById(R.id.itemComentario);
        }
    }
}
