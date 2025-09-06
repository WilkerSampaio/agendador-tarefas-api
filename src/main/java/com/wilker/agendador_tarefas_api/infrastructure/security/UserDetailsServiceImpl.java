package com.wilker.agendador_tarefas_api.infrastructure.security;

import com.wilker.agendadortarefas.infrastructure.client.UsuarioClient;
import com.wilker.agendadortarefas.infrastructure.dto.out.UsuarioDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsuarioClient usuarioClient; // Client para consultar dados do usuário em outro serviço

    // Carrega os dados do usuário pelo email, usando o token JWT para autorização
    public UserDetails carregaDadosUsuario(String email, String token){
        // Busca o usuário remoto via API
        UsuarioDTOResponse usuarioDTOResponse = usuarioClient.buscarUsuarioPeloEmail(email, token);

        // Constrói um UserDetails a partir dos dados recebidos
        return User
                .withUsername(usuarioDTOResponse.getEmail()) // Define o username como email
                .password(usuarioDTOResponse.getSenha())     // Define a senha (não usada diretamente para validação JWT)
                .build();
    }
}
