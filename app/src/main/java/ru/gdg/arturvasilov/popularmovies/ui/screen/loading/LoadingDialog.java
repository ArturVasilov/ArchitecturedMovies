package ru.gdg.arturvasilov.popularmovies.ui.screen.loading;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.afollestad.materialdialogs.MaterialDialog;

import ru.gdg.arturvasilov.popularmovies.R;

/**
 * @author Artur Vasilov
 */
public class LoadingDialog extends DialogFragment {

    private static final String TAG = LoadingDialog.class.getSimpleName();
    private static final String TEXT_KEY = "text_id";

    @NonNull
    public static LoadingDialog create(@StringRes int textId) {
        Bundle bundle = new Bundle();
        bundle.putInt(TEXT_KEY, textId);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void show(@NonNull FragmentManager manager) {
        if (manager.findFragmentByTag(TAG) != null) {
            return;
        }
        show(manager, TAG);
    }

    public void cancel() {
        if (isAdded()) {
            dismissAllowingStateLoss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String text = getString(getArguments().getInt(TEXT_KEY, R.string.loading_progress));
        return new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .title(R.string.please_wait)
                .content(text)
                .build();
    }
}
