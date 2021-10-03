package com.dcoders.satishkumar.g.moviesappstage2.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface FavMoviedao
{
    @Insert(onConflict = REPLACE)
    void insert(Result favMovie);

    @Delete
    void delete(Result favMovie);

    @Query("SELECT * FROM Result")
    LiveData<List<Result>> getlivedataMovies();

    @Query("SELECT * FROM Result WHERE id = :id LIMIT 1")
    Result checkMovieInDatabase(String id);
}
