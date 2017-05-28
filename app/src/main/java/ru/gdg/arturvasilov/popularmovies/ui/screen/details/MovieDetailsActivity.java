package ru.gdg.arturvasilov.popularmovies.ui.screen.details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.gdg.arturvasilov.popularmovies.R;
import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;
import ru.gdg.arturvasilov.popularmovies.interactor.images.Images;

public class MovieDetailsActivity extends AppCompatActivity {

    //Most of transitions code here is from http://antonioleiva.com/collapsing-toolbar-layout/

    public static final String IMAGE = "image";
    public static final String EXTRA_MOVIE = "extraMovie";

    private static final String MAXIMUM_RATING = "10";

    private TextView titleTextView;
    private TextView overviewTextView;
    private TextView ratingTextView;

    public static void startActivity(@NonNull AppCompatActivity activity, @NonNull View transitionImage,
                                     @NonNull Movie movie) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareWindowForAnimation();
        setContentView(R.layout.activity_movie_details);

        titleTextView = (TextView) findViewById(R.id.title);
        overviewTextView = (TextView) findViewById(R.id.overview);
        ratingTextView = (TextView) findViewById(R.id.rating);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar), IMAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        showMovie(movie);
    }

    public void prepareWindowForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    /*

        In fact we should move this logic to some ViewModel, but it will be more complicated
        and unnecessary for this demo app, so I just leave it
     */

    private void showMovie(@NonNull Movie movie) {
        showToolbarTitle(getString(R.string.movie_details));
        showImage(movie, Images.WIDTH_780);
        String year = movie.getReleasedDate().substring(0, 4);
        showMovieTitle(movie.getTitle(), year);
        showMovieOverview(movie.getOverview());

        String average = String.valueOf(movie.getVoteAverage());
        average = average.length() > 3 ? average.substring(0, 3) : average;
        average = average.length() == 3 && average.charAt(2) == '0' ? average.substring(0, 1) : average;
        showAverageRating(average, MAXIMUM_RATING);
    }

    public void showToolbarTitle(@NonNull String title) {
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
    }

    public void showImage(@NonNull Movie movie, @NonNull String width) {
        final ImageView image = (ImageView) findViewById(R.id.image);
        Images.loadMovie(image, movie, width);
    }

    public void showMovieTitle(@NonNull String title, @NonNull String year) {
        titleTextView.setText(getString(R.string.movie_title, title, year));
    }

    public void showMovieOverview(@NonNull String overview) {
        overviewTextView.setText(overview);
    }

    public void showAverageRating(@NonNull String average, @NonNull String max) {
        ratingTextView.setText(getString(R.string.rating, average, max));
    }
}
