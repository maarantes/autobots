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
    public ResponseEntity<List<EntityModel<Endereco>>> obterTodosEnderecos() {
        List<Endereco> enderecos = enderecoServico.obterTodos();
        List<EntityModel<Endereco>> enderecoModels = enderecos.stream()
            .map(endereco -> {
                EntityModel<Endereco> enderecoModel = EntityModel.of(endereco);
                enderecoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEnderecoPorId(endereco.getId())).withSelfRel());
                return enderecoModel;
            }).toList();
        return new ResponseEntity<>(enderecoModels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Endereco>> obterEnderecoPorId(@PathVariable Long id) {
        Optional<Endereco> enderecoOpt = enderecoServico.obterPorId(id);
        if (enderecoOpt.isPresent()) {
            Endereco endereco = enderecoOpt.get();
            EntityModel<Endereco> enderecoModel = EntityModel.of(endereco);
            enderecoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEnderecoPorId(id)).withSelfRel());
            enderecoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterTodosEnderecos()).withRel("enderecos"));
            return new ResponseEntity<>(enderecoModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{clienteId}/adicionar")
    public ResponseEntity<EntityModel<Cliente>> adicionarEnderecoAoCliente(
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
            EntityModel<Cliente> clienteModel = EntityModel.of(cliente);
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterCliente(clienteId)).withSelfRel());
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEnderecoPorId(novoEndereco.getId())).withRel("endereco"));
            return new ResponseEntity<>(clienteModel, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Endereco>> atualizarEndereco(
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
            EntityModel<Endereco> enderecoModel = EntityModel.of(endereco);
            enderecoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEnderecoPorId(id)).withSelfRel());
            enderecoModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterTodosEnderecos()).withRel("enderecos"));
            return new ResponseEntity<>(enderecoModel, HttpStatus.OK);
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
