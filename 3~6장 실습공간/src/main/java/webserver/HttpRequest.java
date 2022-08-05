package webserver;

import constants.HttpConst;
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

    private InputStream input;
    private BufferedReader br;
    private String line;
    private String method;
    private Map<String, String> headers = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        this.input = in;
        this.br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        line = br.readLine();
    }

    private String getToken(int num) {
        String[] tokens = line.split(" ");
        return tokens[num];
    }

    private int getIndex() {
        return getToken(1).indexOf(HttpConst.QUE_MARK);
    }

    public String getMethod() {
        method = getToken(0);
        log.debug("request method : {}", method);
        return method;
    }

    public String getPath() {
        String path = "";
        if (HttpConst.GET.equals(method)) {
            String query = getToken(1);
            path = query.substring(0, getIndex());
        } else if (HttpConst.POST.equals(method)) {
            path = getToken(1);
        }
        log.debug("request path : {}", path);
        return path;
    }

    public String getHeader(String header) throws IOException {
        String nextLine = line;

        while (!"".equals(nextLine)) {
            log.debug("header: {}", nextLine);
            nextLine = br.readLine();
            String[] headerTokens = nextLine.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
        return headers.get(header);
    }

    public String getParameter(String param) throws IOException {
        Map<String, String> params;
        String resource = "";

        if (HttpConst.GET.equals(method)) {
            String query = getToken(1);
            resource = query.substring(getIndex() + 1);
            log.debug("resource : {}", resource);

        } else if (HttpConst.POST.equals(method)) {
            resource = IOUtils.readData(br, Integer.parseInt(headers.get(HttpConst.CONTENT_LENGTH)));
            log.debug("Request Body : {}", resource);
        }

        params = HttpRequestUtils.parseQueryString(resource);
        return params.get(param);
    }
}
