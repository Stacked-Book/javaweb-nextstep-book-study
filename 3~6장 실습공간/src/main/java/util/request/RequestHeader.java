package util.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    public static final String DELIMITER = ":";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader() {
    }

    public void setHeader(String line) {
        String key = line.split(DELIMITER)[0].trim();
        String value = line.split(DELIMITER)[1].trim();
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
}
