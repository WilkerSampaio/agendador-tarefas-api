package com.wilker.agendador_tarefas_api.infrastructure.annotations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro de servidor")
})
public @interface ApiTarefaResponses {
}
