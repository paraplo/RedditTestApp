package com.example.gplotnikov.reddittestapp.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class PresenterStorageProviderFragment extends Fragment implements PresenterStorageProvider {
    private final PresenterStorage storage = new PresenterStorage();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public PresenterStorage getPresenterStorage() {
        return storage;
    }
}
