package org.jacpfx.demo.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;


public class GatewayVerticle extends AbstractVerticle {


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        // define some REST API
        router.get("/api/users/:id").handler(this::getUserById);

        // enable static contant handling 
        router.route().handler(StaticHandler.create());
        vertx.createHttpServer().
                requestHandler(router::accept).
                listen(9090, "0.0.0.0");
        startFuture.complete();
    }

    private void getUserById(RoutingContext ctx) {
        // route REST request to event bus
        vertx.<String>eventBus().
                send("/api/users/:id", ctx.request().getParam("id"),
                        responseHandler -> defaultResponse(ctx, responseHandler));

    }

    private void defaultResponse(RoutingContext ctx, AsyncResult<Message<Object>> responseHandler) {
        if (responseHandler.failed()) {
            ctx.fail(500);
        } else {
            // respond to REST request
            final Message<Object> result = responseHandler.result();
            ctx.response().end(String.valueOf(result.body()));
        }
    }


}