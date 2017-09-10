package com.example.gplotnikov.reddittestapp.data.backend;

import java.util.concurrent.TimeUnit;

class DateUtils {
    private DateUtils() {/* no instantiation */}

    static long toJavaTime(long backendTime) {
        return TimeUnit.SECONDS.toMillis(backendTime);
    }

    static long toBackendTime(long javaTime) {
        return TimeUnit.MILLISECONDS.toSeconds(javaTime);
    }
}
