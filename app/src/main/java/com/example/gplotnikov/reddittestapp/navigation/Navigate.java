package com.example.gplotnikov.reddittestapp.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.gplotnikov.reddittestapp.top.TopicImageActivity;

public class Navigate {
    private final Activity activity;

    private Navigate(Activity activity) {
        this.activity = activity;
    }

    static Bundle getArgumentsBundle(Intent intent) {
        return intent.getBundleExtra(Key.ARGUMENTS_BUNDLE);
    }

    static Intent putArgumentsBundle(Intent intent, Bundle bundle) {
        return intent.putExtra(Key.ARGUMENTS_BUNDLE, bundle);
    }

    public static Navigate from(Activity activity) {
        return new Navigate(activity);
    }

    public void to(ViewTopicImageScreen screen) {
        simpleLaunch(TopicImageActivity.class, screen);
    }

    private Intent prepareIntent(Class<? extends Activity> activityClass, Arguments arguments) {
        return putArgumentsBundle(new Intent(activity, activityClass), arguments.getBundle());
    }

    private void simpleLaunch(Class<? extends Activity> activityClass, Screen<?> screen) {
        activity.startActivity(prepareIntent(activityClass, screen.getArguments()));
    }
}