package com.wangdamme.myapplication.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wangdamme.myapplication.model.NewsResponse;
import com.wangdamme.myapplication.network.NewsApi;
import com.wangdamme.myapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// In Repository, SET LiveData
// *** Repository Tier***
// User retrofit to access from the remote end get data, return LivedData

public class NewsRepository {

    private final NewsApi newsApi; // retrofit interface

    public NewsRepository(Context context)
    {
        //NewsApi newsApi = RetrofitClient.newInstance(this).create(NewsApi.class);
        newsApi= RetrofitClient.newInstance(context).create(NewsApi.class);
    }

    // Search HeadlINE API:    //https://newsapi.org/v2/top-headlines?country=us&apiKey=

    // news response : is observable data holder info
    public LiveData<NewsResponse> getTopHeadlines(String country)
    {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if(response.isSuccessful())
                {
                    topHeadlinesLiveData.setValue(response.body()); //
                }
                else
                {
                    topHeadlinesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                topHeadlinesLiveData.setValue(null);
            }
        });

        return topHeadlinesLiveData;
    }

    // SearchNews get everything API:       //https://newsapi.org/v2/everything?q=bitcoin&apiKey=

    public LiveData<NewsResponse> searchNews(String query)
    {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        // fixed result #：40
        newsApi.getEverything(query,40).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if(response.isSuccessful())
                {
                    everyThingLiveData.setValue(response.body());
                }
                else
                {
                    everyThingLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t)
            {
                everyThingLiveData.setValue(null);

            }
        });

        return everyThingLiveData;
    }




}
