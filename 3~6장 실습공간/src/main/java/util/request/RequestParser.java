package util.request;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        Line requestLine = Line.of(line);

        Header headers = new Header();
        while (!(line = reader.readLine()).equals(END_POINT)) {
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
            headers.setHeader(pair.getKey(), pair.getValue());
        }

        Body body = new Body();

        if (headers.contains(CONTENT_LENGTH)){
            String data = IOUtils.readData(reader, Integer.parseInt(headers.getHeader(CONTENT_LENGTH)));
            body = new Body(data);
        }
        return new HttpRequestImpl(requestLine, headers, body);
    }

}
