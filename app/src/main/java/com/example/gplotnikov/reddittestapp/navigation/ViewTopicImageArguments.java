package com.example.gplotnikov.reddittestapp.navigation;

import android.os.Bundle;

public class ViewTopicImageArguments extends Arguments {
    ViewTopicImageArguments(String url) {
        super(new Bundle());
        getBundle().putString(Key.URL_STRING, url);
    }

    ViewTopicImageArguments(Bundle bundle) {
        super(bundle);
    }

    public String getUrl() {
        return getBundle().getString(Key.URL_STRING);
    }
}