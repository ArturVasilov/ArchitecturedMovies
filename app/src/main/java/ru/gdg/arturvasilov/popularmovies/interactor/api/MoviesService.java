package ru.gdg.arturvasilov.popularmovies.interactor.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.gdg.arturvasilov.popularmovies.data.movies.MoviesResponse;

/**
 * @author Artur Vasilov
 */
public interface MoviesService {

    @GET("popular/")
    Observable<MoviesResponse> popularMovies();
}
