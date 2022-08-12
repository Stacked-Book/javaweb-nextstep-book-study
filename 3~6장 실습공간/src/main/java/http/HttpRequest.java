package http;

import java.util.Map;

public abstract class HttpRequest {

    public abstract HttpMethod getMethod();

    public abstract String getPath();

    public abstract String getHeader(String connection);

    public abstract Object getParameter(String userId);

    public abstract Map<String, String> getParamsMap();
}
