package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Cliente> obterTodos() {
        return clienteRepositorio.findAll();
    }

    public Optional<Cliente> obterPorId(Long id) {
        return clienteRepositorio.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepositorio.deleteById(id);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Optional<Cliente> clienteExistente = clienteRepositorio.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setNomeSocial(clienteAtualizado.getNomeSocial());
            cliente.setDataNascimento(clienteAtualizado.getDataNascimento());
            cliente.setDataCadastro(clienteAtualizado.getDataCadastro());
            cliente.setDocumentos(clienteAtualizado.getDocumentos());
            cliente.setEndereco(clienteAtualizado.getEndereco());
            cliente.setTelefones(clienteAtualizado.getTelefones());
            return clienteRepositorio.save(cliente);
        } else {
            return null;
        }
    }
}