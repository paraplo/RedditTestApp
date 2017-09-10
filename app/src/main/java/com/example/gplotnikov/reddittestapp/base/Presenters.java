package com.example.gplotnikov.reddittestapp.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.gplotnikov.reddittestapp.present.AbstractPresenter;

public class Presenters {
    private final PresenterStorageProviderDelegate presenterStorageProviderDelegate;
    private final PresenterFactory presenterFactory;

    private Presenters(PresenterStorageProviderDelegate presenterStorageProviderDelegate, PresenterFactory presenterFactory) {
        this.presenterStorageProviderDelegate = presenterStorageProviderDelegate;
        this.presenterFactory = presenterFactory;
    }

    public static Presenters of(AppCompatActivity activity, PresenterFactory factory) {
        return new Presenters(new AppCompatActivityPresenterStorageProviderDelegate(activity), factory);
    }

    public static Presenters of(Activity activity, PresenterFactory factory) {
        return new Presenters(new ActivityPresenterStorageProviderDelegate(activity), factory);
    }

    public static Presenters of(Fragment fragment, PresenterFactory factory) {
        return new Presenters(new FragmentActivityPresenterStorageProviderDelegate(fragment.getActivity()), factory);
    }

    public static Presenters of(android.app.Fragment fragment, PresenterFactory factory) {
        return new Presenters(new ActivityPresenterStorageProviderDelegate(fragment.getActivity()), factory);
    }

    public <T extends AbstractPresenter> T get(Class<T> clazz) {
        AbstractPresenter presenter = presenterStorageProviderDelegate.getPresenterStorageProvider().getPresenterStorage().get(clazz);
        if (presenter != null) {
            return clazz.cast(presenter);
        } else {
            presenter = presenterFactory.create(clazz);
            if (presenter != null) {
                presenterStorageProviderDelegate.getPresenterStorageProvider().getPresenterStorage().put(presenter);
            }
            return clazz.cast(presenter);
        }
    }

    public interface PresenterFactory {
        AbstractPresenter create(Class<? extends AbstractPresenter> clazz);
    }

    private interface PresenterStorageProviderDelegate {
        String TAG = "PresenterStorageProviderFragment";

        PresenterStorageProvider getPresenterStorageProvider();
    }

    private static class ActivityPresenterStorageProviderDelegate implements PresenterStorageProviderDelegate {
        private final Activity activity;

        private ActivityPresenterStorageProviderDelegate(Activity activity) {
            this.activity = activity;
        }

        @Override
        public PresenterStorageProvider getPresenterStorageProvider() {
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            android.app.Fragment fragment = fragmentManager.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new PresenterStorageProviderFragment();
                fragmentManager.beginTransaction().add(fragment, TAG).commit();
            }
            return (PresenterStorageProvider) fragment;
        }
    }

    private static class AppCompatActivityPresenterStorageProviderDelegate implements PresenterStorageProviderDelegate {
        private final AppCompatActivity activity;

        private AppCompatActivityPresenterStorageProviderDelegate(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Override
        public PresenterStorageProvider getPresenterStorageProvider() {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new SupportPresenterStorageProviderFragment();
                fragmentManager.beginTransaction().add(fragment, TAG).commitNow();
            }
            return (PresenterStorageProvider) fragment;
        }
    }

    private static class FragmentActivityPresenterStorageProviderDelegate implements PresenterStorageProviderDelegate {
        private final FragmentActivity activity;

        private FragmentActivityPresenterStorageProviderDelegate(FragmentActivity activity) {
            this.activity = activity;
        }

        @Override
        public PresenterStorageProvider getPresenterStorageProvider() {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new SupportPresenterStorageProviderFragment();
                fragmentManager.beginTransaction().add(fragment, TAG).commitNow();
            }
            return (PresenterStorageProvider) fragment;
        }
    }
}
