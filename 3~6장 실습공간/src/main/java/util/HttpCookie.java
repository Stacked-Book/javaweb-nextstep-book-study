package util;

import java.util.Map;

public class HttpCookie {

    private Map<String, String> cookies;

    public HttpCookie(String cookieValue) {
        cookies = HttpRequestUtils.parseCookies(cookieValue);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }
}
