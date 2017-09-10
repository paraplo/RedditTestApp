package com.example.gplotnikov.reddittestapp.top;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gplotnikov.reddittestapp.R;
import com.example.gplotnikov.reddittestapp.domain.model.Topic;
import com.example.gplotnikov.reddittestapp.utils.Plurals;
import com.example.gplotnikov.reddittestapp.utils.RichListRecyclerViewAdapter;

class TopTopicsAdapter extends RichListRecyclerViewAdapter<Topic, TopTopicsAdapter.TopicViewHolder> {

    private OnThumbnailClickListener onThumbnailClickListener;

    TopTopicsAdapter(OnThumbnailClickListener onThumbnailClickListener) {
        this.onThumbnailClickListener = onThumbnailClickListener;
    }

    @Override
    protected TopicViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }

    public OnThumbnailClickListener getOnThumbnailClickListener() {
        return onThumbnailClickListener;
    }

    public void setOnThumbnailClickListener(OnThumbnailClickListener onThumbnailClickListener) {
        this.onThumbnailClickListener = onThumbnailClickListener;
    }

    @Override
    protected void onBindItemViewHolder(final TopicViewHolder holder, int position) {
        Context context = holder.itemView.getContext().getApplicationContext();
        Topic topic = get(position);
        holder.iconView.setOnClickListener(new InnerImageClickListener(topic));
        Glide.with(context).load(topic.getThumbnailUrl()).into(holder.iconView);
        holder.titleView.setText(topic.getTitle());
        holder.authorView.setText(topic.getAuthor());
        holder.dateView.setText(DateUtils.getRelativeTimeSpanString(topic.getCreationDate().getTime(),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS));
        holder.commentsCountView.setText(Plurals.with(context).getFormattedQuantityString(R.plurals.top_topic_comment_count,
                topic.getCommentsCount()));
    }

    interface OnThumbnailClickListener {
        void onThumbnailClick(Topic topic);
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView titleView;
        final TextView authorView;
        final TextView dateView;
        final TextView commentsCountView;

        TopicViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.icon);
            titleView = itemView.findViewById(R.id.title);
            authorView = itemView.findViewById(R.id.author);
            dateView = itemView.findViewById(R.id.date);
            commentsCountView = itemView.findViewById(R.id.comments);
        }
    }

    private class InnerImageClickListener implements View.OnClickListener {
        private Topic topic;

        InnerImageClickListener(Topic topic) {
            this.topic = topic;
        }

        @Override
        public void onClick(View view) {
            if (onThumbnailClickListener != null) {
                onThumbnailClickListener.onThumbnailClick(topic);
            }
        }
    }
}