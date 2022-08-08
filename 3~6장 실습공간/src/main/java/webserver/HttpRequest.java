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

    private BufferedReader br;
    private String line;
    private String method;
    private Map<String, String> headers = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        this.br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
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
        while (!line.equals("")) {
            log.debug("header: {}", line);
            String[] headerTokens = line.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
            line = br.readLine();
        }
        return headers.get(header);
    }

    public String getParameter(String param) throws IOException {
        String resource = "";

        if (HttpConst.GET.equals(method)) {
            String query = getToken(1);
            resource = query.substring(getIndex() + 1);
            log.debug("resource : {}", resource);

        } else if (HttpConst.POST.equals(method)) {
            String length;
            if (headers.isEmpty()) {
                length = getHeader(HttpConst.CONTENT_LENGTH);
            } else {
                length = headers.get(HttpConst.CONTENT_LENGTH);
            }
            resource = IOUtils.readData(br, Integer.parseInt(length));
            log.debug("Request Body : {}", resource);
        }

        Map<String, String> params = HttpRequestUtils.parseQueryString(resource);
        return params.get(param);
    }
}
