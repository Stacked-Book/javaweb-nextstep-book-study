package http.response;

import java.util.Map;

public class HttpResponseImpl implements HttpResponse {
    private final String responseLine;
    private final Map<String, String> headers;
    private final String body;

    private HttpResponseImpl(String responseLine, Map<String, String> headers, String body) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponseImpl(Builder builder) {
        this(builder.responseLine, builder.headers, builder.body);
    }

    @Override
    public String getResponseLine() {
        return responseLine;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getBody() {
        return body;
    }

    public static class Builder {
        private String responseLine;
        private Map<String, String> headers;
        private String body;

        public Builder() {
        }

        public Builder responseLine(String responseLine) {
            this.responseLine = responseLine;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpResponseImpl build() {
            return new HttpResponseImpl(this);
        }
    }
}
