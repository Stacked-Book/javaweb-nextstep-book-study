package http.request;

import util.HttpRequestUtils;

public final class RequestBody {
    private final String body;

    public RequestBody() {
        this.body = null;
    }

    public RequestBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public String getParameter(String key) {
        return HttpRequestUtils.parseQueryString(body).get(key);
    }

    @Override
    public String toString() {
        return "Body{" +
            "body=" + body +
            '}';
    }

}
