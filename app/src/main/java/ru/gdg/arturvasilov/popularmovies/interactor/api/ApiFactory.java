package ru.gdg.arturvasilov.popularmovies.interactor.api;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.gdg.arturvasilov.popularmovies.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class ApiFactory {

    private static volatile OkHttpClient client;

    private static volatile MoviesService service;

    @NonNull
    public static MoviesService getMoviesService() {
        MoviesService service = ApiFactory.service;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = ApiFactory.service;
                if (service == null) {
                    service = ApiFactory.service = createService();
                }
            }
        }
        return service;
    }

    @NonNull
    private static MoviesService createService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MoviesService.class);
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = ApiFactory.client;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = ApiFactory.client;
                if (client == null) {
                    client = ApiFactory.client = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .build();
    }
}
