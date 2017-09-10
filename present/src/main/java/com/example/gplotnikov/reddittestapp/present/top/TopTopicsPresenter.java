package com.example.gplotnikov.reddittestapp.present.top;

import com.example.gplotnikov.reddittestapp.domain.backend.Backend;
import com.example.gplotnikov.reddittestapp.domain.model.Page;
import com.example.gplotnikov.reddittestapp.domain.model.Topic;
import com.example.gplotnikov.reddittestapp.present.AbstractPresenter;
import com.example.gplotnikov.reddittestapp.present.ViewUpdate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TopTopicsPresenter extends AbstractPresenter<TopEntriesView> {
    public static final int MAX_SIZE_UNLIMITED = -1;
    public static final int DEFAULT_ITEMS_PER_PAGE = 10;
    public static final int DEFAULT_MAX_COUNT = 50;
    private final int itemsPerPage;
    private final int maxSize;
    private final Backend backend;
    private final Scheduler observeScheduler;
    private final List<Topic> topics = new ArrayList<>();
    private Page<Topic> lastPage;
    private Disposable disposable;

    public TopTopicsPresenter(Backend backend, int itemsPerPage, int maxCount, Scheduler observeScheduler) {
        super(observeScheduler);
        this.backend = backend;
        this.observeScheduler = observeScheduler;
        this.itemsPerPage = itemsPerPage > maxCount && maxCount > 0 ? maxCount : itemsPerPage;
        this.maxSize = maxCount < 0 ? MAX_SIZE_UNLIMITED : maxCount;
    }

    public TopTopicsPresenter(Backend backend, Scheduler observeScheduler) {
        this(backend, DEFAULT_ITEMS_PER_PAGE, DEFAULT_MAX_COUNT, observeScheduler);
    }

    public List<Topic> getTopics() {
        return new ArrayList<>(topics);
    }

    public void refresh() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        disposable = Single.just(itemsPerPage)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer itemsToLoad) throws Exception {
                        updateView(new ViewUpdate<TopEntriesView>() {
                            @Override
                            public void onUpdate(TopEntriesView topEntriesView) {
                                topEntriesView.onRefreshing();
                            }
                        });
                        return itemsToLoad;
                    }
                })
                .map(new Function<Integer, Page<Topic>>() {
                    @Override
                    public Page<Topic> apply(@NonNull Integer itemsToLoad) throws Exception {
                        return backend.getTop(null, itemsToLoad);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(observeScheduler)
                .subscribe(new Consumer<Page<Topic>>() {
                    @Override
                    public void accept(final Page<Topic> topicPage) throws Exception {
                        topics.clear();
                        lastPage = topicPage;
                        topics.addAll(topicPage.getItems());
                        disposable = null;
                        updateView(new ViewUpdate<TopEntriesView>() {
                            @Override
                            public void onUpdate(TopEntriesView topEntriesView) {
                                topEntriesView.onLoaded(topicPage.getItems(), true);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(final Throwable throwable) throws Exception {
                        disposable = null;
                        updateView(new ViewUpdate<TopEntriesView>() {
                            @Override
                            public void onUpdate(TopEntriesView topEntriesView) {
                                topEntriesView.onException(throwable);
                            }
                        });
                    }
                });
    }

    public void loadMore() {
        if (disposable != null) return;
        int itemsToLoad = calculateItemsToLoad();
        if (itemsToLoad == 0) return;
        disposable = Single.just(itemsToLoad)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer itemsToLoad) throws Exception {
                        updateView(new ViewUpdate<TopEntriesView>() {
                            @Override
                            public void onUpdate(TopEntriesView topEntriesView) {
                                topEntriesView.onLoading();
                            }
                        });
                        return itemsToLoad;
                    }
                })
                .map(new Function<Integer, Page<Topic>>() {
                    @Override
                    public Page<Topic> apply(@NonNull Integer itemsToLoad) throws Exception {
                        return backend.getTop(lastPage != null ? lastPage.getOrder() : null, itemsToLoad);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(observeScheduler)
                .subscribe(new Consumer<Page<Topic>>() {
                    @Override
                    public void accept(final Page<Topic> page) throws Exception {
                        lastPage = page;
                        topics.addAll(page.getItems());
                        disposable = null;
                        updateView(new ViewUpdate<TopEntriesView>() {
                            @Override
                            public void onUpdate(TopEntriesView topEntriesView) {
                                topEntriesView.onLoaded(page.getItems(), false);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(final Throwable throwable) throws Exception {
                        disposable = null;
                        updateView(new ViewUpdate<TopEntriesView>() {
                            @Override
                            public void onUpdate(TopEntriesView topEntriesView) {
                                topEntriesView.onException(throwable);
                            }
                        });
                    }
                });
    }

    private int calculateItemsToLoad() {
        if (maxSize == MAX_SIZE_UNLIMITED) return itemsPerPage;
        if (topics.isEmpty()) return itemsPerPage;
        if (topics.size() > maxSize) return 0;
        int itemsLeft = maxSize - topics.size();
        if (itemsLeft < itemsPerPage) {
            return itemsLeft;
        } else {
            return itemsPerPage;
        }
    }

    public boolean isLoading() {
        return disposable != null;
    }

    @Override
    protected void onClear() {
        topics.clear();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}