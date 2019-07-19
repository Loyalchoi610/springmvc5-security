package com.forestvue.security;

import com.forestvue.domain.MemberVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class JwtUtil {
    private String secret="thisyearmust";

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public MemberVO parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            MemberVO member = new MemberVO();
            member.setUserName(body.getSubject());
            member.setUserid(((String) body.get("userId")));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> auth = gson.fromJson((String)body.get("role"),listType);


            return member;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param member the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(MemberVO member) {
        Claims claims = Jwts.claims().setSubject(member.getUserName());
        claims.put("userId", member.getUserid() + "");
        claims.put("role", member.getAuthList());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}