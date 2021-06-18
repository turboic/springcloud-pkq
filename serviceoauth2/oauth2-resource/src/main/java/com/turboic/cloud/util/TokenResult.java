package com.turboic.cloud.util;

/**
 * {"access_token":"66731814-5a32-46f3-94c7-b519c97bcb4d","token_type":"bearer",
 * "refresh_token":"43648e50-19b9-4b5d-91ee-68afc9427efc","expires_in":7199,"scope":"all"}
 * @author liebe
 */
public class TokenResult {
    private String access_token;

    private String token_type;

    private String refresh_token;

    private long expires_in;

    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
