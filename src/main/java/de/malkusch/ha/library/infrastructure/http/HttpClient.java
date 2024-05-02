package de.malkusch.ha.library.infrastructure.http;

import static java.net.URLEncoder.encode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface HttpClient {

    public record Header(String name, String value) {
    }

    public HttpResponse get(String url, Header... headers) throws IOException, InterruptedException;

    public HttpResponse post(String url, Field... fields) throws IOException, InterruptedException;

    public static final class Field {
        public final String name;
        public final String value;

        public Field(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public Field(String name, int value) {
            this(name, Integer.toString(value));
        }

        String urlencoded() {
            try {
                return encode(name, "UTF-8") + "=" + encode(value, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public String toString() {
            return urlencoded();
        }

        @Override
        public int hashCode() {
            return name.hashCode() + value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Field other) {
                return name.equals(other.name) && value.equals(other.value);
            } else {
                return false;
            }
        }
    }
}
