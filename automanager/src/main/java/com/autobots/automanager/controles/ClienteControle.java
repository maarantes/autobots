package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.servicos.ClienteServico;

@RestController
@RequestMapping("/clientes")
public class ClienteControle {
    @Autowired
    private ClienteServico clienteServico;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obterCliente(@PathVariable long id) {
        Optional<Cliente> clienteOpt = clienteServico.obterPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            EntityModel<Cliente> clienteModel = EntityModel.of(cliente);

            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterCliente(id)).withSelfRel());
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterClientes()).withRel("clientes"));
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).atualizarCliente(id, cliente)).withRel("atualizar"));
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).excluirCliente(id)).withRel("deletar"));

            return new ResponseEntity<>(clienteModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<Cliente>>> obterClientes() {
        List<Cliente> clientes = clienteServico.obterTodos();
        List<EntityModel<Cliente>> clienteModels = clientes.stream()
            .map(cliente -> {
                EntityModel<Cliente> clienteModel = EntityModel.of(cliente);
                clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterCliente(cliente.getId())).withSelfRel());
                return clienteModel;
            }).toList();
        return new ResponseEntity<>(clienteModels, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteServico.salvar(cliente);
        EntityModel<Cliente> clienteModel = EntityModel.of(novoCliente);
        clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterCliente(novoCliente.getId())).withSelfRel());
        clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterClientes()).withRel("clientes"));
        return new ResponseEntity<>(clienteModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> atualizarCliente(@PathVariable Long id, @RequestBody Cliente atualizacao) {
        Optional<Cliente> clienteOpt = clienteServico.obterPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            ClienteAtualizador atualizador = new ClienteAtualizador();
            atualizador.atualizar(cliente, atualizacao);
            clienteServico.salvar(cliente);
            EntityModel<Cliente> clienteModel = EntityModel.of(cliente);
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterCliente(id)).withSelfRel());
            clienteModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControle.class).obterClientes()).withRel("clientes"));
            return new ResponseEntity<>(clienteModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteServico.obterPorId(id);
        if (clienteOpt.isPresent()) {
            clienteServico.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
