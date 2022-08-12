package util.request;

import java.util.HashMap;
import java.util.Map;

public class Header {
    public static final String DELIMITER = ":";

    private final Map<String, String> headers = new HashMap<>();

    public Header() {
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }

    @Override
    public String toString() {
        return "Header{" +
            "headers=" + headers +
            '}';
    }
}
