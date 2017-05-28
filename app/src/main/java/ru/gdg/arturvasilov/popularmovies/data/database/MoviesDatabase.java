package ru.gdg.arturvasilov.popularmovies.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;
import ru.gdg.arturvasilov.popularmovies.data.database.dao.MoviesDao;

/**
 * @author Artur Vasilov
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
