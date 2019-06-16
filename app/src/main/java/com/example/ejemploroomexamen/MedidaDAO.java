package com.example.ejemploroomexamen;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedidaDAO {

    @Query("SELECT * FROM medida order by fecha DESC")
    List<Medida> getAll();

    @Insert
    void insert(Medida medida);

    @Delete
    void delete(Medida medida);

    @Update
    void update(Medida medida);


}
