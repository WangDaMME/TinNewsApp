package com.wangdamme.myapplication.network;

import com.wangdamme.myapplication.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// http restful api
// To 2 endpoints:  "https://newsapi.org/v2/top-headlines" & "https://newsapi.org/v2/everything"
// api declaration code: HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD.

//@Get("relative_url_path")   @Query(parameter)
public interface NewsApi {
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country);
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=

    @GET("everything")
    Call<NewsResponse> getEverything(@Query("pageSize") int pageSize);
    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=
    //pageSize:The number of results to return per page. 20 is the default, 100 is the maximum.
}


/*
https://square.github.io/retrofit/

public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}
The Retrofit class generates an implementation of the GitHubService interface.

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();

GitHubService service = retrofit.create(GitHubService.class);
 */