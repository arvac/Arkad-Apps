package com.example.irir1.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irir1.myapplication.R;
import com.example.irir1.myapplication.api.APIService;
import com.example.irir1.myapplication.api.APIUrl;
import com.example.irir1.myapplication.fragments.MessageFragment;

import com.example.irir1.myapplication.helper.SharedPrefManager;
import com.example.irir1.myapplication.models.Messages;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewName;
    String id_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

///llama id en el sharepreference
        Call<Messages> call = service.getMessages(SharedPrefManager.getInstance(this).getUser().getId());
        int id =SharedPrefManager.getInstance(this).getUser().getId();
        id_string = Integer.toString(id);

        Toast toast1 =    Toast.makeText(getApplicationContext(),
                id_string, Toast.LENGTH_SHORT);

        toast1.show();


        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }


        View headerView = navigationView.getHeaderView(0);
        textViewName = (TextView) headerView.findViewById(R.id.textViewName);
        textViewName.setText(SharedPrefManager.getInstance(this).getUser().getName());

        //loading home fragment by default
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = new MessageFragment();
                break;
            case R.id.nav_profile:
             //   fragment = new ProfileFragment();
                break;
            case R.id.nav_messages:
                fragment = new MessageFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }
}
