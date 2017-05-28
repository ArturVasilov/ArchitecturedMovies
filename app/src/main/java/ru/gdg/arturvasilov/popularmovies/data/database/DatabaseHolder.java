package ru.gdg.arturvasilov.popularmovies.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class DatabaseHolder {

    private static MoviesDatabase database;

    @MainThread
    public static void init(@NonNull Context context) {
        database = Room.databaseBuilder(
                context.getApplicationContext(),
                MoviesDatabase.class,
                "movies-database"
        ).build();
    }

    @NonNull
    public static MoviesDatabase database() {
        return database;
    }
}
