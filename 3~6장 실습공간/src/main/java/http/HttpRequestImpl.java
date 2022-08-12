package http;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequestImpl extends HttpRequest{
    private final BufferedReader br;
    private final InputStream in;
    private Map<String, String> paramsMap;
    private final RequestLine requestLine;
    private HttpMethod httpMethod;
    private HttpHeaders httpHeaders;


    public HttpRequestImpl(InputStream in) throws IOException {
        this.in = in;
        this.br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();

        this.requestLine = new RequestLine(line);
        this.httpHeaders = new HttpHeaders();

        readHttpHeaderLines();
        getRequestParam();
    }

    @Override
    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    @Override
    public String getPath() {
        return this.requestLine.getPath();
    }

    @Override
    public String getHeader(String connection){
        return this.httpHeaders.getHeader(connection);
    }

    @Override
    public Object getParameter(String userId) {
        return this.paramsMap.get(userId);
    }

    @Override
    public Map<String, String> getParamsMap() {
        return this.paramsMap;
    }

    private void getRequestParam() throws IOException {
        httpMethod = requestLine.getMethod();
        if (httpMethod.isPost()) {
            String body = IOUtils.readData(br, Integer.parseInt(httpHeaders.getHeader("Content-Length")));
            this.paramsMap = HttpRequestUtils.parseQueryString(body);
        } else {
            this.paramsMap = requestLine.getParameter();
        }
    }

    private void readHttpHeaderLines() throws IOException {
        String temp;
        while (!(temp = br.readLine()).equals("")) {
            httpHeaders.inputHeaders(temp);
        }
    }
}
