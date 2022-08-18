package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseParser {
    private static final String END_RESPONSE = "";
    private static final String END_LINE = "\r\n";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_SPLITTER = ": ";

    public static void parser(final DataOutputStream out, final HttpResponse response) {
        writeStream(out, response.getResponseLine());

        response.getHeaders().forEach(
            (key, value) -> writeStream(out, key + HEADER_SPLITTER + value)
        );

        if (response.getHeaders().containsKey(CONTENT_LENGTH)) {
            writeStream(out, response.getBody());
        }

        writeStream(out, END_RESPONSE);
    }

    private static void writeStream(DataOutputStream out, String text) {
        try {
            out.writeBytes(text + END_LINE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
