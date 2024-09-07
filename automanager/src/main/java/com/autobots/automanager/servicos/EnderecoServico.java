package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@Service
public class EnderecoServico {

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    public List<Endereco> obterTodos() {
        return enderecoRepositorio.findAll();
    }

    public Optional<Endereco> obterPorId(Long id) {
        return enderecoRepositorio.findById(id);
    }

    public Endereco salvar(Endereco endereco) {
        return enderecoRepositorio.save(endereco);
    }

    public void deletar(Long id) {
        enderecoRepositorio.deleteById(id);
    }
}
