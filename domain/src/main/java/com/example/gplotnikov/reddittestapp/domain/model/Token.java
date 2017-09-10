package com.example.gplotnikov.reddittestapp.domain.model;

public class Token {
    private final String accessToken;
    private final String tokenType;
    private final String deviceId;
    private final String expiresIn;
    private final String scope;

    public Token(String accessToken, String tokenType, String deviceId, String expiresIn, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.deviceId = deviceId;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (!accessToken.equals(token.accessToken)) return false;
        if (!tokenType.equals(token.tokenType)) return false;
        if (!deviceId.equals(token.deviceId)) return false;
        if (!expiresIn.equals(token.expiresIn)) return false;
        return scope.equals(token.scope);

    }

    @Override
    public int hashCode() {
        int result = accessToken.hashCode();
        result = 31 * result + tokenType.hashCode();
        result = 31 * result + deviceId.hashCode();
        result = 31 * result + expiresIn.hashCode();
        result = 31 * result + scope.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}