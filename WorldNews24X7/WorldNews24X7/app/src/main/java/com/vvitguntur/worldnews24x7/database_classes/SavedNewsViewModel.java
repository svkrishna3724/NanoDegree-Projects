package com.vvitguntur.worldnews24x7.database_classes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class SavedNewsViewModel extends AndroidViewModel
{

    private final SavedNewsRepository mSavedNewsRepository;
    private final LiveData<List<SavedNews>> mAllSavedNews;
    public SavedNewsViewModel(@NonNull Application application)
    {
        super(application);
        mSavedNewsRepository = new SavedNewsRepository(application);
        mAllSavedNews = mSavedNewsRepository.getAllSavedNews();
    }

    public LiveData<List<SavedNews>> getAllSavedNews()
    {
        return mAllSavedNews;
    }

    public void insert(SavedNews savedNews){
        mSavedNewsRepository.insert(savedNews);
    }
    public void delete(SavedNews savedNews){
        mSavedNewsRepository.delete(savedNews);
    }

}
