package com.wangdamme.myapplication.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wangdamme.myapplication.TinNewsApplication;
import com.wangdamme.myapplication.database.TinNewsAppDatabase;
import com.wangdamme.myapplication.model.Article;
import com.wangdamme.myapplication.model.NewsResponse;
import com.wangdamme.myapplication.network.NewsApi;
import com.wangdamme.myapplication.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// In Repository, SET LiveData
// *** Repository Tier***
// User retrofit to access from the remote end get data, return LivedData

public class NewsRepository {

    private final NewsApi newsApi; // retrofit interface

    //Add database access to NewsRepository
    private final TinNewsAppDatabase database;

    public NewsRepository(Context context)
    {
        //NewsApi newsApi = RetrofitClient.newInstance(this).create(NewsApi.class);
        newsApi= RetrofitClient.newInstance(context).create(NewsApi.class);
        database= ((TinNewsApplication) context.getApplicationContext()).getDatabase(); // cast the application context into TinNewsApplicaition
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


    // HomeView Model will call setFavoriteArticleInput(Article article)
    public LiveData<Boolean> favoriteArticle(Article article)
    {
        MutableLiveData<Boolean>  resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database,resultLiveData).execute(article);  // execute returns immediately,  db operation runs in background & will notify the result thru {resultLiveData}

        return  resultLiveData;
    }

    //*********** AyncTask ************//


    // Create AsyncTask to get Favorite to dispatch querry to a backround thread
    private static class FavoriteAsyncTask extends AsyncTask<Article,Void,Boolean> // Generic Type Input Param,Progress,Result
    {
        private final TinNewsAppDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsAppDatabase database, MutableLiveData<Boolean> liveData)
        {
            this.database=database;
            this.liveData=liveData;
        }

        // evething thing in doInBackground will be executed on a separate background thread.
        @Override
        protected Boolean doInBackground(Article... articles) //Params
        {

            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            }
            catch (Exception e)
            {
                return false;
            }
            return true;
        }

        //After done doinBackground, onPostExecute will be executed back in MAIN Thread
        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }

    // for SaveFragment
    public LiveData<List<Article>> getAllSavedArticles()
    {
        return database.articleDao().getAllArticles();// return all database article info
    }

    public void deleteSavedArticle(Article article)
    {
        AsyncTask.execute( ()-> database.articleDao().deleteArticle(article)); // you dont care when it actually deletes
    }


}
