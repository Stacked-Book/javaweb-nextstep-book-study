package http.request;

import java.util.Map;

public final class HttpRequestImpl implements HttpRequest {

    private RequestLine line;
    private RequestHeaders header;
    private RequestBody body;

    public HttpRequestImpl(RequestLine line, RequestHeaders header, RequestBody body) {
        this.line = line;
        this.header = header;
        this.body = body;
    }

    @Override
    public String method() {
        return line.getMethod().name();
    }

    @Override
    public String url() {
        return line.getUrl().getPath();
    }

    @Override
    public String protocol() {
        return header("protocol");
    }

    @Override
    public Map<String, String> headers() {
        return header.getHeaders();
    }

    @Override
    public String header(String key) {
        return header.getHeader(key);
    }

    @Override
    public String parameter(String key) {
        return line.getParameter(key);
    }

    @Override
    public String content() {
        return body.getBody();
    }

    @Override
    public String toString() {
        return "HttpRequestImpl{" +
            "line=" + line +
            ", header=" + header +
            ", body=" + body +
            '}';
    }
}
