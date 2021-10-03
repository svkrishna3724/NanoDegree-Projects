package com.dcoders.satishkumar.g.moviesappstage2.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;

import java.util.List;

public class FavMoviesRepository {


    private FavMoviedao mFavMovieDao;
    private LiveData<List<Result>> mAllresults;

    FavMoviesRepository(Application application) {
        FavMovieDatabase db = FavMovieDatabase.getDatabase(application);
        mFavMovieDao = db.favMoviedao();
        mAllresults = mFavMovieDao.getlivedataMovies();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Result>> getAllResults() {
        return mAllresults;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Result result) {
        new insertAsyncTask(mFavMovieDao).execute(result);
    }
    public void delete (Result result) {
        new deleteAsyncTask(mFavMovieDao).execute(result);
    }
    Result checkMovieInDatabase(String id)
    {
       Result result =  mFavMovieDao.checkMovieInDatabase(id);
       return result;
    }
    private static class insertAsyncTask extends AsyncTask<Result, Void, Void> {

        private FavMoviedao mAsyncTaskDao;

        insertAsyncTask(FavMoviedao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Result... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Result, Void, Void> {

        private FavMoviedao mAsyncTaskDao;

        deleteAsyncTask(FavMoviedao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Result... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
