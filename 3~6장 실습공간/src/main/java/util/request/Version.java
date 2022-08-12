package util.request;

import java.util.Arrays;

public enum Version {

    /**
     * HTTP version 1.1
     */
    HTTP_1_1("HTTP/1.1"),

    /**
     * HTTP version 2
     */
    HTTP_2("HTTP/2");

    private String version;

    Version(String version) {
        this.version = version;
    }

    public static Version of(String version) {
       return Arrays.stream(Version.values())
           .filter(findVersion -> findVersion.version.equals(version))
           .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return version;
    }
}
