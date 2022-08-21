package http.request;

import util.HttpRequestUtils;

import java.util.Collections;
import java.util.Map;

public final class RequestHeaders {
    private final Map<String, String> headers;
    private final Map<String, String> cookies;

    public RequestHeaders(Map<String, String> params) {
        headers = params;
        if (!params.containsKey("Cookie")) {
            cookies = Collections.emptyMap();
            return;
        }
        cookies = HttpRequestUtils.parseCookies(params.get("Cookie"));
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

    public boolean isLogined() {
        return cookies.size() != 0 && cookies.containsKey("logined") && !cookies.get("logined").equals("false");
    }
}
