package com.example.gplotnikov.reddittestapp.navigation;

import android.os.Bundle;

public abstract class Arguments {
    private final Bundle bundle;

    protected Arguments(Bundle bundle) {
        this.bundle = bundle;
    }

    protected Bundle getBundle() {
        return bundle;
    }
}