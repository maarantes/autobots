package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.servicos.ClienteServico;
import com.autobots.automanager.servicos.DocumentoServico;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {

    @Autowired
    private DocumentoServico documentoServico;

    @Autowired
    private ClienteServico clienteServico;

    @GetMapping
    public ResponseEntity<List<EntityModel<Documento>>> obterTodos() {
        List<Documento> documentos = documentoServico.obterTodos();
        List<EntityModel<Documento>> documentoModels = documentos.stream()
            .map(documento -> {
                EntityModel<Documento> documentoModel = EntityModel.of(documento);
                documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterPorId(documento.getId())).withSelfRel());
                return documentoModel;
            }).toList();
        return new ResponseEntity<>(documentoModels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Documento>> obterPorId(@PathVariable Long id) {
        Optional<Documento> documentoOpt = documentoServico.obterPorId(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            EntityModel<Documento> documentoModel = EntityModel.of(documento);
            documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterPorId(id)).withSelfRel());
            documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterTodos()).withRel("documentos"));
            documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).atualizarDocumento(id, documento)).withRel("atualizar"));
            documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).deletarDocumento(id)).withRel("deletar"));
            return new ResponseEntity<>(documentoModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Documento>> criarDocumento(@RequestBody Documento documento) {
        Documento novoDocumento = documentoServico.salvar(documento);
        EntityModel<Documento> documentoModel = EntityModel.of(novoDocumento);
        documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterPorId(novoDocumento.getId())).withSelfRel());
        documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterTodos()).withRel("documentos"));
        return new ResponseEntity<>(documentoModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Documento>> atualizarDocumento(@PathVariable Long id, @RequestBody Documento documentoAtualizado) {
        Optional<Documento> documentoOpt = documentoServico.obterPorId(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            documento.setTipo(documentoAtualizado.getTipo());
            documento.setNumero(documentoAtualizado.getNumero());
            documentoServico.salvar(documento);
            EntityModel<Documento> documentoModel = EntityModel.of(documento);
            documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterPorId(id)).withSelfRel());
            documentoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterTodos()).withRel("documentos"));
            return new ResponseEntity<>(documentoModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{clienteId}/adicionar")
    public ResponseEntity<EntityModel<Cliente>> adicionarDocumentoAoCliente(
        @PathVariable Long clienteId,
        @RequestBody Documento documento) {

        Optional<Cliente> clienteOpt = clienteServico.obterPorId(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.getDocumentos().add(documento);
            clienteServico.salvar(cliente);
            EntityModel<Cliente> clienteModel = EntityModel.of(cliente);
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterCliente(clienteId)).withSelfRel());
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterPorId(documento.getId())).withRel("documento"));
            return new ResponseEntity<>(clienteModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDocumento(@PathVariable Long id) {
        Optional<Documento> documentoOpt = documentoServico.obterPorId(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            List<Cliente> clientes = clienteServico.obterTodos();
            for (Cliente cliente : clientes) {
                if (cliente.getDocumentos().contains(documento)) {
                    cliente.getDocumentos().remove(documento);
                    clienteServico.salvar(cliente);
                }
            }
            try {
                documentoServico.deletar(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
