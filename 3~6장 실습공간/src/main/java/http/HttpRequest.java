package http;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.util.Map;

public class HttpRequest {
    private final BufferedReader br;
    private final InputStream in;
    private Map<String, String> paramsMap;
    private RequestLine requestLine;
    private HttpMethod httpMethod;
    private HttpHeaders httpHeaders;

    public HttpRequest(InputStream in) throws IOException {
        this.in = in;
        this.br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();

        this.requestLine = new RequestLine(line);
        this.httpHeaders = new HttpHeaders();

        String temp;
        while (!(temp = br.readLine()).equals("")) {
            httpHeaders.inputHeaders(temp);
        }

        httpMethod = requestLine.getMethod();
        if (httpMethod.isPost()) {
            String body = IOUtils.readData(br, Integer.parseInt(httpHeaders.getHeader("Content-Length")));
            this.paramsMap = HttpRequestUtils.parseQueryString(body);
        } else {
            this.paramsMap = requestLine.getParameter();
        }
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() throws IOException {
        return this.requestLine.getPath();
    }

    public String getHeader(String connection){
        return this.httpHeaders.getHeader(connection);
    }

    public Object getParameter(String userId) {
        return this.paramsMap.get(userId);
    }

    public Map<String, String> getParamsMap() {
        return this.paramsMap;
    }

}
