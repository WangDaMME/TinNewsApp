package com.wangdamme.myapplication.ui.home;

import android.widget.Adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wangdamme.myapplication.model.Article;
import com.wangdamme.myapplication.model.NewsResponse;
import com.wangdamme.myapplication.repository.NewsRepository;

// *** ViewModel: business tier *** //
// A ViewModel object provides the data for a specific UI component(View), such as a fragment or activity,
// and contains data-handling business logic to communicate with the model.
// For example, the ViewModel can call other components to load the data,
// and it can forward user requests to modify the data.
// The ViewModel doesn't know about UI components,
// so it isn't affected by configuration changes, such as recreating an activity when rotating the device.

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>(); //wrap String_country as A LiveData pipe

    public HomeViewModel(NewsRepository repository)
    {
        this.repository=repository;
    }

    public void setCountryInput(String country)
    {
        countryInput.setValue(country);
    }

    public LiveData<NewsResponse> getTopHeadlines() {

        // when a LiveData<X> value change Trigger the other one LiveData<Y>
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);// java 8 ::e method reference in Java 8
    }

    // add API to save Favorite Async In Room Database
    public void setFavoriteArticleInput(Article article)
    {
        repository.favoriteArticle(article);
        // dont need to do Transformations.switchMap to expose observing result.
    }

}
