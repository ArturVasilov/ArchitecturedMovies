package ru.gdg.arturvasilov.popularmovies.ui.screen.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;
import ru.gdg.arturvasilov.popularmovies.data.response.Response;
import ru.gdg.arturvasilov.popularmovies.interactor.repository.MoviesRepository;

/**
 * @author Artur Vasilov
 */
@SuppressWarnings("WeakerAccess")
public class MoviesViewModel extends ViewModel {

    @NonNull
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    @NonNull
    private final MoviesRepository moviesRepository;

    @Nullable
    private MutableLiveData<Response<List<Movie>>> moviesLiveData;

    public MoviesViewModel(@NonNull MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @NonNull
    LiveData<Boolean> isLoading() {
        return loadingLiveData;
    }

    @MainThread
    @NonNull
    LiveData<Response<List<Movie>>> getMoviesList() {
        if (moviesLiveData == null) {
            moviesLiveData = new MutableLiveData<>();
            moviesRepository.popularMovies()
                    .map(movies -> {
                        Collections.sort(movies, (first, second) -> second.getId() - first.getId());
                        return movies;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> loadingLiveData.setValue(true))
                    .doAfterTerminate(() -> loadingLiveData.setValue(false))
                    .subscribe(
                            movies -> moviesLiveData.setValue(Response.success(movies)),
                            throwable -> moviesLiveData.setValue(Response.error(throwable))
                    );
        }
        return moviesLiveData;
    }
}
