package org.example.empresa.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JwtValidationFilter extends BasicAuthenticationFilter {
    private KeyPair keyPair;

    public JwtValidationFilter(AuthenticationManager authenticationManager, KeyPair keyPair) {
        super(authenticationManager);
        this.keyPair = keyPair;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace("Bearer ", "");

        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);
            Collection<? extends GrantedAuthority> roles = List.of(
                    new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class).readValue(decodedJWT.getClaim("authorities").asString().getBytes(), SimpleGrantedAuthority[].class)
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, roles);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (JWTVerificationException exception){
            var objectMapper = new ObjectMapper();
            Map<String, String> error = Map.of("error", "Token is not valid: ".concat(exception.getMessage()));

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(error));
            response.setContentType("application/json");
        }
    }
}
