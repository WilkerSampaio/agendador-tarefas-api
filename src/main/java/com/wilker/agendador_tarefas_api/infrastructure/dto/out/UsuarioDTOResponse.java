package com.wilker.agendador_tarefas_api.infrastructure.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioDTOResponse {

    private String email;
    private String senha;

}
