package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static util.HttpHeaderKey.CONTENT_LENGTH;

public class HttpRequestImpl {
    private static final String QUERY = "?";
    private static final String END_POINT = "";
    private static final String REQUEST_LINE = " ";
    private final String method;
    private final String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();

    public HttpRequestImpl(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        if (line.equals(END_POINT)) {
            throw new RuntimeException("Invalid Request Line");
        }

        method = line.split(REQUEST_LINE)[0];
        path = line.split(REQUEST_LINE)[1];

        if (line.split(REQUEST_LINE)[1].contains(QUERY)) {
            parameters = HttpRequestUtils.parseQueryString(line.split(REQUEST_LINE)[1].split("\\?")[1]);
        }

        while (!(line = reader.readLine()).equals(END_POINT)) {
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
            headers.put(pair.getKey(), pair.getValue());
        }

        if (headers.containsKey(CONTENT_LENGTH.getKey())) {
            String data = IOUtils.readData(reader, Integer.parseInt(headers.get(CONTENT_LENGTH.getKey())));
            parameters = HttpRequestUtils.parseQueryString(data);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        if (method.equals("GET") && path.contains(QUERY)) {
            return path.split(headers.get("Host"))[1].split("\\?")[0];
        }
        if (method.equals("GET")) {
            return path.split(headers.get("Host"))[1];
        }
        return path;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

}
