package com.example.irir1.myapplication.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.irir1.myapplication.R;
import com.example.irir1.myapplication.activities.MainActivity;
import com.example.irir1.myapplication.models.Message;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

//import com.example.irir1.myapplication.models.MessageResponse;

/**
 * Created by Belal on 14/04/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    String valor,id_clientes;
    private List<Message> messages;
    private Context mCtx;

    public MessageAdapter(List<Message> messages, Context mCtx) {
        this.messages = messages;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_messages, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Message message = messages.get(position)  ;
      valor=Integer.toString(message.getiId_asignar());
       holder.textViewName.setText(valor);
        id_clientes=Integer.toString(message.getiId_clientes());
        holder.textcliente.setText(id_clientes);
        holder.textViewTitle.setText(message.getCod());
        holder.textViewMessage.setText(message.getNom());



        holder.textViewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///---------------------------------------------------------------------------------------------------------------------
                Intent mIntent = new Intent(mCtx, MainActivity.class);
                mIntent.putExtra("id", messages.get(holder.getAdapterPosition()).getiId_asignar());
                mIntent.putExtra("id_clientes_intent", messages.get(holder.getAdapterPosition()).getiId_clientes());
                mIntent.putExtra("nom", messages.get(holder.getAdapterPosition()).getNom());
                mIntent.putExtra("cod", messages.get(holder.getAdapterPosition()).getCod());

                mCtx.startActivity(mIntent);
                ///----------------------------------------------------------------------------------------------------------------------

            }
        });
    }

    public void onViewRecycled(MapsAdapter_test holder) {
        if (holder.mMap != null) {
            holder.mMap.clear();
            holder.mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }

    }
    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textcliente;
        public TextView textViewTitle;
        public TextView textViewMessage;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textcliente = (TextView) itemView.findViewById(R.id.textViewTime);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);

        }
    }

}