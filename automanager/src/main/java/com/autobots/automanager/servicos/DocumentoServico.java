package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class DocumentoServico {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    public List<Documento> obterTodos() {
        return documentoRepositorio.findAll();
    }

    public Optional<Documento> obterPorId(Long id) {
        return documentoRepositorio.findById(id);
    }

    public Documento salvar(Documento documento) {
        return documentoRepositorio.save(documento);
    }

    public void deletar(Long id) {
        documentoRepositorio.deleteById(id);
    }

    public Documento atualizar(Long id, Documento documentoAtualizado) {
        Optional<Documento> documentoExistente = documentoRepositorio.findById(id);
        if (documentoExistente.isPresent()) {
            Documento documento = documentoExistente.get();
            documento.setTipo(documentoAtualizado.getTipo());
            documento.setNumero(documentoAtualizado.getNumero());
            return documentoRepositorio.save(documento);
        } else {
            return null;
        }
    }
}
