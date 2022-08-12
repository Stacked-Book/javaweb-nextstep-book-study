package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HttpResponseImpl implements HttpResponse{
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;
    private final Map<String, String> headerMap = new ConcurrentHashMap<>();

    public HttpResponseImpl(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    @Override
    public void forward(String url) {
        try{
            byte[] body = Files.readAllBytes(new File("./3~6장 실습공간/webapp" + url).toPath());
            inputContentType(url, body);
            response200Header(body.length);
            responseBody(body);
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headerMap.put("Content-Type", "text/html;charset=utf-8");
        headerMap.put("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
    }

    @Override
    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            writeHeaders();
            dos.writeBytes("Location: " + redirectUrl + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void writeHeaders() {
        Set<String> keys = headerMap.keySet();
        for (String key : keys) {
            try {
                dos.writeBytes(key + ": " + headerMap.get(key) + " \r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addHeader(String key, String value) {
        headerMap.put(key, value);
    }


    @Override
    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            writeHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void inputContentType(String url, byte[] body) {
        if (url.endsWith(".css")) {
            headerMap.put("Content-Type", "text/css");
        } else if (url.endsWith(".js")) {
            headerMap.put("Content-Type", "application/javascript");
        } else {
            headerMap.put("Content-Type", "text/html;charset=utf-8");
        }
        headerMap.put("Content-Length", body.length + "");
    }
}
