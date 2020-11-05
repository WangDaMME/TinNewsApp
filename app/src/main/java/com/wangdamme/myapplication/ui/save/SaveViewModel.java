package com.wangdamme.myapplication.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.wangdamme.myapplication.model.Article;
import com.wangdamme.myapplication.repository.NewsRepository;

import java.util.List;

public class SaveViewModel extends ViewModel
{

    private final NewsRepository repository;

    public SaveViewModel(NewsRepository repository)
    {
        this.repository=repository;
    }

    public LiveData<List<Article>> getAllSavedArticles()
    {
        return repository.getAllSavedArticles(); // from db
    }

    public void deleteSavedArticle(Article article)
    {
        repository.deleteSavedArticle(article);
    }
}
