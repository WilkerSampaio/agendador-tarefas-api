package com.wilker.agendador_tarefas_api.infrastructure.repository;

import com.wilker.agendador_tarefas_api.infrastructure.entity.TarefasEntity;
import com.wilker.agendador_tarefas_api.infrastructure.enums.StatusNotificacaoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

    List<TarefasEntity> findByDataEventoBetweenAndStatusNotificacaoEnum(LocalDateTime dataInicial, LocalDateTime dataFinal,
                                                                        StatusNotificacaoEnum statusNotificacaoEnum);
    List<TarefasEntity> findByEmailUsuario(String email);
}
