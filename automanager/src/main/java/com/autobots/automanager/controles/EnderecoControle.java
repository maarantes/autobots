package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.servicos.ClienteServico;
import com.autobots.automanager.servicos.EnderecoServico;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {

    @Autowired
    private EnderecoServico enderecoServico;

    @Autowired
    private ClienteServico clienteServico;

    @GetMapping
    public ResponseEntity<List<Endereco>> obterTodosEnderecos() {
        List<Endereco> enderecos = enderecoServico.obterTodos();
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEnderecoPorId(@PathVariable Long id) {
        Optional<Endereco> enderecoOpt = enderecoServico.obterPorId(id);
        if (enderecoOpt.isPresent()) {
            Endereco endereco = enderecoOpt.get();
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{clienteId}/adicionar")
    public ResponseEntity<Cliente> adicionarEnderecoAoCliente(
        @PathVariable Long clienteId,
        @RequestBody Endereco novoEndereco) {

        Optional<Cliente> clienteOpt = clienteServico.obterPorId(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();

            if (cliente.getEndereco() != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            cliente.setEndereco(novoEndereco);
            clienteServico.salvar(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(
        @PathVariable Long id,
        @RequestBody Endereco enderecoAtualizado) {

        Optional<Endereco> enderecoOpt = enderecoServico.obterPorId(id);
        if (enderecoOpt.isPresent()) {
            Endereco endereco = enderecoOpt.get();
            endereco.setEstado(enderecoAtualizado.getEstado());
            endereco.setCidade(enderecoAtualizado.getCidade());
            endereco.setBairro(enderecoAtualizado.getBairro());
            endereco.setRua(enderecoAtualizado.getRua());
            endereco.setNumero(enderecoAtualizado.getNumero());
            endereco.setCodigoPostal(enderecoAtualizado.getCodigoPostal());
            endereco.setInformacoesAdicionais(enderecoAtualizado.getInformacoesAdicionais());
            enderecoServico.salvar(endereco);
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        Optional<Endereco> enderecoOpt = enderecoServico.obterPorId(id);
        if (enderecoOpt.isPresent()) {
            List<Cliente> clientes = clienteServico.obterTodos();
            for (Cliente cliente : clientes) {
                if (cliente.getEndereco() != null && cliente.getEndereco().getId().equals(id)) {
                    cliente.setEndereco(null);
                    clienteServico.salvar(cliente);
                }
            }

            try {
                enderecoServico.deletar(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
