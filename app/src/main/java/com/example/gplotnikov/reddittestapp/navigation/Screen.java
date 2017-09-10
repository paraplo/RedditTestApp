package com.example.gplotnikov.reddittestapp.navigation;

import com.example.gplotnikov.reddittestapp.top.TopicImageActivity;

public abstract class Screen<T extends Arguments> {
    private final T arguments;

    protected Screen(T arguments) {
        this.arguments = arguments;
    }

    public static ViewTopicImageScreen of(TopicImageActivity activity) {
        return new ViewTopicImageScreen(new ViewTopicImageArguments(Navigate.getArgumentsBundle(activity.getIntent())));
    }

    public static ViewTopicImageScreen viewTopicImage(String url) {
        return new ViewTopicImageScreen(new ViewTopicImageArguments(url));
    }

    public T getArguments() {
        return arguments;
    }
}