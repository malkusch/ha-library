package de.malkusch.ha.library.infrastructure.http;

import java.io.IOException;

abstract class HttpClientProxy implements HttpClient {

    private final HttpClient client;

    HttpClientProxy(HttpClient client) {
        this.client = client;
    }

    @Override
    public final HttpResponse get(String url, Header... headers) throws IOException, InterruptedException {
        return proxied(() -> client.get(url, headers));
    }

    @Override
    public final HttpResponse post(String url, Field... fields) throws IOException, InterruptedException {
        return proxied(() -> client.post(url, fields));
    }

    abstract HttpResponse proxied(Operation op) throws IOException, InterruptedException;

    @FunctionalInterface
    static interface Operation {
        HttpResponse send() throws IOException, InterruptedException;
    }

}
