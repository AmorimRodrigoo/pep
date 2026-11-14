package com.pep.mv.clinical;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "registros_clinicos")
@CompoundIndex(name = "idx_paciente_data", def = "{'pacienteId': 1, 'dataRegistro': -1}")
public class RegistroClinico {

    @Id
    private String id;

    private Long pacienteId;

    private TipoRegistroClinico tipo;

    private Instant dataRegistro;

    private Long profissionalId;

    private Map<String, Object> conteudo;

    private Instant criadoEm;

    @Indexed(unique = true, sparse = true)
    private String idempotencyKey;

    public RegistroClinico() {
    }

    public RegistroClinico(
            Long pacienteId,
            TipoRegistroClinico tipo,
            Instant dataRegistro,
            Long profissionalId,
            Map<String, Object> conteudo,
            Instant criadoEm,
            String idempotencyKey
    ) {
        this.pacienteId = pacienteId;
        this.tipo = tipo;
        this.dataRegistro = dataRegistro;
        this.profissionalId = profissionalId;
        this.conteudo = conteudo;
        this.criadoEm = criadoEm;
        this.idempotencyKey = idempotencyKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public TipoRegistroClinico getTipo() {
        return tipo;
    }

    public void setTipo(TipoRegistroClinico tipo) {
        this.tipo = tipo;
    }

    public Instant getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Instant dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Map<String, Object> getConteudo() {
        return conteudo;
    }

    public void setConteudo(Map<String, Object> conteudo) {
        this.conteudo = conteudo;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
