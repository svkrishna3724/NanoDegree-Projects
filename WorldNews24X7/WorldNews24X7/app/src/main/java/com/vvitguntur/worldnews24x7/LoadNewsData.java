package com.vvitguntur.worldnews24x7;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import com.vvitguntur.worldnews24x7.utilities.NetworkUtils;
import java.net.URL;

class LoadNewsData extends AsyncTaskLoader<String> {

    private String category = null;

    public LoadNewsData(@NonNull Context context, String category) {
        super(context);
        Context context1 = context;
        this.category = category;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {

        try {
            URL url = NetworkUtils.buildUrl(category);
            return NetworkUtils.getResponseFromHttpUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
