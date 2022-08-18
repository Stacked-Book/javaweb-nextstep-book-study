package http.response;

import java.util.Map;

public class HttpResponseImpl implements HttpResponse {
    private final ResponseLine responseLine;
    private final ResponseHeaders headers;
    private final ResponseBody body;

    private HttpResponseImpl(String responseLine, Map<String, String> headers, String body) {
        this.responseLine = new ResponseLine(responseLine);
        this.headers = new ResponseHeaders(headers);
        this.body = new ResponseBody(body);
    }

    public HttpResponseImpl(Builder builder) {
        this(builder.responseLine, builder.headers, builder.body);
    }

    @Override
    public String getResponseLine() {
        return responseLine.getResponseLine();
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    @Override
    public String getBody() {
        return body.getBody();
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
