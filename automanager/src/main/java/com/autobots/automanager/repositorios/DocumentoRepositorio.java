package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entidades.Documento;

@Repository
public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
}