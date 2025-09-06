package com.wilker.agendador_tarefas_api.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtro que intercepta cada requisição HTTP para processar tokens JWT
public class JwtRequestFilter extends OncePerRequestFilter {

    // Dependências para manipulação de JWT e carregamento de dados do usuário
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    // Construtor que inicializa as dependências
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // Metodo chamado uma vez por requisição para aplicar o filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtém o valor do header "Authorization" da requisição
        final String authorizationHeader = request.getHeader("Authorization");

        // Verifica se o header existe e começa com "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extrai o token JWT do header
            final String token = authorizationHeader.substring(7);
            // Obtém o nome de usuário presente no token
            final String username = jwtUtil.extractUsername(token);

            // Se o nome de usuário existe e ainda não há autenticação no contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Carrega os detalhes do usuário a partir do nome de usuário
                UserDetails userDetails = userDetailsServiceImpl.carregaDadosUsuario(username, authorizationHeader);
                // Valida o token JWT em relação ao usuário
                if (jwtUtil.validateToken(token, username)) {
                    // Cria um objeto de autenticação com os dados do usuário
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    // Define a autenticação no contexto de segurança do Spring
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Continua a cadeia de filtros para que a requisição prossiga normalmente
        chain.doFilter(request, response);
    }
}
