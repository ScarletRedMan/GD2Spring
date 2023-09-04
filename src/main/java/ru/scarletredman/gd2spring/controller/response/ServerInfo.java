package ru.scarletredman.gd2spring.controller.response;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public final class ServerInfo {

    private final static int GD_SERVER_URL_LEN = "http://www.boomlings.com/database".length();
    private final static char URL_FILLER_CHAR = '-';

    private final String serverName;
    private final String root;
    private final String gdServerURI;
    private final String gdServerURL;

    public ServerInfo(@NonNull String serverName, @NonNull String root) {
        this.serverName = serverName;
        this.root = root;
        gdServerURI = generateURI(root);
        gdServerURL = root + gdServerURI;
    }

    private String generateURI(String root) {
        if (root.length() > GD_SERVER_URL_LEN) {
            throw new IllegalArgumentException("URL must not be longer than " + GD_SERVER_URL_LEN + " characters");
        }
        if (root.length() == GD_SERVER_URL_LEN) return root;

        var delta = GD_SERVER_URL_LEN - root.length();
        var sb = new StringBuilder();
        for (int i = 0; i < delta; i++) {
            if (i == 0) {
                sb.append(root.endsWith("/")? URL_FILLER_CHAR : '/');
                continue;
            }

            sb.append(URL_FILLER_CHAR);
        }
        return sb.toString();
    }
}
