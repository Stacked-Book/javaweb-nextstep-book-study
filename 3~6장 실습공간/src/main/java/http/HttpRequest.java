package http;

import constants.HttpConst;
import constants.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private BufferedReader br;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private RequestLine requestLine;

    public HttpRequest(InputStream in) {
        try {
            br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            if (line == null) {
                return;
            }

            processHeader(line);
            requestLine = new RequestLine(line);
            processParams();


        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processHeader(String line) {
        try {
            while (!line.equals("")) {
                log.debug("header: {}", line);
                String[] headerTokens = line.split(": ");
                if (headerTokens.length == 2) {
                    headers.put(headerTokens[0], headerTokens[1]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processParams() {
        try {
            if (getMethod().isPost()) {
                String body = IOUtils.readData(br, Integer.parseInt(headers.get(HttpConst.CONTENT_LENGTH)));
                params = HttpRequestUtils.parseQueryString(body);
            } else {
                params = requestLine.getParams();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
