package com.example.khubbart.mysbusaappv3;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {Element.class}, version = 1, exportSchema = false)
public abstract class ElementDatabase extends RoomDatabase {

    public abstract ElementDao elementDao();

    private static ElementDatabase INSTANCE;

    public static ElementDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ElementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElementDatabase.class,
                            "element_table")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

   /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ElementDao mDao;

        PopulateDbAsync(ElementDatabase db) {
            mDao = db.elementDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAllElements();

            Element element = new Element("1S", "Single Salchow");
            mDao.insertElement(element);
            element = new Element("1F", "Single Flip");
            mDao.insertElement(element);
            element = new Element("1A", "Single Axel");
            mDao.insertElement(element);

            return null;
        }
    }
}
