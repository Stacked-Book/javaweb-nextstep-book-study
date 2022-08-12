package util.request;

import java.util.Map;

public class HttpRequestImpl extends HttpRequest {

    private Line line;
    private Header header;
    private Body body;

    public HttpRequestImpl(Line line, Header header, Body body) {
        this.line = line;
        this.header = header;
        this.body = body;
    }

    @Override
    protected String method() {
        return line.getMethod().name();
    }

    @Override
    protected String url() {
        return line.getUrl().getPath();
    }

    @Override
    protected String protocol() {
        return line.getUrl().getProtocol();
    }

    @Override
    protected Map<String, String> headers() {
        return header.getHeaders();
    }

    @Override
    protected String header(String key) {
        return header.getHeader(key);
    }

    @Override
    protected String content() {
        return body.getBody();
    }
}
