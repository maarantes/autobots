package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.entidades.Credencial;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkCredencial implements AdicionadorLink<Credencial> {
	
	@Override
	public void adicionarLink(List<Credencial> lista) {
		for (Credencial credencial : lista) {
			adicionarLinkParaListagem(credencial);
		}
	}
	
	@Override
	public void adicionarLink(Credencial objeto) {
		if (!objeto.getLinks().isEmpty()) {
			return;
		}
		adicionarLinkParaDetalhe(objeto);
	}
	
	public void adicionarLinkParaListagem(Credencial objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredencial(id))
				.withSelfRel();
		objeto.add(linkProprio);
	}
	
	public void adicionarLinkParaDetalhe(Credencial objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredencial(id))
				.withSelfRel();
		objeto.add(linkProprio);
		
		Link linkListar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredenciais())
				.withRel("listarCredenciais");
		objeto.add(linkListar);
		
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.excluirCredencial(id))
				.withRel("deletar");
		objeto.add(linkDeletar);
		
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.atualizarCredencial(null, id, null))
				.withRel("atualizar");
		objeto.add(linkAtualizar);
	}
}