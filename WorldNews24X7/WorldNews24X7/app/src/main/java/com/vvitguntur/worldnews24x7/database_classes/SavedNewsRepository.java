package com.vvitguntur.worldnews24x7.database_classes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class SavedNewsRepository
{
    private final SavedNewsDao mSavedNewsDao;
    private final LiveData<List<SavedNews>> mAllSavedNews;

    SavedNewsRepository(Application application)
    {
        SavedNewsDatabase db = SavedNewsDatabase.getDatabase(application);
        mSavedNewsDao = db.savedNewsDao();
        mAllSavedNews = mSavedNewsDao.getAll();
    }
    LiveData<List<SavedNews>> getAllSavedNews() {
        return mAllSavedNews;
    }

    public void delete(SavedNews savedNews){
        new deleteAsyncTask(mSavedNewsDao).execute(savedNews);
    }


    public void insert (SavedNews savedNews) {
        new insertAsyncTask(mSavedNewsDao).execute(savedNews);
    }

    private static class insertAsyncTask extends AsyncTask<SavedNews, Void, Void> {

        private final SavedNewsDao mAsyncTaskDao;

        insertAsyncTask(SavedNewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SavedNews... savedNews) {
            mAsyncTaskDao.insert(savedNews[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<SavedNews, Void, Void> {

        private final SavedNewsDao mAsyncTaskDao;

        deleteAsyncTask(SavedNewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SavedNews... savedNews) {
            mAsyncTaskDao.delete(savedNews[0]);
            return null;
        }
    }
}
