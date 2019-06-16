package com.example.ejemploroomexamen;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClientMedida {
    private Context mCtx;
    private static DatabaseClientMedida mInstance;

    //our app database object
    private AppDatabaseMedida appDatabaseMedida;

    private DatabaseClientMedida(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabaseMedida = Room.databaseBuilder(mCtx, AppDatabaseMedida.class, "MyToDosMedida").build();
    }

    public static synchronized DatabaseClientMedida getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClientMedida(mCtx);
        }
        return mInstance;
    }

    public AppDatabaseMedida getAppDatabaseMedia() {
        return appDatabaseMedida;
    }
}
