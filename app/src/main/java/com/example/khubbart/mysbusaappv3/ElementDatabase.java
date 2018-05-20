package com.example.khubbart.mysbusaappv3;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {Element.class}, version = 1)
public abstract class ElementDatabase extends RoomDatabase {

    public abstract ElementDao elementDao();

    private static ElementDatabase INSTANCE;

    static ElementDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ElementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                    /*INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElementDatabase.class, "element_table")
                            .build();*/
                }
            }
        }
        return INSTANCE;
    }

    private static ElementDatabase buildDatabase (final Context context){
        return Room.databaseBuilder(context,
                ElementDatabase.class,
                "element_table")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).elementDao().insertAllElements(Element.populateElementTable());
                            }
                        });
                    }
                })
                .build();
    }
}
