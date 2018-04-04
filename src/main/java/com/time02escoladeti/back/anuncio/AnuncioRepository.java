package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.usuario.UsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, AnuncioId> {
    @Query("SELECT p FROM Anuncio p ORDER BY p.titulo ASC")
    List<Anuncio> findAllAnunciosImpulsionados(UsuarioId id);

    @Query("SELECT p FROM Anuncio p ORDER BY p.titulo ASC")
    List<Anuncio> findAllAnunciosPublicados(UsuarioId id);

    @Query("SELECT p FROM Anuncio p ORDER BY p.titulo ASC")
    List<Anuncio> findAllAnunciosAguardandoAprovacao(UsuarioId id);

    @Query("SELECT p FROM Anuncio p ORDER BY p.titulo ASC")
    List<Anuncio> findAllAnunciosExpirados(UsuarioId id);

    @Query("SELECT p FROM Anuncio p ORDER BY p.titulo ASC")
    List<Anuncio> getAllAnuncioImpulsionado();
}
