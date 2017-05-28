package ru.gdg.arturvasilov.popularmovies.ui.screen.movies;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import ru.gdg.arturvasilov.popularmovies.R;
import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;
import ru.gdg.arturvasilov.popularmovies.data.database.DatabaseHolder;
import ru.gdg.arturvasilov.popularmovies.data.database.dao.MoviesDao;
import ru.gdg.arturvasilov.popularmovies.interactor.api.ApiFactory;
import ru.gdg.arturvasilov.popularmovies.interactor.api.MoviesService;
import ru.gdg.arturvasilov.popularmovies.ui.screen.base.BaseLifecycleActivity;
import ru.gdg.arturvasilov.popularmovies.ui.screen.details.MovieDetailsActivity;
import ru.gdg.arturvasilov.popularmovies.ui.screen.loading.LoadingDialog;
import ru.gdg.arturvasilov.popularmovies.ui.widget.BaseAdapter;
import ru.gdg.arturvasilov.popularmovies.ui.widget.EmptyRecyclerView;

public class MoviesActivity extends BaseLifecycleActivity implements BaseAdapter.OnItemClickListener<Movie> {

    private LoadingDialog progressDialog;

    private MoviesAdapter adapter;

    @Override
    public void onItemClick(@NonNull View view, @NonNull Movie movie) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        MovieDetailsActivity.startActivity(this, imageView, movie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = LoadingDialog.create(R.string.movies_progress);

        setupMoviesRecyclerView();

        MoviesViewModel viewModel = getViewModel();
        viewModel.getMoviesList().observe(this, moviesResponse -> {
            if (moviesResponse != null && moviesResponse.getData() != null) {
                adapter.setNewValues(moviesResponse.getData());
            } else if (moviesResponse != null && moviesResponse.getError() != null) {
                // show error
            }
        });

        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading != null && isLoading) {
                progressDialog.show(getSupportFragmentManager());
            } else {
                progressDialog.cancel();
            }
        });
    }

    private void setupMoviesRecyclerView() {
        EmptyRecyclerView recyclerView = (EmptyRecyclerView) findViewById(R.id.recyclerView);
        int columns = getResources().getInteger(R.integer.columns_count);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(findViewById(R.id.empty));

        int imageWidth = getResources().getDisplayMetrics().widthPixels / columns;

        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.rows_count, typedValue, true);
        float rowsCount = typedValue.getFloat();
        int actionBarHeight = getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)
                ? TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics())
                : 0;

        int imageHeight = (int) ((getResources().getDisplayMetrics().heightPixels - actionBarHeight) / rowsCount);
        adapter = new MoviesAdapter(new ArrayList<>(), imageWidth, imageHeight);
        adapter.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
    }

    @NonNull
    private MoviesViewModel getViewModel() {
        MoviesService service = ApiFactory.getMoviesService();
        MoviesDao moviesDao = DatabaseHolder.database().moviesDao();
        ViewModelProvider.Factory factory = new MoviesViewModelProviderFactory(service, moviesDao);
        return ViewModelProviders.of(this, factory).get(MoviesViewModel.class);
    }
}
