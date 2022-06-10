package portfolio.server.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Token {
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String getToken(String data){
        return Jwts.builder().setSubject(data).setExpiration(Date.from(
                LocalDateTime.now().plusMinutes(120L)
                        .atZone(ZoneId.systemDefault())
                        .toInstant())).signWith(KEY).compact();
    }
}