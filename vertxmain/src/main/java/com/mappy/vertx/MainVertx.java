package com.mappy.vertx;

import io.vertx.core.Vertx;
import org.jacpfx.demo.service.GatewayVerticle;
import org.jacpfx.demo.service.ReadVerticle;

public class MainVertx {

    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(ReadVerticle.class.getName());
        vertx.deployVerticle(GatewayVerticle.class.getName());
    }
}
