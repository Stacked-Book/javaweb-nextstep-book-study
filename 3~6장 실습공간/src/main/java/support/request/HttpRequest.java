package support.request;

import java.util.Map;

public abstract class HttpRequest {

    public abstract String method();

    public abstract String url();

    public abstract String protocol();

    public abstract Map<String, String> headers();

    public abstract String header(String key);

    public abstract String content();

    public abstract String toString();
}
