package com.turboic.cloud.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */
public class JwtUtil {
    public static void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    public static void setExpiration(int expiration) {
        JwtUtil.expiration = expiration;
    }

    private static String secret="liebe";
    private static int expiration=7200;
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_ID="id";
    private static final String CLAIM_KEY_CREATED="created";
    private static final String CLAIM_KEY_ROLES="roles";

    public static String getUsernameFromToken(String token){
        String username;
        try {
            username=getClaimsFromToken(token).getSubject();
        }catch (Exception e){
            username=null;
        }
        return username;
    }

    //获得token中的时间
    public static Date getCreatedDateFromToken(String token){
        Date date;
        try {
            final Claims claims=getClaimsFromToken(token);
            date=new Date((Long)claims.get(CLAIM_KEY_CREATED));
        }catch (Exception e)
        {
            date=null;
        }
        return date;
    }

    //从token中获得过期时间
    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    //解析token
    private static Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
        }catch(ExpiredJwtException e){
            claims=e.getClaims();
        }
        return claims;
    }

    //生成过期时间
    private static Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+ expiration);
    }

    public static String generateToken(){
        Map<String,Object> claims=new HashMap<>(4);
        claims.put(CLAIM_KEY_USERNAME,"admin");
        claims.put(CLAIM_KEY_CREATED,new Date());
        claims.put(CLAIM_KEY_ID,"123456");
        claims.put(CLAIM_KEY_ROLES,"admin");
        return generateToken(claims);
    }
    public static String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    private static Boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public static Boolean canTokenBeRefreshed(String token){
        return !isTokenExpired(token);
    }


    public static String refreshToken(String token){
        String refreshedToken;
        try{
            final Claims claims=getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED,new Date());
            refreshedToken=generateToken(claims);
        }catch(Exception e){
            refreshedToken=null;
        }
        return refreshedToken;
    }

    public static Boolean validateToken(String token, UserDetails userDetails){
        final String username=getUsernameFromToken(token);
        final Date created=getCreatedDateFromToken(token);
        return username.equals(userDetails.getUsername())&&isTokenExpired(token);
    }
}