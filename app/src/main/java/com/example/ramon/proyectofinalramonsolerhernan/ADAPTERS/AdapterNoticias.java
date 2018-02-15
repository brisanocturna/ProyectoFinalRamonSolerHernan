package com.example.ramon.proyectofinalramonsolerhernan.ADAPTERS;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramon.proyectofinalramonsolerhernan.POJOS.Noticias;
import com.example.ramon.proyectofinalramonsolerhernan.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ramon on 14/02/2018.
 */

public class AdapterNoticias extends RecyclerView.Adapter<AdapterNoticias.ViewHolder>{

    Context context;
    ArrayList<Noticias> noticias;

    public AdapterNoticias(Context context, ArrayList<Noticias> noticias) {
        this.context = context;
        this.noticias = noticias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.item_noticia,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (Noticias n: noticias) {
            holder.autor.setText(n.getAutor().getNick());
            holder.autor.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.titulo.setText(n.getTitulo());
            holder.titulo.setTextColor(context.getResources().getColor(R.color.colorText));
            SimpleDateFormat temp = new SimpleDateFormat("dd-mm-yyyy");
            holder.fecha.setText(temp.format(n.getFechaUpdate()));
            holder.fecha.setTextColor(context.getResources().getColor(R.color.colorText));
            //holder.imagen.setImageDrawable();
        }
    }

    @Override
    public int getItemCount() {
        return this.noticias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView autor,titulo,fecha;
        ImageView imagen;
        public ViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.txtItemNoticiaAutor);
            titulo = itemView.findViewById(R.id.txtItemNoticiaTitulo);
            fecha = itemView.findViewById(R.id.txtItemNoticiaFecha);
            imagen = itemView.findViewById(R.id.imgItemNoticia);
        }
    }
}
