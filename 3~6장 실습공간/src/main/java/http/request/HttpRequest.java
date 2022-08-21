package http.request;

import java.util.Map;

public interface HttpRequest {

    RequestMethod method();

    String url();

    String protocol();

    Map<String, String> headers();

    String header(String key);

    String content();

    String toString();

    String parameter(String key);

    boolean isLogined();

    String bodyParameter(String key);
}
