package com.example.gplotnikov.reddittestapp.domain.backend;

import com.example.gplotnikov.reddittestapp.domain.model.Order;
import com.example.gplotnikov.reddittestapp.domain.model.Page;
import com.example.gplotnikov.reddittestapp.domain.model.Topic;

import java.io.IOException;

public interface Backend {
    Page<Topic> getTop(Order order, int limit) throws IOException, SomethingWrongException;
}