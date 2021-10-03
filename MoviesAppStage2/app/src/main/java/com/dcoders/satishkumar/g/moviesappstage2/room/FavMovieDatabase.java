package com.dcoders.satishkumar.g.moviesappstage2.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;

import java.util.List;


@Database(entities = {Result.class}, version = 1, exportSchema = false)
public abstract class FavMovieDatabase extends RoomDatabase {

    public abstract FavMoviedao favMoviedao();

    private static FavMovieDatabase INSTANCE;

    public static FavMovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavMovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavMovieDatabase.class, "favourites.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, LiveData<List<Result>>> {

        private final FavMoviedao mDao;

        PopulateDbAsync(FavMovieDatabase db) {
            mDao = db.favMoviedao();
        }


        @Override
        protected LiveData<List<Result>> doInBackground(Void... voids) {
            return mDao.getlivedataMovies();
        }
    }

}
