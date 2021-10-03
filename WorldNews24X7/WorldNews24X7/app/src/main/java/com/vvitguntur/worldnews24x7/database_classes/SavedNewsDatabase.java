package com.vvitguntur.worldnews24x7.database_classes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {SavedNews.class},version = 1,exportSchema = false)
public abstract class SavedNewsDatabase extends RoomDatabase
{
    public abstract SavedNewsDao savedNewsDao();
    private static volatile SavedNewsDatabase INSTANCE;

    static SavedNewsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SavedNewsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SavedNewsDatabase.class, "saved_news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
