package com.wangdamme.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wangdamme.myapplication.model.Article;

import java.util.List;

// Dao contains the methods to access database

@Dao
public interface ArticleDao {

    @Insert
    void saveArticle(Article article);

    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllArticles();

    @Delete
    void deleteArticle(Article article);

}
