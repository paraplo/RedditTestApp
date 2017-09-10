package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

class ApiTokenResponse {
    @SerializedName("access_token")
    private final String accessToken;
    @SerializedName("token_type")
    private final String tokenType;
    @SerializedName("device_id")
    private final String deviceId;
    @SerializedName("expires_in")
    private final long expiresIn;
    @SerializedName("scope")
    private final String scope;

    ApiTokenResponse(String accessToken, String tokenType, String deviceId, long expiresIn, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.deviceId = deviceId;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }

    String getAccessToken() {
        return accessToken;
    }

    String getTokenType() {
        return tokenType;
    }

    String getDeviceId() {
        return deviceId;
    }

    long getExpiresIn() {
        return expiresIn;
    }

    String getScope() {
        return scope;
    }
}