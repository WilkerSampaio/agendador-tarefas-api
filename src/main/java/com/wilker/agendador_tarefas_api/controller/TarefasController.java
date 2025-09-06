package com.wilker.agendador_tarefas_api.controller;


import com.wilker.agendador_tarefas_api.infrastructure.dto.in.TarefasDTORequest;
import com.wilker.agendador_tarefas_api.infrastructure.dto.out.TarefasDTOResponse;
import com.wilker.agendador_tarefas_api.service.TarefasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


}
