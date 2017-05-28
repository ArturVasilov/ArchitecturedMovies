package ru.gdg.arturvasilov.popularmovies.ui.screen.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.gdg.arturvasilov.popularmovies.R;
import ru.gdg.arturvasilov.popularmovies.interactor.images.Images;
import ru.gdg.arturvasilov.popularmovies.data.movies.Movie;
import ru.gdg.arturvasilov.popularmovies.ui.widget.BaseAdapter;

/**
 * @author Artur Vasilov
 */
class MoviesAdapter extends BaseAdapter<MoviesAdapter.ViewHolder, Movie> {

    private final int imageWidth;
    private final int imageHeight;

    MoviesAdapter(@NonNull List<Movie> items, int imageWidth, int imageHeight) {
        super(items);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.image_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = imageHeight;
        layoutParams.width = imageWidth;
        imageView.requestLayout();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Movie movie = getItem(position);
        Images.loadMovie(holder.mImageView, movie, Images.WIDTH_185);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final ImageView mImageView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
