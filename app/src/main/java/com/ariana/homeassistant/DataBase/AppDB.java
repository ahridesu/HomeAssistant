package com.ariana.homeassistant.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Placement;
import com.ariana.homeassistant.model.Room;
import com.ariana.homeassistant.model.Routine;

@Database(entities = {Room.class , Routine.class , Device.class , Placement.class},version = 2,exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    private static AppDB database;

    private static String databaseName = "database";

    public synchronized static AppDB getInstance(Context context){
        if(database == null){
            database  = androidx.room.Room.databaseBuilder(context.getApplicationContext(),AppDB.class,databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract mainDao mainDao();
}
