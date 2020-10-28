package com.wangdamme.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.os.strictmode.NonSdkApiUsedViolation;
import android.util.Log;  // use System Log to show system message while you debug.

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.wangdamme.myapplication.model.NewsResponse;
import com.wangdamme.myapplication.network.NewsApi;
import com.wangdamme.myapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    // jetpack Navigation

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // draw activity_main.xml View

        BottomNavigationView navView = findViewById(R.id.nav_view); // find BottomnavigationView
        //1. found navHostFragment which is the fragment View (defined: nav_graph)
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //2. get the control_right from Fragment
        navController = navHostFragment.getNavController();
        //3. pass to BottombarNavigation view component
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

        NewsApi newsApi = RetrofitClient.newInstance(this).create(NewsApi.class);
        newsApi.getTopHeadlines("US").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if(response.isSuccessful())
                {
                    Log.d("getTopHeadlines",response.body().toString());
                }
                else
                {
                    Log.d("getTopHeadlines",response.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("getTopHeadlines",t.toString());// print error
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp()
    {

        return navController.navigateUp();
    }



}