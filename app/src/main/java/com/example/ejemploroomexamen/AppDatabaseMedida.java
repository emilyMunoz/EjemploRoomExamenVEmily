package com.example.ejemploroomexamen;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Medida.class}, version = 2)
public abstract class AppDatabaseMedida extends RoomDatabase {
    public abstract MedidaDAO medidaDAO();
}
