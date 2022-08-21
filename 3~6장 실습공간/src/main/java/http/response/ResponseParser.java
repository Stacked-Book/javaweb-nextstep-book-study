package http.response;

import http.request.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ResponseParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);
    private static final String END_RESPONSE = "";
    private static final String END_LINE = "\r\n";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_SPLITTER = ": ";
    public final DataOutputStream dos;

    public ResponseParser(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    public void forward(String url) {
        try {
            Map<String, String> headers = new HashMap<>();

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

            parser(httpResponse);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", redirectUrl);

        HttpResponse httpResponse = new HttpResponseImpl
            .Builder()
            .responseLine("HTTP/1.1 302 Found")
            .headers(headers)
            .build();

        parser(httpResponse);
    }

    private void parser(final HttpResponse response) {
        writeLine(response);

        response.getHeaders().forEach(
            (key, value) -> writeKey(key, value)
        );

        writeSplitLine();

        if (response.getHeaders().containsKey(CONTENT_LENGTH)) {
            writeBody(response.getBody());
        }
    }

    private void writeLine(HttpResponse response) {
        writeStream(response.getResponseLine());
    }

    private void writeKey(String key, String value) {
        writeStream(key + HEADER_SPLITTER + value);
    }

    private void writeSplitLine() {
        writeStream(END_RESPONSE);
    }

    private void writeStream(String text) {
        try {
            dos.writeBytes(text + END_LINE);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
