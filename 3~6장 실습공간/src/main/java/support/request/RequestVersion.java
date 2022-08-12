package support.request;

import java.util.Arrays;

public enum RequestVersion {

    /**
     * HTTP version 1.1
     */
    HTTP_1_1("HTTP/1.1"),

    /**
     * HTTP version 2
     */
    HTTP_2("HTTP/2");

    private String version;

    RequestVersion(String version) {
        this.version = version;
    }

    public static RequestVersion of(String version) {
       return Arrays.stream(RequestVersion.values())
           .filter(findVersion -> findVersion.version.equals(version))
           .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return version;
    }
}
