package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpHeaders {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String, String> headers = new ConcurrentHashMap<>();

    public void inputHeaders(String line) {
        log.debug("line= {}" , line);
        String[] split = line.split(" ");
        split[0] = split[0].replace(":", "");
        this.headers.put(split[0], split[1]);
    }

    public String getHeader(String connection) {
        return this.headers.get(connection);
    }

    public Map<String, String> getParameter() {
        return headers;
    }
}
