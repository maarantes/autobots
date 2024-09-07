package com.autobots.automanager.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@Service
public class TelefoneServico {

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    public List<Telefone> obterTodos() {
        return telefoneRepositorio.findAll();
    }

    public Optional<Telefone> obterPorId(Long id) {
        return telefoneRepositorio.findById(id);
    }

    public Telefone salvar(Telefone telefone) {
        return telefoneRepositorio.save(telefone);
    }

    public void deletar(Long id) {
        telefoneRepositorio.deleteById(id);
    }
}
