package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.servicos.ClienteServico;
import com.autobots.automanager.servicos.TelefoneServico;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {

    @Autowired
    private TelefoneServico telefoneServico;

    @Autowired
    private ClienteServico clienteServico;

    @PostMapping("/{clienteId}/adicionar")
    public ResponseEntity<Cliente> adicionarTelefoneAoCliente(
        @PathVariable Long clienteId,
        @RequestBody Telefone telefone) {

        Optional<Cliente> clienteOpt = clienteServico.obterPorId(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.getTelefones().add(telefone);
            clienteServico.salvar(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Telefone>> obterTodosTelefones() {
        List<Telefone> telefones = telefoneServico.obterTodos();
        return new ResponseEntity<>(telefones, HttpStatus.OK);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<Telefone>> obterTelefonesPorCliente(@PathVariable Long clienteId) {
        Optional<Cliente> clienteOpt = clienteServico.obterPorId(clienteId);
        if (clienteOpt.isPresent()) {
            List<Telefone> telefones = clienteOpt.get().getTelefones();
            return new ResponseEntity<>(telefones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{telefoneId}")
    public ResponseEntity<Telefone> atualizarTelefone(
        @PathVariable Long telefoneId,
        @RequestBody Telefone telefoneAtualizado) {

        Optional<Telefone> telefoneOpt = telefoneServico.obterPorId(telefoneId);
        if (telefoneOpt.isPresent()) {
            Telefone telefone = telefoneOpt.get();
            telefone.setDdd(telefoneAtualizado.getDdd());
            telefone.setNumero(telefoneAtualizado.getNumero());
            telefoneServico.salvar(telefone);
            return new ResponseEntity<>(telefone, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{telefoneId}")
    public ResponseEntity<Void> deletarTelefone(@PathVariable Long telefoneId) {
        try {
            Optional<Telefone> telefoneOpt = telefoneServico.obterPorId(telefoneId);
            if (telefoneOpt.isPresent()) {
                Telefone telefone = telefoneOpt.get();

                List<Cliente> clientes = clienteServico.obterTodos();
                for (Cliente cliente : clientes) {
                    if (cliente.getTelefones().contains(telefone)) {
                        cliente.getTelefones().remove(telefone);
                        clienteServico.salvar(cliente);
                        break;
                    }
                }

                try {
                    telefoneServico.deletar(telefoneId);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (EmptyResultDataAccessException e) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
