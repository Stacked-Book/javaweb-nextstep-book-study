package request;

import constants.HttpConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private RequestLine requestLine;

    private BufferedReader br;
    private String line;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    public RequestHandler(BufferedReader bufferedReader, String requestLine) {
        br = bufferedReader;
        line = requestLine;

        processParams();
        processHeader();
    }

    private void processHeader() {
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
            if (requestLine.getMethod().isPost()) {
                String body = IOUtils.readData(br, Integer.parseInt(headers.get(HttpConst.CONTENT_LENGTH)));
                params = HttpRequestUtils.parseQueryString(body);
            } else {
                params = requestLine.getParams();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String getParams(String name) { return params.get(name); };
    public String getHeader(String name) {
        return headers.get(name);
    }
}
