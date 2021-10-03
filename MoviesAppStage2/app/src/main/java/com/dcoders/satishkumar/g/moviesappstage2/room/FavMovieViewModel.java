package com.dcoders.satishkumar.g.moviesappstage2.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;

import java.util.List;

public class FavMovieViewModel extends AndroidViewModel
{
    private FavMoviesRepository mRepository;
    private LiveData<List<Result>> mAllResults;
    Result result;

    public FavMovieViewModel (Application application)
    {
        super(application);
        mRepository = new FavMoviesRepository(application);
        mAllResults = mRepository.getAllResults();
    }

    public LiveData<List<Result>> getAllResults() { return mAllResults; }
    public Result checkMovieInDatabase(String id)
    {
        result = mRepository.checkMovieInDatabase(id);
        return result;
    }
    public void insert(Result result) { mRepository.insert(result); }
    public void delete(Result result) { mRepository.delete(result); }
}
