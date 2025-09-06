package com.wilker.agendador_tarefas_api.infrastructure.dto.out;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTOResponse {
    private String mensagem;
}
