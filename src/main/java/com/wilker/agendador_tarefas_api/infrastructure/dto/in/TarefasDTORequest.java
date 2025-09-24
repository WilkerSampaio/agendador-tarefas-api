package com.wilker.agendador_tarefas_api.infrastructure.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wilker.agendador_tarefas_api.infrastructure.enums.StatusNotificacaoEnum;
import java.time.LocalDateTime;

public record TarefasDTORequest(String id,
                                String nomeTarefa,
                                String descricao,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                LocalDateTime dataCriacao,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataEvento,
                                String emailUsuario,
                                LocalDateTime dataAlteracao,
                                StatusNotificacaoEnum statusNotificacaoEnum) {

}
