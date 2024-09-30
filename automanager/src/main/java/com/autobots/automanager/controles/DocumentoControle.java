package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public ResponseEntity<List<Documento>> obterTodos() {
        List<Documento> documentos = documentoServico.obterTodos();
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterPorId(@PathVariable Long id) {
        Optional<Documento> documentoOpt = documentoServico.obterPorId(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            return new ResponseEntity<>(documento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Documento> criarDocumento(@RequestBody Documento documento) {
        Documento novoDocumento = documentoServico.salvar(documento);
        return new ResponseEntity<>(novoDocumento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizarDocumento(@PathVariable Long id, @RequestBody Documento documentoAtualizado) {
        Optional<Documento> documentoOpt = documentoServico.obterPorId(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            documento.setTipo(documentoAtualizado.getTipo());
            documento.setNumero(documentoAtualizado.getNumero());
            documentoServico.salvar(documento);
            return new ResponseEntity<>(documento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{clienteId}/adicionar")
    public ResponseEntity<Cliente> adicionarDocumentoAoCliente(@PathVariable Long clienteId, @RequestBody Documento documento) {
        Optional<Cliente> clienteOpt = clienteServico.obterPorId(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.getDocumentos().add(documento);
            clienteServico.salvar(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
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
