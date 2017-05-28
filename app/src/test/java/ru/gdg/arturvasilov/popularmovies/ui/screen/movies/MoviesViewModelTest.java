package ru.gdg.arturvasilov.popularmovies.ui.screen.movies;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;

import io.reactivex.Observable;
import ru.gdg.arturvasilov.popularmovies.data.response.Response;
import ru.gdg.arturvasilov.popularmovies.interactor.repository.MoviesRepository;
import ru.gdg.arturvasilov.popularmovies.testutils.RxSchedulersTestRule;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Artur Vasilov
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class MoviesViewModelTest {

    @Rule
    public RxSchedulersTestRule rule = new RxSchedulersTestRule();

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private MoviesViewModel viewModel;

    private MoviesRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(MoviesRepository.class);
        Mockito.when(repository.popularMovies()).thenReturn(Observable.just(new ArrayList<>()));

        viewModel = new MoviesViewModel(repository);
    }

    @Test
    public void testLoadingMovies() throws Exception {
        Observer observer = Mockito.mock(Observer.class);
        viewModel.getMoviesList().observeForever(observer);

        Mockito.verify(repository).popularMovies();
        Mockito.verify(observer).onChanged(any(Response.class));
    }

    @Test
    public void testProgressUpdated() throws Exception {
        Observer observer = Mockito.mock(Observer.class);
        viewModel.isLoading().observeForever(observer);
        viewModel.getMoviesList();

        Mockito.verify(observer).onChanged(true);
        Mockito.verify(observer).onChanged(false);
    }
}
