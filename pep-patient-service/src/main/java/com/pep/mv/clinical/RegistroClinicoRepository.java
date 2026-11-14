package com.pep.mv.clinical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegistroClinicoRepository extends MongoRepository<RegistroClinico, String> {

    Page<RegistroClinico> findByPacienteIdOrderByDataRegistroDesc(Long pacienteId, Pageable pageable);

    Optional<RegistroClinico> findByIdempotencyKey(String idempotencyKey);
}
