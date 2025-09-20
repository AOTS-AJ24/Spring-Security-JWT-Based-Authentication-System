package com.abhi.SpringSecEx.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    //now making a key
    private String sec_key = " ";

    public JWTService(){
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = kg.generateKey();
            sec_key = Base64.getEncoder().encodeToString(sk.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }





    public String generateToken(String username){

        Map<String,Object> claim_1 = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claim_1)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*30)) //30 min
                .and()
                .signWith(getkey())
                .compact();


//        return "will generate";

    }

    private SecretKey getkey(){

        byte[] chavi = Decoders.BASE64.decode(sec_key);
        return Keys.hmacShaKeyFor(chavi); // but it takke only byte[]
    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject) ;
    }
    private <T> T extractClaim(String token , Function<Claims,T>claimResolver){
        final Claims claim1 = extractAllClaims(token);
        return  claimResolver.apply(claim1);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


//---------------------------------------------------------------------------------------------------------------------/
    public boolean validateToken(String token, UserDetails userDet) {
        final String user_name = extractUserName(token);
        return (user_name.equals(userDet.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }


}
