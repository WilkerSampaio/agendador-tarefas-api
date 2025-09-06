package com.wilker.agendador_tarefas_api.infrastructure.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioDTORequest {

    private String email;
    private String senha;

}
