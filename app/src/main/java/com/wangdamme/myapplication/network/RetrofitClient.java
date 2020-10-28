package com.wangdamme.myapplication.network;

import android.content.Context;

import com.ashokvarma.gander.GanderInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This class is responsible for providing a configured Retrofit instance,
//that can then instantiate a NewsApi implementation.
public class RetrofitClient {

    private static final String API_KEY = "69f0c8f29d2b4d819b4b7f94f0df2968";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    // return a Retrofit
    //Call - NewsApi api = RetrofitClient.newInstance(this).create(NewsApi.class);
    public static Retrofit newInstance(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new GanderInterceptor(context).showNotification(true))
                .build();
        //Retrofit retrofit = new xxx
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();  // Gson adapter. This is to tell how a JSON response can be deserialized into model classes.


    }

    // headerInterceptor:A header interceptor. You can attach custom or standard header information to all requests.
    //Interceptors are a powerful mechanism that can monitor, rewrite, and retry call
    private static class HeaderInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request original = chain.request();
            Request request = original.newBuilder().header("X-Api-Key",API_KEY).build();

            return chain.proceed(request); //producing a response to satisfy the request
        }
    }

}

