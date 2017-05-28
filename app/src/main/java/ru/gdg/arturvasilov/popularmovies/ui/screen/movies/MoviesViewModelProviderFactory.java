package ru.gdg.arturvasilov.popularmovies.ui.screen.movies;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import ru.gdg.arturvasilov.popularmovies.data.database.dao.MoviesDao;
import ru.gdg.arturvasilov.popularmovies.interactor.api.MoviesService;
import ru.gdg.arturvasilov.popularmovies.interactor.repository.MoviesRepository;
import ru.gdg.arturvasilov.popularmovies.ui.screen.base.BaseViewModelFactory;

/**
 * @author Artur Vasilov
 */
class MoviesViewModelProviderFactory extends BaseViewModelFactory {

    @NonNull
    private final MoviesRepository repository;

    MoviesViewModelProviderFactory(@NonNull MoviesService moviesService,
                                   @NonNull MoviesDao moviesDao) {
        this.repository = new MoviesRepository(moviesService, moviesDao);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(MoviesViewModel.class)) {
            //noinspection unchecked
            return (T) new MoviesViewModel(repository);
        }
        return super.create(modelClass);
    }
}
