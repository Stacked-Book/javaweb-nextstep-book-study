package webserver;

import http.request.HttpRequest;
import http.request.RequestParser;
import http.response.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            ResponseParser responseParser = new ResponseParser(out);
            responseParser.forward(httpRequest.url());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
