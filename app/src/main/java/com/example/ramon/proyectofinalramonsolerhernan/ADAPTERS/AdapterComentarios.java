package com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Comentarios;
import com.example.ramon.proyectofinalramonsolerhernan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ramon on 14/02/2018.
 */

public class AdapterComentarios extends RecyclerView.Adapter<AdapterComentarios.ViewHolder>{

    Context context;
    ArrayList<Comentarios> comentarios;

    public AdapterComentarios(Context context, ArrayList<Comentarios> comentarios) {
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
            Comentarios c = comentarios.get(position);
            holder.fecha.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.contenido.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.autor.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.titulo.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.contenido.setText(comentarios.get(position).getContenido());
            holder.autor.setText(comentarios.get(position).getAutor().getNick());
            SimpleDateFormat temp = new SimpleDateFormat("dd-MM-yyyy");
            holder.fecha.setText(temp.format(comentarios.get(position).getFechaUpdate()));
            holder.titulo.setText(comentarios.get(position).getTitulo());
           holder.imagendelete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //TODO cambiar por quitar de la base de datos
                   comentarios.remove(position);
                   AdapterComentarios.this.notifyDataSetChanged();
               }
           });
           holder.imagenedit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //TODO hacer el intent y llevarlo hasta editar comentario
                   Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
               }
           });


    }

    @Override
    public int getItemCount() {
        return this.comentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout comentario;
        TextView autor,contenido,fecha, titulo;
        ImageView imagendelete, imagenedit;
        public ViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.txtItemComentarioAutor);
            titulo = itemView.findViewById(R.id.txtItemComentarioTitulo);
            contenido = itemView.findViewById(R.id.txtItemComentario);
            fecha = itemView.findViewById(R.id.txtItemComentarioFecha);
            imagendelete = itemView.findViewById(R.id.imgItemComentarioDelete);
            imagenedit = itemView.findViewById(R.id.imgItemComentarioEdit);
            comentario = itemView.findViewById(R.id.itemComentario);
        }
    }
}
