package webserver;

import http.request.HttpRequest;
import http.request.RequestParser;
import http.response.HttpResponse;
import http.response.HttpResponseImpl;
import http.response.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class V1RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(V1RequestHandler.class);
    private final Socket connection;

    public V1RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = RequestParser.parser(in);
            log.info("Parse Request : {}", httpRequest);

            if (httpRequest.url().equals("/index.html")) {
                log.info("Move to main form");
                final DataOutputStream dos = new DataOutputStream(out);
                final byte[] body = Files.readAllBytes(new File("./3~6장 실습공간/webapp/index.html").toPath());

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text;charset=utf-8");
                headers.put("Content-Length", body.length + "");

                HttpResponse httpResponse = new HttpResponseImpl
                    .Builder()
                    .responseLine("HTTP/1.1 200 OK")
                    .headers(headers)
                    .body(body)
                    .build();
                ResponseParser.parser(dos, httpResponse);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
