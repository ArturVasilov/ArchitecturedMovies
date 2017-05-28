package ru.gdg.arturvasilov.popularmovies.data.movies;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class MoviesResponse {

    @SerializedName("results")
    private List<Movie> movies;

    @NonNull
    public List<Movie> getMovies() {
        if (movies == null) {
            return new ArrayList<>();
        }
        return movies;
    }

}
