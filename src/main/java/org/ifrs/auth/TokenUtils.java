package org.ifrs.auth;

import org.eclipse.microprofile.jwt.JsonWebToken;

public class TokenUtils {
    public static Long getUserId(JsonWebToken token) {
        return Long.parseLong(token.claim("userId").get().toString());
    }
}
