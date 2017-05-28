package ru.gdg.arturvasilov.popularmovies.data.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Artur Vasilov
 */
public class Response<T> {

    @Nullable
    private final T data;

    @Nullable
    private final Throwable error;

    private Response(@Nullable T data, @Nullable Throwable error) {
        this.data = data;
        this.error = error;
    }

    @NonNull
    public static <T> Response<T> success(@NonNull T data) {
        return new Response<T>(data, null);
    }

    @NonNull
    public static <T> Response<T> error(@NonNull Throwable error) {
        return new Response<T>(null, error);
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}
