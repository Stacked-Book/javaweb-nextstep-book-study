package webserver;

import http.request.HttpRequest;
import http.request.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class V1RequestHandler {
    private static final Logger log = LoggerFactory.getLogger(V0RequestHandler.class);
    private final Socket connection;
    private final UserService userService;

    public V1RequestHandler(Socket connection, UserService userService) {
        this.connection = connection;
        this.userService = userService;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = RequestParser.parser(in);
            log.info("Parse Request : {}", httpRequest);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
