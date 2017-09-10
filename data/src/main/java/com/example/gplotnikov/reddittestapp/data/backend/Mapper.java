package com.example.gplotnikov.reddittestapp.data.backend;

import com.example.gplotnikov.reddittestapp.domain.model.Order;
import com.example.gplotnikov.reddittestapp.domain.model.Page;
import com.example.gplotnikov.reddittestapp.domain.model.Topic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Mapper {
    static Topic map(ApiTopic topic) {
        ApiTopicData data = topic.getData();
        return new Topic(data.getTitle(),
                data.getAuthor(),
                new Date(DateUtils.toJavaTime(data.getCreatedAt())),
                data.getThumbnailUrl(),
                getOriginalImageUrl(data.getPreview()),
                data.getCommentsCount());
    }

    private static String getOriginalImageUrl(ApiPreview preview) {
        if (preview == null) return null;
        for (ApiImage image : preview.getImages()) {
            if (image.getSource() != null) {
                return image.getSource().getUrl();
            }
        }
        return null;
    }

    static Page<Topic> map(ApiListingResponse<ApiTopic> listing) {
        ApiData<ApiTopic> data = listing.getData();
        Order order;
        if (data.getAfter() != null) {
            order = Order.after(data.getAfter());
        } else if (data.getBefore() != null) {
            order = Order.before(data.getBefore());
        } else {
            throw new IllegalStateException();
        }
        List<Topic> topics = new ArrayList<>();
        for (ApiTopic apiTopic : data.getItems()) {
            topics.add(map(apiTopic));
        }
        return new Page<>(topics, order);
    }
}