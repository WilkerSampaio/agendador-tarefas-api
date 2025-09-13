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

        TarefasDTORequest tarefasDTORequestFinal = new TarefasDTORequest(null,tarefasDTORequest.nomeTarefa(), tarefasDTORequest.descricao(),
                LocalDateTime.now(), tarefasDTORequest.dataEvento(), email, null, StatusNotificacaoEnum.PENDENTE);

        TarefasEntity tarefasEntity = tarefaConverter.paraTarefaEntity(tarefasDTORequestFinal);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }
    public List<TarefasDTOResponse> buscaListaDeTarefaPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){

        List<TarefasEntity> tarefasEntity = tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(
                dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE);

        return tarefaConverter.paraListaTarefaDTO(tarefasEntity);
    }

    public List<TarefasDTOResponse> buscarTarefaPorEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));

        List<TarefasEntity> tarefasEntity = tarefasRepository.findByEmailUsuario(email);
        if(tarefasEntity.isEmpty()){
            throw new ResourceNotFoundException("Nenhuma tarefa encontrada");
        }

        return tarefaConverter.paraListaTarefaDTO(tarefasEntity);
    }

    public void deletaTarefaPorId(String id){
        if(!tarefasRepository.existsById(id)){
            throw new ResourceNotFoundException("ID não encontrado");
        }
        tarefasRepository.deleteById(id);
    }

    public TarefasDTOResponse alteraStatusTarefa(StatusNotificacaoEnum statusNotificacaoEnum, String id){

        TarefasEntity tarefasEntity = tarefasRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException ("ID não encontrado"));
        tarefasEntity.setStatusNotificacaoEnum(statusNotificacaoEnum);
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

    public TarefasDTOResponse alterarDadosTarefa(TarefasDTORequest tarefasDTORequest, String id){
        TarefasEntity tarefasEntity = tarefasRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("ID não encontrado"));

        tarefaUpdateConverter.updateTarefas(tarefasDTORequest, tarefasEntity);
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

}
