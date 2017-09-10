package com.example.gplotnikov.reddittestapp.base;

import com.example.gplotnikov.reddittestapp.present.AbstractPresenter;

import java.util.HashMap;
import java.util.Map;

class PresenterStorage {
    private final Map<Class<? extends AbstractPresenter>, AbstractPresenter> presenters = new HashMap<>();

    void put(AbstractPresenter presenter) {
        if (presenters.containsKey(presenter.getClass())) return;
        presenters.put(presenter.getClass(), presenter);
    }

    AbstractPresenter get(Class<? extends AbstractPresenter> clazz) {
        return presenters.get(clazz);
    }
}
