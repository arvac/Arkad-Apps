package com.example.irir1.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Belal on 14/04/17.
 */

public class MessageFragment extends Fragment {

    private RecyclerView recyclerViewMessages;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tareas");

        recyclerViewMessages = (RecyclerView) view.findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getActivity()));


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIService service = retrofit.create(APIService.class);


    Call<Messages> call = service.getMessages(SharedPrefManager.getInstance(getActivity()).getUser().getId());



        call.enqueue(new Callback<Messages>() {
        @Override
        public void onResponse(Call<Messages> call, Response<Messages> response) {
            adapter = new MessageAdapter(response.body().getMessages(), getActivity());
            recyclerViewMessages.setAdapter(adapter);
        }

        @Override
        public void onFailure(Call<Messages> call, Throwable t) {
            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    });
}
}
