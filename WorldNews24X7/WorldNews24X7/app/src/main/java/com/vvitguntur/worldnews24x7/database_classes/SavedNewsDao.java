package com.vvitguntur.worldnews24x7.database_classes;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SavedNewsDao
{
    @Insert
    void insert(SavedNews savedNews);

    @Delete
    void delete(SavedNews savedNews);

    @Query("select * from savednews")
    LiveData<List<SavedNews>> getAll();
}
