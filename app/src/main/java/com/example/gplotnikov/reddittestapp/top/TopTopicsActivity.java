package com.example.gplotnikov.reddittestapp.top;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.gplotnikov.reddittestapp.App;
import com.example.gplotnikov.reddittestapp.R;
import com.example.gplotnikov.reddittestapp.base.Presenters;
import com.example.gplotnikov.reddittestapp.domain.model.Topic;
import com.example.gplotnikov.reddittestapp.navigation.Navigate;
import com.example.gplotnikov.reddittestapp.navigation.Screen;
import com.example.gplotnikov.reddittestapp.present.AbstractPresenter;
import com.example.gplotnikov.reddittestapp.present.top.TopEntriesView;
import com.example.gplotnikov.reddittestapp.present.top.TopTopicsPresenter;
import com.example.gplotnikov.reddittestapp.utils.LoadMoreScrollListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TopTopicsActivity extends AppCompatActivity implements TopEntriesView,
        TopTopicsAdapter.OnThumbnailClickListener, SwipeRefreshLayout.OnRefreshListener {
    private TopTopicsAdapter topTopicsAdapter;
    private TopTopicsPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(topTopicsAdapter = new TopTopicsAdapter(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        recyclerView.setHasFixedSize(true);
        scrollListener = new LoadMoreScrollListener(recyclerView.getLayoutManager(), getResources().getInteger(R.integer.items_load_count)) {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        presenter = Presenters.of(this, new TopicPresenterFactory()).get(TopTopicsPresenter.class);
        presenter.clearViewUpdates();
        scrollListener.setEnabled(!presenter.isLoading());
        topTopicsAdapter.addAll(presenter.getTopics());
        topTopicsAdapter.setShowLoading(presenter.isLoading());

        if (presenter.getTopics().isEmpty()) {
            presenter.refresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            presenter.clear();
        }
    }

    @Override
    public void onLoaded(List<Topic> topics, boolean isRefreshed) {
        swipeRefreshLayout.setRefreshing(false);
        topTopicsAdapter.setShowLoading(false);
        scrollListener.setEnabled(true);
        if (isRefreshed) {
            topTopicsAdapter.replaceAll(topics);
        } else {
            topTopicsAdapter.addAll(topics);
        }
    }

    @Override
    public void onRefreshing() {
        scrollListener.setEnabled(false);
        swipeRefreshLayout.setRefreshing(true);
        topTopicsAdapter.setShowLoading(false);
    }

    @Override
    public void onLoading() {
        scrollListener.setEnabled(false);
        topTopicsAdapter.setShowLoading(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onException(Throwable throwable) {
        Toast.makeText(this, R.string.error_message_load_top_topic, Toast.LENGTH_SHORT).show();
        scrollListener.setEnabled(true);
        topTopicsAdapter.setShowLoading(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onThumbnailClick(Topic topic) {
        if (topic.getImageUrl() != null) {
            Navigate.from(this).to(Screen.viewTopicImage(topic.getImageUrl()));
        }
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    private class TopicPresenterFactory implements Presenters.PresenterFactory {
        @Override
        public AbstractPresenter create(Class<? extends AbstractPresenter> clazz) {
            return new TopTopicsPresenter(App.getInstance().getBackend(), AndroidSchedulers.mainThread());
        }
    }
}