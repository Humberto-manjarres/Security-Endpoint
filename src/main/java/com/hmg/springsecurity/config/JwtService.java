package com.hmg.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    /*TODO: página para generar token https://seanwasere.com/generate-random-hex/*/
    private static final String SECRET_KEY = "ccc801aff6244671dbe5fb258a84c6ca2bd0b4ee4164e3e89137b6e0893ea4cd";


    /*TODO: implementación para la generación de token para la autenticación*/
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))/*TODO: fecha en que se genera el token*/
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))/*TODO: fecha en que expirará el token - 24 horas*/
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)/*TODO: llave codificada y algoritmo de la firma*/
                .compact();
    }

    public String getUserName(String token) {

        /*TODO: para hacer uso de la clase Claims, debemos agregar las dependencias jsonwebtoken api-impl-jackson.
        *  el método getSubject de la interfaz Claims es el que nos ayuda a obtener el nombre del usuario del token*/
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*TODO: este método codifica en SECRET_KEY*/
    private Key getSignInKey() {
        /*TODO: convertimos la SECRET_KEY a base 64*/
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        /*TODO: retornamos una firma */
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        /*TODO: usuario extraído del token*/
        final String username = getUserName(token);

        /*TODO: verificamos que el nombre del usuario extraído del token sea igual al usuario del userDetail que viene de la BD.
        *  también validaremos si el token no a expirado*/
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token,Claims::getExpiration);
    }

}
