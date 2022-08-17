package response;

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
import java.util.Set;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos = null;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
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
            response200Header();
            responseBody(body);
        } catch(IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headers.put(HttpConst.CONTENT_TYPE, "text/html;charset=utf-8");
        headers.put(HttpConst.CONTENT_LENGTH, contents.length + "");
        response200Header();
        responseBody(contents);
    }

    public void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void processHeaders() {
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.get(key) + " \r\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
