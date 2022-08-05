package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestLine {
    private final Map<String, String> headerMap = new ConcurrentHashMap<>();
    private Map<String, String> parameterMap = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
    private HttpMethod httpMethod;

    public RequestLine(String requestMsg) {
        log.debug("request line : {}", requestMsg);
        initHeaderMapWithRequestMsg(requestMsg);
        inputQueryStringToParameterMap();
    }

    public HttpMethod getMethod() {
        return this.httpMethod;
    }

    public String getPath() {
        if (httpMethod.isGET() && this.headerMap.get("path").contains("?") ) {
            return headerMap.get("path").substring(0, headerMap.get("path").indexOf("?"));
        }
        return headerMap.get("path");
    }

    public Map<String,String> getParameter() {
        return parameterMap;
    }

    private void inputQueryStringToParameterMap() {
        if (this.headerMap.get("path").contains("?")) {
            this.parameterMap = HttpRequestUtils.parseQueryString(headerMap.get("path").substring(headerMap.get("path").indexOf("?") + 1));
        }
    }

    private void initHeaderMapWithRequestMsg(String requestMsg) {
        String[] requestLine = requestMsg.split(" ");
        this.headerMap.put("method", requestLine[0]);
        this.headerMap.put("path", requestLine[1]);
        this.headerMap.put("httpVer", requestLine[2]);
        this.httpMethod = HttpMethod.valueOf(requestLine[0]);
    }
}
