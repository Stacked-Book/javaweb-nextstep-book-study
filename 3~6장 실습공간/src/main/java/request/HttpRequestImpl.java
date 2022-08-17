package request;

import constants.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestImpl extends HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestImpl.class);
    private RequestLine requestLine;
    private RequestHandler handler;

    public HttpRequestImpl(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            if (line == null) {
                return;
            }
            requestLine = new RequestLine(line);
            handler = new RequestHandler(br, line);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    @Override
    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public String getParameter(String name) {
        return handler.getParams(name);
    }

    @Override
    public String getHeader(String name) {
        return handler.getHeader(name);
    }
}
