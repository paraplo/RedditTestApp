package com.example.gplotnikov.reddittestapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class SupportPresenterStorageProviderFragment extends Fragment implements PresenterStorageProvider {
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
