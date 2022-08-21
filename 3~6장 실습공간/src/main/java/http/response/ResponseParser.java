package http.response;

import http.request.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ResponseParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);
    private static final String END_RESPONSE = "";
    private static final String END_LINE = "\r\n";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_SPLITTER = ": ";

    public static void forward(DataOutputStream out, String url) {
        Map<String, String> headers = new HashMap<>();

        try {
            final byte[] body = Files.readAllBytes(new File("./3~6장 실습공간/webapp" + url).toPath());
            if (url.endsWith(".css")) {
                headers.put("Content-Type", "text/css");
            } else if (url.endsWith(".js")) {
                headers.put("Content-Type", "application/javascript");
            } else {
                headers.put("Content-Length", "text/html;charset=utf-8");
            }
            headers.put("Content-Length", body.length + "");

            HttpResponse httpResponse = new HttpResponseImpl
                .Builder()
                .responseLine("HTTP/1.1 200 OK")
                .headers(headers)
                .body(body)
                .build();

            parser(out, httpResponse);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void parser(final DataOutputStream out, final HttpResponse response) {
        writeLine(out, response);

        response.getHeaders().forEach(
            (key, value) -> writeKey(out, key, value)
        );

        writeSplitLine(out);

        if (response.getHeaders().containsKey(CONTENT_LENGTH)) {
            writeBody(out, response.getBody());
        }
    }

    private static void writeLine(DataOutputStream out, HttpResponse response) {
        writeStream(out, response.getResponseLine());
    }

    private static void writeKey(DataOutputStream out, String key, String value) {
        writeStream(out, key + HEADER_SPLITTER + value);
    }

    private static void writeSplitLine(DataOutputStream out) {
        writeStream(out, END_RESPONSE);
    }

    private static void writeStream(DataOutputStream out, String text) {
        try {
            out.writeBytes(text + END_LINE);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void writeBody(DataOutputStream out, byte[] body) {
        try {
            out.write(body, 0, body.length);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
