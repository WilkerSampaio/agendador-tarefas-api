package com.wilker.agendador_tarefas_api.controller;


import com.wilker.agendador_tarefas_api.infrastructure.dto.in.TarefasDTORequest;
import com.wilker.agendador_tarefas_api.infrastructure.dto.out.TarefasDTOResponse;
import com.wilker.agendador_tarefas_api.service.TarefasService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefas")
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTOResponse> registraTarefa(@RequestBody TarefasDTORequest tarefasDTORequest,
                                                             @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.salvarTarefa(tarefasDTORequest, token));
    }
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefaPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal){
        return ResponseEntity.ok(tarefasService.buscaListaDeTarefaPorPeriodo(dataInicial, dataFinal));
    }
    @GetMapping
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefaPorEmail(@RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefasService.buscarTarefaPorEmail(token));
    }
    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam ("id") String id){
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.ok().build();
    }



}
