package com.wangdamme.myapplication;

import android.app.Application;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;

//Application class
public class TinNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Gander.setGanderStorage(GanderIMDB.getInstance());
    }
}
