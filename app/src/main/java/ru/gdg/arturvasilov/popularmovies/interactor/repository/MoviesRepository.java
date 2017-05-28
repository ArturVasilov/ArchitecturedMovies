package ru.gdg.arturvasilov.popularmovies.interactor.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;
import ru.gdg.arturvasilov.popularmovies.data.movies.MoviesResponse;
import ru.gdg.arturvasilov.popularmovies.data.database.dao.MoviesDao;
import ru.gdg.arturvasilov.popularmovies.interactor.api.MoviesService;

/**
 * @author Artur Vasilov
 */
public class MoviesRepository {

    @NonNull
    private final MoviesService service;

    @NonNull
    private final MoviesDao moviesDao;

    public MoviesRepository(@NonNull MoviesService service, @NonNull MoviesDao moviesDao) {
        this.service = service;
        this.moviesDao = moviesDao;
    }

    @NonNull
    public Observable<List<Movie>> popularMovies() {
        return service.popularMovies()
                .map(MoviesResponse::getMovies)
                .doOnNext(movies -> {
                    List<Movie> currentMovies = moviesDao.movies();
                    moviesDao.clear(currentMovies);
                    moviesDao.saveAll(movies);
                })
                .onErrorResumeNext(throwable -> {
                    List<Movie> movies = moviesDao.movies();
                    return Observable.just(movies);
                });
    }
}
