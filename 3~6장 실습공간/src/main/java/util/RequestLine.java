package util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestLine {
    private final Map<String, String> headerMap = new ConcurrentHashMap<>();
    private Map<String, String> parameterMap = new ConcurrentHashMap<>();

    public RequestLine(String requestMsg) {
        initHeaderMapWithRequestMsg(requestMsg);
        inputQueryStringToParameterMap();
    }

    public String getMethod() {
        return this.headerMap.get("method");
    }

    public String getPath() {
        if (isGETMethod()) {
            return headerMap.get("path").substring(0, headerMap.get("path").indexOf("?"));
        }
        return headerMap.get("path");
    }

    public Map<String,String> getParameter() {
        return parameterMap;
    }

    private void inputQueryStringToParameterMap() {
        if (isGETMethod()) {
            this.parameterMap = HttpRequestUtils.parseQueryString(headerMap.get("path").substring(headerMap.get("path").indexOf("?") + 1));
        }
    }

    private boolean isGETMethod() {
        return headerMap.get("path").contains("?");
    }

    private void initHeaderMapWithRequestMsg(String requestMsg) {
        String[] requestLine = requestMsg.split(" ");
        this.headerMap.put("method", requestLine[0]);
        this.headerMap.put("path", requestLine[1]);
        this.headerMap.put("httpVer", requestLine[2]);
    }
}
