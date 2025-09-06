package com.wilker.agendador_tarefas_api.service;

import com.wilker.agendadortarefas.infrastructure.dto.in.TarefasDTORequest;
import com.wilker.agendadortarefas.infrastructure.dto.out.TarefasDTOResponse;
import com.wilker.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.wilker.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.wilker.agendadortarefas.infrastructure.exception.ResourceNotFoundException;
import com.wilker.agendadortarefas.infrastructure.mapper.TarefaConverter;
import com.wilker.agendadortarefas.infrastructure.mapper.TarefaUpdateConverter;
import com.wilker.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.wilker.agendadortarefas.infrastructure.security.JwtUtil;
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

    public TarefasDTOResponse alteraStatusTarefa(StatusNotificacaoEnum statusNotificacaoEnum, String id){

            TarefasEntity tarefa = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException ("ID não encontrado"));
            tarefa.setStatusNotificacaoEnum(statusNotificacaoEnum);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefa));
    }

    public TarefasDTOResponse alterarDadosTarefa(TarefasDTORequest tarefasDTORequest, String id){
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).orElseThrow(
                    ()-> new ResourceNotFoundException("ID não encontrado"));

            tarefaUpdateConverter.updateTarefas(tarefasDTORequest, tarefasEntity);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }
}
