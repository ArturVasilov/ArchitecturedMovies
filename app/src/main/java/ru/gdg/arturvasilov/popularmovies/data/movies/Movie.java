package ru.gdg.arturvasilov.popularmovies.data.movies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
@Entity
public class Movie implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releasedDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;

    public Movie() {
    }

    public Movie(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        overview = in.readString();
        title = in.readString();
        releasedDate = in.readString();
        voteAverage = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(@NonNull String posterPath) {
        this.posterPath = posterPath;
    }

    @NonNull
    public String getOverview() {
        return overview;
    }

    public void setOverview(@NonNull String overview) {
        this.overview = overview;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(@NonNull String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", title='" + title + '\'' +
                ", releasedDate='" + releasedDate + '\'' +
                ", voteAverage=" + voteAverage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(title);
        parcel.writeString(releasedDate);
        parcel.writeDouble(voteAverage);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @NonNull
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @NonNull
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };
}
