package com.wilker.agendador_tarefas_api.infrastructure.mapper;


import com.wilker.agendador_tarefas_api.infrastructure.dto.in.TarefasDTORequest;
import com.wilker.agendador_tarefas_api.infrastructure.dto.out.TarefasDTOResponse;
import com.wilker.agendador_tarefas_api.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")

public interface TarefaConverter {

    TarefasEntity paraTarefaEntity(TarefasDTORequest tarefasDTORequest);

    TarefasDTOResponse paraTarefaDTO (TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefaEntity(List<TarefasDTORequest> listaTarefasRequest);

    List<TarefasDTOResponse> paraListaTarefaDTO(List<TarefasEntity> tarefasEntity);
}
