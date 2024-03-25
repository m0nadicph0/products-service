package com.monadic.products.util;

public class UrlUtil {
    public static String urlFor(String resource, int port) {
        return String.format("http://localhost:%d/%s", port, resource);
    }

    public static String singularUrlFor(String resource, String id, int port) {
        return String.format("http://localhost:%d/%s/%s", port, resource, id);
    }
}
