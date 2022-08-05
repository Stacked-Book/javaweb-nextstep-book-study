package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.RequestLine;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private final BufferedReader br;
    private final InputStream in;
    private final Map<String, String> headerMap = new ConcurrentHashMap<>();


    private Map<String, String> paramsMap = new ConcurrentHashMap<>();
    private RequestLine requestLine;

    public HttpRequest(InputStream in) throws IOException {
        this.in = in;
        this.br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();

        this.requestLine = new RequestLine(line);

        String temp = br.readLine();
        while (!temp.equals("")) {
            log.debug("line= {}" , line);
            String[] split = temp.split(" ");
            split[0] = split[0].replace(":", "");
            this.headerMap.put(split[0], split[1]);
            temp = br.readLine();
        }

        if (getMethod().equals("POST")) {
            String body = IOUtils.readData(br, Integer.parseInt(headerMap.get("Content-Length")));
            this.paramsMap = HttpRequestUtils.parseQueryString(body);
        } else {
            this.paramsMap = requestLine.getParameter();
        }
    }

    public String getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() throws IOException {
        return this.requestLine.getPath();
    }

    public Object getHeader(String connection){
        return this.headerMap.get(connection);
    }

    public Object getParameter(String userId) {
        return this.paramsMap.get(userId);
    }


    public Map<String, String> getParamsMap() {
        return this.paramsMap;
    }
}
