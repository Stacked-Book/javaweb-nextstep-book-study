package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static util.HttpHeaderKey.*;
import static util.HttpRequestUtils.Pair;
import static util.HttpRequestUtils.parseHeader;

public class IOUtils {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String COLON = ":";
    private static final String END_POINT = "";

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(final BufferedReader br, final int contentLength) throws IOException {
        final char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static Map<String, String> getRequestMessage(final InputStream in) throws IOException {
        log.info("Get Request Message");
        final Map<String, String> requestMessages = new HashMap<>();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = reader.readLine();
        requestMessages.put(REQUEST.getKey(), line);

        while (!(line = reader.readLine()).equals(END_POINT)) {
            Pair pair = parseHeader(line);
            requestMessages.put(pair.getKey(), pair.getValue());
        }

        if (requestMessages.containsKey(CONTENT_LENGTH.getKey())) {
            requestMessages.put(
                BODY.getKey(),
                readData(reader, Integer.parseInt(requestMessages.get(CONTENT_LENGTH.getKey()))
                )
            );
        }

        return requestMessages;
    }
}
