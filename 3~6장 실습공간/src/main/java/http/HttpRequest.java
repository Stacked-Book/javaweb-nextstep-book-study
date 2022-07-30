package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();
    private RequestLine requestLine;

    /**
     * InputStream을 생성자 인자로 받아 InputStream에 담긴 데이터를 필요 형태로 분리 후 필드에 저장
     * */
    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader( new InputStreamReader(in,"UTF-8"));
            String line = br.readLine();
            if (line == null) {
                return;
            }
            requestLine = new RequestLine(line);
//            processRequestLine(line);

            line = br.readLine();
            while (!line.equals("")) {
                log.debug("header : {}", line);
                String[] tokens = line.split(":");
                headers.put(tokens[0].trim(), tokens[1].trim());
                line = br.readLine();
            }

            if("POST".equals(getMethod())) {
                String body = IOUtils.readData(br,
                        Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            } else {
                params = requestLine.getParams();
            }
        } catch (IllegalAccessException | IOException e) {
            log.error(e.getMessage());
        }
    }

/*    private void processRequestLine(String requestLine) {
        log.debug("request line : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        method = tokens[0];

        if("POST".equals(method)) {
            path = tokens[1];
            return;
        }

        int index = tokens[1].indexOf("?");
        if(index == -1) {
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(
                    tokens[1].substring(index+1)
            );
        }
    }*/

    public HttpMethod getMethod() {
//        return method;
        return requestLine.getMethod();
    }

    public String getPath() {
//        return path;
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        return params.get(name);
    }

}
