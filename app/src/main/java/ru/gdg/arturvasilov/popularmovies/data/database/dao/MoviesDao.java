package ru.gdg.arturvasilov.popularmovies.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;

/**
 * @author Artur Vasilov
 */
@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movie")
    List<Movie> movies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Movie> movies);

    @Delete
    void clear(List<Movie> movies);
}
