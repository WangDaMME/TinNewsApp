package com.wangdamme.myapplication;

import android.app.Application;

import androidx.room.Room;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.facebook.stetho.Stetho;
import com.wangdamme.myapplication.database.TinNewsAppDatabase;

//Application class
public class TinNewsApplication extends Application {

    private TinNewsAppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Gander.setGanderStorage(GanderIMDB.getInstance());
        Stetho.initializeWithDefaults(this);

        // Access a database
        database = Room.databaseBuilder(this, TinNewsAppDatabase.class, "tinnews_db").build(); //getApplicationContext()
    }

    public TinNewsAppDatabase getDatabase() // singleton
    {
        return database;
    }
}
