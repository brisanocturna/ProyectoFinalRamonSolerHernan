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
        View itemview= LayoutInflater.from(context).inflate(R.layout.item_noticia,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        for (Comentarios n: comentarios) {
            holder.autor.setText(n.getAutor().getNick());
            holder.autor.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.titulo.setText(n.getTitulo());
            holder.titulo.setTextColor(context.getResources().getColor(R.color.colorText));
            SimpleDateFormat temp = new SimpleDateFormat("dd-mm-yyyy");
            holder.fecha.setText(temp.format(n.getFechaUpdate()));
            holder.fecha.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.noticia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MostrarNoticiaActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable("NOTICIA",noticias.get(position));
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            //holder.imagen.setImageDrawable();
        }
    }

    @Override
    public int getItemCount() {
        return this.comentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //TODO HACER Y CAMBIAR EL ITEM COMENTARIO
        LinearLayout noticia;
        TextView autor,titulo,fecha;
        ImageView imagen;
        public ViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.txtItemNoticiaAutor);
            titulo = itemView.findViewById(R.id.txtItemNoticiaTitulo);
            fecha = itemView.findViewById(R.id.txtItemNoticiaFecha);
            imagen = itemView.findViewById(R.id.imgItemNoticia);
            noticia = itemView.findViewById(R.id.ItemNoticia);
        }
    }
}
