package com.example.gplotnikov.reddittestapp.data.backend;

import com.example.gplotnikov.reddittestapp.domain.backend.Backend;

public class BackendFactory {
    private BackendFactory() {/* prevent instantiation */}

    public static Backend newRetrofitBackend(String user, String password) {
        return new RetrofitBackend(user, password);
    }
}
