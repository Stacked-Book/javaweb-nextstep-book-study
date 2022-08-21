package http.request;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class RequestParser {
    private static final String END_POINT = "";
    private static final String CONTENT_LENGTH = "Content-Length";


    private RequestParser() {
        throw new IllegalArgumentException("Util 클래스입니다.");
    }

    public static HttpRequest parser(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        System.out.println(line);
        RequestLine requestLine = RequestLine.of(line);

        Map<String, String> headers = new HashMap<>();
        while (!(line = reader.readLine()).equals(END_POINT)) {
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
            headers.put(pair.getKey(), pair.getValue());
        }
        RequestHeaders requestHeaders = new RequestHeaders(headers);

        RequestBody body = new RequestBody();

        if (requestHeaders.contains(CONTENT_LENGTH)){
            String data = IOUtils.readData(reader, Integer.parseInt(requestHeaders.getHeader(CONTENT_LENGTH)));
            body = new RequestBody(data);
        }
        return new HttpRequestImpl(requestLine, requestHeaders, body);
    }

}
