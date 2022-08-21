package http.request;

import java.util.Map;

public interface HttpRequest {

    String method();

    String url();

    String protocol();

    Map<String, String> headers();

    String header(String key);

    String content();

    String toString();

    String parameter(String key);
}
