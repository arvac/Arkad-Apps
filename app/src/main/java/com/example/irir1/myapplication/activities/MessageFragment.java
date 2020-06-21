package com.example.irir1.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irir1.myapplication.R;
import com.example.irir1.myapplication.api.APIService;
import com.example.irir1.myapplication.api.APIUrl;
import com.example.irir1.myapplication.helper.MessageAdapter;
import com.example.irir1.myapplication.helper.SharedPrefManager;
import com.example.irir1.myapplication.models.Messages;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.irir1.myapplication.models.Users;

/**
 * Created by Belal on 14/04/17.
 */

public class MessageFragment extends AppCompatActivity {

    private RecyclerView recyclerViewMessages;
    private RecyclerView.Adapter adapter;
    TextView id;Button bu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_messages);
         id = (TextView) findViewById(R.id.id);
        bu = (Button) findViewById(R.id.bu);
        recyclerViewMessages = (RecyclerView)findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
         Toast.makeText(getApplicationContext(),
                "Toast por defecto", Toast.LENGTH_SHORT);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

///llama id en el sharepreference
        Call<Messages> call = service.getMessages(SharedPrefManager.getInstance(this).getUser().getId());



        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                adapter = new MessageAdapter(response.body().getMessages(),MessageFragment.this);
                recyclerViewMessages.setAdapter(adapter);
                id.setText("valor");
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(MessageFragment.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("valor");
                Toast toast1 =    Toast.makeText(getApplicationContext(),
                        "Toast por defecto", Toast.LENGTH_SHORT);

                toast1.show();

            }
        });
    }
}
