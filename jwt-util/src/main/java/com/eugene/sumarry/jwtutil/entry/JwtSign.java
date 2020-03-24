package com.eugene.sumarry.jwtutil.entry;

import com.eugene.sumarry.jwtutil.model.JwtProperty;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtSign {

    private final Logger logger = LoggerFactory.getLogger(JwtSign.class);

    private final static String JWT_TOKEN_KEY = "jwt_token_key";

    private final static String HASHTAG = "#";

    private JwtProperty jwtProperty;

    public JwtProperty getJwtProperty() {
        return jwtProperty;
    }

    public void setJwtProperty(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String sign(String key, Object object) {
        long expiredTime = System.currentTimeMillis() + Long.valueOf(jwtProperty.getExpiration());

        return Jwts.builder().claim(key, object)
                .setExpiration(new Date(expiredTime))
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret()).compact();
    }

    public Jwt unSign(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
            SignatureException, IllegalArgumentException {
        return Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(jwtToken);
    }

    public Object unSignHeader(String jwtToken) {
        return !isLegal(jwtToken) ? null : unSign(jwtToken).getHeader();
    }

    public Object unSignBody(String jwtToken) {
        return !isLegal(jwtToken) ? null : unSign(jwtToken).getBody();
    }

    public String buildJwtTokenRedisKey(String userName) {
        return new StringBuffer(userName).append(HASHTAG).append(JWT_TOKEN_KEY).toString();
    }

    public boolean isLegal(String jwtToken) {
        boolean isLegal = true;
        try {
            unSign(jwtToken);
        } catch (ExpiredJwtException e) {
            isLegal = false;
            logger.error("Token (" + jwtToken + ") has been expired.", e);
        } catch (UnsupportedJwtException e) {
            isLegal = false;
            logger.error("Un supported jwt exception", e);
        } catch (MalformedJwtException e) {
            isLegal = false;
            logger.error("Token format is error", e);
        } catch (SignatureException e) {
            isLegal = false;
            logger.error("Token signature is error", e);
        } catch (IllegalArgumentException e) {
            isLegal = false;
            logger.error("Illegal argument is error", e);
        }
        return isLegal;
    }

    public static void main(String[] args) {
        new JwtSign();
    }
}
