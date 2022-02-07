package com.ark.JWT.With.UserDetails.security.jwt_algorith;

import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.utils.StringConstant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class JwtAlgorithm {

    private final String algorithKey = "ArkHab";
    /*
     * 1 MilliSecond = 0.001
     * 10 * 60 * 1000 = 600,000 Milli Second
     *  600,000 * 0.001 = 600 seconds
     * 600 / 60 = 10 Minutes
     * */

    private final int expireTime = 5 * 60 * 1000; // 5 Minutues
    private final int refreshTokenTime = 10 * 60 * 1000; // 10 Minutues


    public String tokenGenerate(@NotNull User user, @NotNull HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(algorithKey.getBytes());
        return JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(StringConstant.CLAIM_VARIABLE, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String refreshToken(@NotNull User user, @NotNull HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(algorithKey.getBytes());
        return JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenTime))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }


    public String getUserNameFromJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(algorithKey.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }

    public Collection<SimpleGrantedAuthority> getUserRolesFromJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(algorithKey.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String userName = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(StringConstant.CLAIM_VARIABLE).asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }


}
