package com.example.irir1.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.irir1.myapplication.R;
import com.example.irir1.myapplication.models.Message;

import java.util.List;

public class maps extends AppCompatActivity {
    String valor;
    private List<Message> messages;
    private RecyclerView recyclerViewMessages;
    private RecyclerView.Adapter adapter;
TextView id,nom,cod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maps);

        cod = (TextView) findViewById(R.id.id);
        id = (TextView) findViewById(R.id.nombre);
        nom = (TextView) findViewById(R.id.cod);



    }
}
