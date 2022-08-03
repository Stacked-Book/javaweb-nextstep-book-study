package webserver;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpRequest {
    public static final int HTTP_METHOD_NUMBER = 4;
    private final BufferedReader br;
    private final InputStream testDR;
    private final Map<String, String> headerMap = new ConcurrentHashMap<>();
    private final Map<String, String> parameterMap = new ConcurrentHashMap<>();

    public HttpRequest(InputStream in) {
        this.testDR = in;
        this.br = new BufferedReader(new InputStreamReader(in));
    }

    public String getMethod() throws IOException {
        String line = this.br.readLine();
        String[] requestLine = line.split(" ");
        this.headerMap.put("method", requestLine[0]);
        this.headerMap.put("path", requestLine[1]);
        this.headerMap.put("httpVer", requestLine[2]);
        return this.headerMap.get("method");
    }

    public String getPath() throws IOException {
        String path = this.headerMap.get("path");
        if (path.contains("?")) {
            return path.substring(0, path.indexOf("?"));
        }
        return path;
    }

    public Object getHeader(String connection) throws IOException {
        String temp = this.br.readLine();
        while (!temp.equals("")) {
            String[] line = temp.split(" ");
            line[0] = line[0].replace(":", "");
            this.headerMap.put(line[0], line[1]);
            temp = br.readLine();
        }
        return this.headerMap.get(connection);
    }

    public Object getParameter(String userId) throws IOException {
        String method = this.headerMap.get("method");
        String path;

        if (method.equals("GET")) {
            path = this.headerMap.get("path");
            path = path.substring(path.indexOf("?") + 1);
        }
        else {
            path = br.readLine();
        }

        String[] params = path.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            this.parameterMap.put(split[0], split[1]);
        }
        return this.parameterMap.get(userId);
    }
}
