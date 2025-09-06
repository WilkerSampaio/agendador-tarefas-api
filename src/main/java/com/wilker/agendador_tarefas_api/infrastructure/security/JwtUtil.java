package com.wilker.agendador_tarefas_api.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {

    // Chave secreta usada para assinar e verificar tokens JWT
    @Value("${chave.secreta}")
    private String secretKey;

    // Converte a chave secreta em SecretKey para uso no JWT
    private SecretKey getSecretKey(){
        byte[] key = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    // Extrai as claims (informações contidas no token) do JWT
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey()) // Usa a SecretKey para validar a assinatura
                .build()
                .parseClaimsJws(token) // Analisa o token JWT e obtém as claims
                .getBody(); // Retorna o corpo das claims
    }

    // Extrai o nome de usuário (subject) do token JWT
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Verifica se o token JWT está expirado
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Valida o token JWT verificando o nome de usuário e se não está expirado
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
