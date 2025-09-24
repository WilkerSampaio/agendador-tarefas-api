package com.wilker.agendador_tarefas_api.infrastructure.dto.out;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioDTOResponse{
    private String email;
    private String senha;

}
