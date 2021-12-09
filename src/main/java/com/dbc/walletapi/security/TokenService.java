package com.dbc.walletapi.security;

import com.dbc.walletapi.entity.UsuarioEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    private static final String CLAIN_PERMISSOES = "permissoes";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UsuarioEntity usuario) {
        Date generateDate = new Date();

        //tempoExpiração
        Date exp = new Date(generateDate.getTime() + Long.parseLong(expiration));

        List<String> permissoes = usuario.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        String jwtToken = Jwts.builder()
                .setIssuer("pessoa-api")
                .claim(CLAIN_PERMISSOES, permissoes)
                .setSubject(usuario.getIdUsuario().toString())
                .setIssuedAt(generateDate)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TOKEN_PREFIX + " " + jwtToken;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String user = claims.getSubject();

            List<String> permissoes = (List<String>) claims.get(CLAIN_PERMISSOES);

            List<GrantedAuthority> grantedAuthorities = permissoes.stream()
                    .map(permissao -> new SimpleGrantedAuthority(permissao))
                    .collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        grantedAuthorities
                );
            }
        }

        return null;
    }
}