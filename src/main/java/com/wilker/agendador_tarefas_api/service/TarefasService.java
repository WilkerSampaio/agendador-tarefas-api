package com.wilker.agendador_tarefas_api.service;


import com.wilker.agendador_tarefas_api.infrastructure.dto.in.TarefasDTORequest;
import com.wilker.agendador_tarefas_api.infrastructure.dto.out.TarefasDTOResponse;
import com.wilker.agendador_tarefas_api.infrastructure.entity.TarefasEntity;
import com.wilker.agendador_tarefas_api.infrastructure.enums.StatusNotificacaoEnum;
import com.wilker.agendador_tarefas_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.agendador_tarefas_api.infrastructure.mapper.TarefaConverter;
import com.wilker.agendador_tarefas_api.infrastructure.mapper.TarefaUpdateConverter;
import com.wilker.agendador_tarefas_api.infrastructure.repository.TarefasRepository;
import com.wilker.agendador_tarefas_api.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTOResponse salvarTarefa(TarefasDTORequest tarefasDTORequest, String token){

        String email = jwtUtil.extractUsername(token.substring(7));

        tarefasDTORequest.setEmailUsuario(email);
        tarefasDTORequest.setDataCriacao(LocalDateTime.now());
        tarefasDTORequest.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity tarefa = tarefaConverter.paraTarefaEntity(tarefasDTORequest);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefa));
    }
    public List<TarefasDTOResponse> buscaListaDeTarefaPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){

        List<TarefasEntity> tarefaEntity = tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(
                dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE);
        if(tarefaEntity.isEmpty()){
            throw new ResourceNotFoundException("Nenhuma tarefa encontrada");
        }

        return tarefaConverter.paraListaTarefaDTO(tarefaEntity);
    }

    public List<TarefasDTOResponse> buscarTarefaPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));

        List<TarefasEntity> tarefaEntity = tarefasRepository.findByEmailUsuario(email);
        if(tarefaEntity.isEmpty()){
            throw new ResourceNotFoundException("Nenhuma tarefa encontrada");
        }

        return tarefaConverter.paraListaTarefaDTO(tarefaEntity);
    }

    public void deletaTarefaPorId(String id){
        if(!tarefasRepository.existsById(id)){
            throw new ResourceNotFoundException("ID não encontrado");
        }
        tarefasRepository.deleteById(id);
    }

}
