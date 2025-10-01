package com.wilker.agendador_tarefas_api.controller;


import com.wilker.agendador_tarefas_api.infrastructure.annotations.ApiTarefaResponses;
import com.wilker.agendador_tarefas_api.infrastructure.dto.in.TarefasDTORequest;
import com.wilker.agendador_tarefas_api.infrastructure.dto.out.TarefasDTOResponse;
import com.wilker.agendador_tarefas_api.infrastructure.enums.StatusNotificacaoEnum;
import com.wilker.agendador_tarefas_api.infrastructure.security.SecurityConfig;
import com.wilker.agendador_tarefas_api.service.TarefasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Cadastrar Tarefas do Usuário")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Criar Tarefa de Usuário", description = "Cadastra uma nova tarefa")
    @ApiTarefaResponses
    public ResponseEntity<TarefasDTOResponse> registraTarefa(@RequestBody TarefasDTORequest tarefasDTORequest,
                                                             @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.salvarTarefa(tarefasDTORequest, token));
    }
    @GetMapping("/eventos")
    @Operation(summary = "Busca Todas as Tarefas por Periodo", description = "Busca tarefas cadastradas por periodo")
    @ApiTarefaResponses
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefaPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal){
        return ResponseEntity.ok(tarefasService.buscaListaDeTarefaPorPeriodo(dataInicial, dataFinal));
    }
    @GetMapping
    @Operation(summary = "Busca Lista de Tarefas por Email do Usuário", description = "Busca tarefas pelo email")
    @ApiTarefaResponses
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefaPorEmail(@RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.buscarTarefaPorEmail(token));
    }
    @DeleteMapping
    @Operation(summary = "Deleta Tarefa por ID", description = "Deleta tarefa cadastrada pelo ID")
    @ApiTarefaResponses
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam ("id") String id){
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    @Operation(summary = "Altera Status da Tarefa", description = "Altera status da tarefa cadastrada")
    @ApiTarefaResponses
    public ResponseEntity<TarefasDTOResponse> alterarStatusTarefa(@RequestParam ("status") StatusNotificacaoEnum status,
                                                                  @RequestParam ("id") String id){
        return ResponseEntity.ok(tarefasService.alteraStatusTarefa(status, id));
    }
    @PutMapping
    @Operation(summary = "Altera Dados da Tarefa", description = "Altera dados da tarefa cadastrada")
    @ApiTarefaResponses
    public ResponseEntity<TarefasDTOResponse> alterarDadosTarefa(@RequestBody TarefasDTORequest tarefasDTORequest,
                                                                 @RequestParam ("id") String id){
        return ResponseEntity.ok(tarefasService.alterarDadosTarefa(tarefasDTORequest, id));
    }
}
