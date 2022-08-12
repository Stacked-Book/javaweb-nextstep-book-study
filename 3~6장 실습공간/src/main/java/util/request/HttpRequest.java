package util.request;

import java.util.Map;

public abstract class HttpRequest {

    protected abstract String method();

    protected abstract String url();

    protected abstract String protocol();

    protected abstract Map<String, String> headers();

    protected abstract String header(String key);

    protected abstract String content();
}
