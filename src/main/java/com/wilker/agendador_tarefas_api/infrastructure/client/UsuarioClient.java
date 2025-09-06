package com.wilker.agendador_tarefas_api.infrastructure.client;


import com.wilker.agendador_tarefas_api.infrastructure.dto.out.UsuarioDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/usuario")
    UsuarioDTOResponse buscarUsuarioPeloEmail(@RequestParam("email") String email,
                                              @RequestHeader("Authorization") String token);

}
