package yay.linda.dto;

import java.util.UUID;

public class SessionToken {

    private String sessionToken;

    public SessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionToken() {
        this.sessionToken = UUID.randomUUID().toString();
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
