package com.wangdamme.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wangdamme.myapplication.model.Article;

// Create Database of Article_tables
// as database holder and serves as the main access point
// for the underlying connection to app's persistedm relational data.

@Database(entities ={Article.class}, version = 1, exportSchema = false)
public abstract class TinNewsAppDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}
// both abstract: we dont implement this, the Room annotation Processor will.
// entities specify the tables db contains,
// export schema : for dumping a database schema to file system.
// version: modify --> new & define migraiton strategy