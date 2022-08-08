package webserver;

import constants.HttpConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public void forward(String url) {
        try {
            byte[] body = Files.readAllBytes(new File("./3~6장 실습공간/webapp", url).toPath());
            if (url.endsWith(".css")) {
                headers.put(HttpConst.CONTENT_TYPE, "text/css");
            }
            else if (url.endsWith(".js")) {
                headers.put(HttpConst.CONTENT_TYPE, "application/javascript");
            }
            else {
                headers.put(HttpConst.CONTENT_TYPE, "text/html;charset=utf-8");
            }
            headers.put(HttpConst.CONTENT_LENGTH, body.length + "");
            response200Header(body.length);
            responseBody(body);
        } catch(IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headers.put(HttpConst.CONTENT_TYPE, "text/html;charset=utf-8");
        headers.put(HttpConst.CONTENT_LENGTH, contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void processHeaders() {

    }


}
