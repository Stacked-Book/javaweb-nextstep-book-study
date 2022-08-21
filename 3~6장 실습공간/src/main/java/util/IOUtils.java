package util;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

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
}
