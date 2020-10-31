package com.wangdamme.myapplication.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wangdamme.myapplication.model.NewsResponse;
import com.wangdamme.myapplication.repository.NewsRepository;

//  *** ViewModel ***
//  exposes streams of data relevant to the View
// when repository get data --> View Model expose to --> View.xml


//2. things. 1. After user done input --> set (String) search_input as LiveData to be exposed to View



public class SearchViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SearchViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }

    public LiveData<NewsResponse> searchNews() {
        // when serachinput is changed, callback repsoitory::serachNews func which returns //    public LiveData<NewsResponse> searchNews(String query)
        return Transformations.switchMap(searchInput, repository::searchNews); // repository: has retrofit client to call remote
    }

}
