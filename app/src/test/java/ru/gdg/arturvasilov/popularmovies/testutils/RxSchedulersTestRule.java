package ru.gdg.arturvasilov.popularmovies.testutils;

import android.support.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class RxSchedulersTestRule implements TestRule {

    @NonNull
    @Override
    public Statement apply(@NonNull final Statement base, @NonNull Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.reset();
                RxJavaPlugins.onIoScheduler(Schedulers.trampoline());
                RxJavaPlugins.onComputationScheduler(Schedulers.trampoline());

                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());

                base.evaluate();

                RxJavaPlugins.reset();
                RxAndroidPlugins.reset();
            }
        };
    }
}
