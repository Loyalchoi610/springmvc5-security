package com.forestvue.security;

import com.forestvue.domain.MemberVO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
@Log4j
public class JwtGenerator {
    //@Value("${jwt.secret}")
    private String secret="5bc9b21a28f64739a0c25e02aeb7bc82419e8c46175e415e8563487ec932aad8abcdefghijk";

    //@Value("${jwt.expireHour}")
    private int expireHour=24;

    private Key jwtSecretKey;
    private int expireTime;

    @PostConstruct
    public void init() {
        jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());
        expireTime = 1000 * 60 * 60 * expireHour;
    }

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    AuthenticatedUser parseToken(String token) {
        try {
            //        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());
            Claims body = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
            return new AuthenticatedUser(body.getId(), (String) body.get("username"));

        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        } catch (JwtException | ClassCastException e) {
            log.error("jwt parse error.", e);
        }

        return null;
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims.
     * These properties are taken from the specified User object.
     * Tokens validity is infinite.
     *
     * @param member the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(MemberVO member) {
        Claims claims = Jwts.claims().setId(member.getUserid());
        claims.setExpiration(new Date(System.currentTimeMillis() + expireTime));
        claims.put("username", member.getUserName());
        claims.put("regDate", member.getRegDate().toString());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}

