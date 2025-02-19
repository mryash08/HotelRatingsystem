package org.hotelrating.apigateway.Models;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthResponse {

    private String userId;

    private String accessToken;
    private String refreshToken;

    private long expireAt;

    private Collection<String> authorities;

}
