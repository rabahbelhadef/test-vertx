package org.jacpfx.demo.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;


public class ReadVerticle extends AbstractVerticle {

    @Override
    public void start(io.vertx.core.Future<Void> startFuture) throws Exception {
        vertx.eventBus().consumer("/api/users", getAllUsers());
        vertx.eventBus().consumer("/api/users/:id", getUserById());
        startFuture.complete();
    }

    private Handler<Message<Object>> getUserById() {
        return handler -> {
            final String id = handler.body().toString();
            handler.reply("Hello Id" + id);
        };
    }

    private Handler<Message<Object>> getAllUsers() {
        return handler -> {
            handler.reply("Hello all");
        };
    }

}