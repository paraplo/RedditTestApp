package com.example.gplotnikov.reddittestapp.present;

import java.util.LinkedList;
import java.util.Queue;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class AbstractPresenter<View> {
    private final Queue<ViewUpdate<View>> updates = new LinkedList<>();
    private final PublishSubject<ViewUpdate<View>> updateSubject = PublishSubject.create();
    private final Disposable viewUpdateDisposable;
    private View view;

    protected AbstractPresenter(final Scheduler dispatchScheduler) {
        viewUpdateDisposable = updateSubject.observeOn(dispatchScheduler).subscribe(new Consumer<ViewUpdate<View>>() {
            @Override
            public void accept(ViewUpdate<View> viewUpdate) throws Exception {
                updates.add(viewUpdate);
                dispatchViewUpdates();
            }
        });
    }

    protected View getView() {
        return view;
    }

    public final void attach(View view) {
        this.view = view;
        dispatchViewUpdates();
        onAttach(view);
    }

    public final void detach(View view) {
        this.view = null;
        onDetach(view);
    }

    public final void clear() {
        viewUpdateDisposable.dispose();
        updateSubject.onComplete();
        onClear();
    }

    public final void clearViewUpdates() {
        updates.clear();
        onClearUpdates();
    }

    protected void updateView(final ViewUpdate<View> update) {
        updateSubject.onNext(update);
    }

    private void dispatchViewUpdates() {
        if (getView() != null) {
            while (!updates.isEmpty()) {
                updates.poll().onUpdate(getView());
            }
        }
    }

    protected void onClearUpdates() {/* empty */}

    protected void onAttach(View view) {/* empty */}

    protected void onDetach(View view) {/* empty */}

    protected void onClear() {/* empty */}
}