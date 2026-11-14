package com.pep.mv.clinical;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registros-clinicos")
public class RegistroClinicoController {

    private final RegistroClinicoService registroClinicoService;

    public RegistroClinicoController(RegistroClinicoService registroClinicoService) {
        this.registroClinicoService = registroClinicoService;
    }

    @PostMapping
    public ResponseEntity<RegistroClinicoResponse> criar(
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            @Valid @RequestBody RegistroClinicoRequest request
    ) {
        RegistroClinicoResponse response = registroClinicoService.criar(request, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Page<RegistroClinicoResponse>> listarPorPaciente(
            @PathVariable Long pacienteId,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(registroClinicoService.listarPorPaciente(pacienteId, pageable));
    }
}
